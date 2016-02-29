package com.onepiece.redwood.customer.contracts;

import android.content.Context;
import android.os.Handler;

import com.onepiece.redwood.customer.contracts.lib.SortModel;

import java.util.List;


/**
 * Created by Administrator on 2015/8/25.
 */
public class ContractPrestenter {
    Context context;
    IContractView iContractView;
    IContractBiz iContractBiz;
    Handler handler = new Handler();
    public ContractPrestenter(Context context, IContractView iContractView) {
        this.context = context;
        this.iContractView = iContractView;
        iContractBiz = new ContractBiz();
    }

    public void getDataInfo() {
        iContractBiz.getPhoneContacts(context, new OnPhoneContactsListener() {
            @Override
            public void OnSuccess(final List<SortModel> sortModelList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iContractView.showDataSuccess(sortModelList);
                    }
                });

            }

            @Override
            public void OnFail() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iContractView.showDataFail();
                    }
                });

            }
        });
    }
}
