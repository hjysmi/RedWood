package com.onepiece.redwood.prodetail.photoview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.prodetail.photoview.libs.PhotoViewFragment;
import com.onepiece.redwood.prodetail.photoview.libs.ViewpagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品图片的放大缩小
 */
public class PicShowAct extends FragmentActivity {
    private ViewPager vp;
    private TextView indexTV;
    private ArrayList<String> list = new ArrayList<String>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        vp = (ViewPager) findViewById(R.id.viewPager);
        vp.setOverScrollMode(View.OVER_SCROLL_NEVER);
        indexTV = (TextView) findViewById(R.id.indexTV);
        float x = (float) 0.6;
        indexTV.setAlpha(x);
        index = getIntent().getIntExtra(ResultCode.PIC_SHOW_POSITION, 0);
        list = getIntent().getStringArrayListExtra(ResultCode.PIC_SHOW_IMGURL);
        List<Fragment> arrayList_pic = new ArrayList<Fragment>();
        for (int i = 0; i < list.size(); i++) {
            arrayList_pic.add(new PhotoViewFragment(this, list.get(i)));
        }
        ViewpagerAdapter vpAdapter = new ViewpagerAdapter(getSupportFragmentManager(), arrayList_pic, null);

        vp.setAdapter(vpAdapter);
        vp.setCurrentItem(index);
        updateStatus(index);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateStatus(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void updateStatus(int position) {


        this.indexTV.setText(position + 1 + "/" + list.size());

    }
}
