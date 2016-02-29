package com.onepiece.redwood.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onepiece.redwood.ResultCode;

/**
 * 监听重新登录广播
 */
public class BroadCastRelogin extends BroadcastReceiver {
    IReLoginReceive iReLoginReceive;

    public void setiReLoginReceive(IReLoginReceive iReLoginReceive) {
        this.iReLoginReceive = iReLoginReceive;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER:
                iReLoginReceive.OnReceiceReLogin();
                break;
            default:
                break;
        }
    }
}
