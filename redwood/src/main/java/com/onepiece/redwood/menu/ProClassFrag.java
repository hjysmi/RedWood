package com.onepiece.redwood.menu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.onepiece.redwood.BaseFragment;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.prodetail.ProDetailAct;
import com.onepiece.redwood.prolist.ProListAct;
import com.onepiece.redwood.utils.KeyBoardUtils;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.ScreenUtils;
import com.onepiece.redwood.utils.StringUtils;
import com.zxing.activity.CaptureActivity;

import java.util.List;

/**
 * 产品分类(首页)
 */
public class ProClassFrag extends BaseFragment implements IMenuView, View.OnClickListener {

    View mView;
    int width, height;
    EditText id_et_search;
    TextView id_proclassfrag_all;
    ListView id_proclassfrag_lv;
    GridView id_proclassfrag_gv;
    WhorlView id_whorlview;
    ImageView id_scanner;
    FirstMenuAdapter firstMenuAdapter;
    SecondMenuAdapter secondMenuAdapter;
    MenuPresenter menuPresenter = new MenuPresenter(this);
    List<MenuBean> list_menuBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.proclassfrag, container, false);
        initView();
        initValues();
        initEvents();
        menuPresenter.getMenuInfo();

        return mView;
    }

    private void initValues() {
        secondMenuAdapter = new SecondMenuAdapter(getActivity());
    }

    private void initEvents() {
        id_proclassfrag_all.setOnClickListener(this);
        id_proclassfrag_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //更改背景颜色
                FirstMenuAdapter adapter = (FirstMenuAdapter) parent.getAdapter();
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();

             //   secondMenuAdapter = new SecondMenuAdapter(getActivity());
                secondMenuAdapter.bindData(list_menuBean.get(position).getChildProductCustomCat());
                id_proclassfrag_gv.setAdapter(secondMenuAdapter);
            }
        });
    }

    private void initView() {
        width = ScreenUtils.getScreenWidth(getActivity());
        height = ScreenUtils.getScreenHeight(getActivity());
        id_et_search = (EditText) mView.findViewById(R.id.id_et_search);
        id_et_search.setPadding((int) (0.09 * width), 0, 0, 0);
        id_proclassfrag_lv = (ListView) mView.findViewById(R.id.id_proclassfrag_lv);
        id_proclassfrag_gv = (GridView) mView.findViewById(R.id.id_proclassfrag_gv);
        id_proclassfrag_gv.setVerticalSpacing((int) (0.03 * width));
        id_scanner = (ImageView) mView.findViewById(R.id.id_scanner);
        id_proclassfrag_all = (TextView) mView.findViewById(R.id.id_proclassfrag_all);
        id_whorlview = (WhorlView) mView.findViewById(R.id.id_whorlview);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius((float) (0.125 * width));
        gd.setColor(Color.parseColor("#ffffff"));
        id_proclassfrag_all.setBackgroundDrawable(gd);

        id_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
        id_et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String str = v.getText().toString().trim();

                if (StringUtils.isEmpty(str)) {
                    Msg.showShort(getActivity(), "搜索内容不能为空");
                } else {
                    Intent intent = new Intent(getActivity(), ProListAct.class);
                    intent.putExtra(ProListAct.KEY_SEARCH, true);
                    intent.putExtra(ProListAct.KEY_SEARCHCONTENT, v.getText().toString());
                    startActivity(intent);
                }
                KeyBoardUtils.closeKeybord(id_et_search, getActivity());
                v.setText("");
                return false;
            }
        });
    }


    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.INVISIBLE);
        id_whorlview.stop();
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void showMenuSuccess(List<MenuBean> list_menuBean) {
        this.list_menuBean = list_menuBean;
        firstMenuAdapter = new FirstMenuAdapter(getActivity(), R.layout.item_firstmen);
        firstMenuAdapter.bindDatas(list_menuBean);
        id_proclassfrag_lv.setAdapter(firstMenuAdapter);

        secondMenuAdapter.bindData(list_menuBean.get(0).getChildProductCustomCat());
        id_proclassfrag_gv.setAdapter(secondMenuAdapter);
       /* secondMenuAdapter = new SecondMenuAdapter(getActivity(), list_menuBean.get(0).getChildProductCustomCat(), R.layout.item_secondmenu);
        id_proclassfrag_gv.setAdapter(secondMenuAdapter);*/
    }

    @Override
    public void showNetError() {
        Msg.showShort(getActivity(), "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(getActivity(), "请求失败,请重试");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == -1) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            String[] result = scanResult.split("=");
            if (result.length == 2) {
                Integer resultValue = getResultValue(result[1]);
                if (resultValue == null) {
                    Msg.showShort(getActivity(), "请再次扫描二维码");
                } else {
                    Intent intent = new Intent(getActivity(), ProDetailAct.class);
                    intent.putExtra(ResultCode.PRO_ID, resultValue);
                    getActivity().startActivity(intent);
                }
            } else {
                Msg.showShort(getActivity(), "读取错误,请重新扫一扫");
            }


        }
    }

    private Integer getResultValue(String result) {
        Integer i = 0;
        try {
            i = Integer.valueOf(result.trim());
            return i;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_proclassfrag_all:
                Intent intent = new Intent(getActivity(), ProListAct.class);
                intent.putExtra(ProListAct.KEY_SEARCH, false);
                intent.putExtra(ProListAct.KEY_ID, 0);
                intent.putExtra(ProListAct.KEY_PID, 0);
                getActivity().startActivity(intent);
                break;
        }
    }
}
