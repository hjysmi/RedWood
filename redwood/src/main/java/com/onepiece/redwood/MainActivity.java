package com.onepiece.redwood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.onepiece.redwood.cart.CartFrag;
import com.onepiece.redwood.customer.CustomerFrag;
import com.onepiece.redwood.menu.ProClassFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener,OnPageChangeListener {
    ViewPager mViewPager;
    List<Fragment> mTabs = new ArrayList<Fragment>();
    FragmentPagerAdapter pagerAdapter;
    private List<ChangeColorWithText> mTabIndicators = new ArrayList<ChangeColorWithText>();
    String[] mTitles = new String[]{
            "订单","我"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();
        initDatas();
        mViewPager.setAdapter(pagerAdapter);
        initEvent();



    }
    private void initEvent() {
        mViewPager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        //产品分类
        mTabs.add(new ProClassFrag());
        //客户管理
        mTabs.add(new CustomerFrag());
        //订单管理
        mTabs.add(new CartFrag());
        //购物车管理
        mTabs.add(new CartFrag());
        //我
        mTabs.add(new CartFrag());
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };

    }
    private void initValues() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setOffscreenPageLimit(5);
       /* ChangeColorWithText one = (ChangeColorWithText) findViewById(R.id.id_indicator_one);
        mTabIndicators.add(one);
        ChangeColorWithText two = (ChangeColorWithText) findViewById(R.id.id_indicator_two);
        mTabIndicators.add(two);
        ChangeColorWithText three = (ChangeColorWithText) findViewById(R.id.id_indicator_three);
        mTabIndicators.add(three);
        ChangeColorWithText four = (ChangeColorWithText) findViewById(R.id.id_indicator_four);
        mTabIndicators.add(four);
        ChangeColorWithText five = (ChangeColorWithText) findViewById(R.id.id_indicator_five);
        mTabIndicators.add(five);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        one.setIconAlpha(1.0f);*/
    }
    @Override
    public void onClick(View v) {
        clickTab(v);
    }
    private void clickTab(View v) {
        resetOtherTabs();

       /* switch (v.getId())
        {
            case R.id.id_indicator_one:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mTabIndicators.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.id_indicator_five:
                mTabIndicators.get(4).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(4, false);
                break;
        }*/



    }
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++)
        {
            mTabIndicators.get(i).setIconAlpha(0);
        }

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0)
        {
            ChangeColorWithText left = mTabIndicators.get(position);
            ChangeColorWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
