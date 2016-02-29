package com.onepiece.redwood.coll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.WhorlView;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.HashMap;
import java.util.List;

/**
 * 收藏夹
 */
public class CollAct extends Activity implements ICollView, View.OnClickListener, Colladapter.Callback {
    private ListView lv;
    private Button but_back, but_update;
    private PercentLinearLayout rl_empty;
    private WhorlView id_whorlview;
    CollPrestener prestener = new CollPrestener(this, this);
    HashMap<CollBean, Boolean> hashMap;
    Colladapter adapter;
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coll);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        but_update.setOnClickListener(this);
    }

    private void initValues() {
        prestener.queryCollAll();

    }

    private void initViews() {
        lv = (ListView) findViewById(R.id.lv);
        but_update = (Button) findViewById(R.id.but_update);
        but_back = (Button) findViewById(R.id.but_back);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        rl_empty = (PercentLinearLayout) findViewById(R.id.rl_empty);
        lv.setEmptyView(rl_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;

            case R.id.but_update:

                prestener.queryCollAll();
                break;
        }
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.GONE);
        id_whorlview.stop();
    }

    @Override
    public void showCollSuccess(List<CollBean> collBeanList) {
        hashMap = new HashMap<CollBean, Boolean>();
        if (flag) {
            //删除显示箭头隐藏
            for (int i = 0; i < collBeanList.size(); i++) {
                CollBean collBean = collBeanList.get(i);
                hashMap.put(collBean, true);
            }
        } else {
            //箭头显示删除隐藏
            for (int i = 0; i < collBeanList.size(); i++) {
                CollBean collBean = collBeanList.get(i);
                hashMap.put(collBean, false);
            }
        }
        adapter = new Colladapter(this, R.layout.coll_item, this);
        adapter.bindDatas(collBeanList);
        adapter.setHashMap(hashMap);
        lv.setAdapter(adapter);
        if (flag) {
            flag = false;
        } else {
            flag = true;
        }
    }

    @Override
    public void delColl(List<CollBean> collBeanList) {
        for (int i = 0; i < collBeanList.size(); i++) {
            CollBean collBean = collBeanList.get(i);
            hashMap.put(collBean, true);
        }
        adapter = new Colladapter(this, R.layout.coll_item, this);
        adapter.bindDatas(collBeanList);
        adapter.setHashMap(hashMap);
        lv.setAdapter(adapter);
    }



    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.but_del:
                int proId = (int) v.getTag();
                prestener.delCollById(proId);
                break;
        }
    }
}
