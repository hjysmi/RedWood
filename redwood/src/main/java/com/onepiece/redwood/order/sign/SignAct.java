package com.onepiece.redwood.order.sign;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * 电子签名
 */
public class SignAct extends Activity implements View.OnClickListener, ISignView {
    private Button id_but_back;
    private PaintView id_signview;
    private Button id_but_resign, id_but_sure;
    private WhorlView id_whorlview;
    private SignPresenter presenter = new SignPresenter(this);
    private int orderId=0;
    BroadCastRelogin broadCastRelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        id_but_back.setOnClickListener(this);
        id_but_resign.setOnClickListener(this);
        id_but_sure.setOnClickListener(this);
        /**重新登录监听*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                File file = id_signview.saveBitmap();
                if (file.exists()) {
                    String mimeType = "image/jpg";
                    TypedFile fileToSend = new TypedFile(mimeType, file);
                    String token = (String) SPUtils.get(SignAct.this, "token", "");
                    presenter.upSignImage(token,orderId, fileToSend);
                } else {
                    Msg.showShort(SignAct.this, "没有找到该图片");
                }

            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID,0);
    }

    private void initViews() {
        id_but_back = (Button) findViewById(R.id.id_but_back);
        id_signview = (PaintView) findViewById(R.id.id_signview);
        id_but_resign = (Button) findViewById(R.id.id_but_resign);
        id_but_sure = (Button) findViewById(R.id.id_but_sure);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_but_back:
                finish();
                break;
            case R.id.id_but_resign:
                id_signview.removeAllPaint();
                break;
            case R.id.id_but_sure:
                File file = id_signview.saveBitmap();
                if (file.exists()) {
                    String mimeType = "image/jpg";
                    TypedFile fileToSend = new TypedFile(mimeType, file);
                    String token = (String) SPUtils.get(this, "token", "");
                    presenter.upSignImage(token,orderId, fileToSend);
                } else {
                    Msg.showShort(this, "没有找到该图片");
                }
                break;
        }
    }




    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.GONE);
        id_whorlview.stop();
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.stop();
    }

    @Override
    public void showUpSignImageSuccess() {
        Msg.showShort(this, "上传成功");
        finish();
    }

    @Override
    public void showNetError() {
        Msg.showShort(this,"请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this, "上传失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
    }
    private void registerBroadcastReceiver() {
        broadCastRelogin = new BroadCastRelogin();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
        registerReceiver(broadCastRelogin, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

       unregisterReceiver(broadCastRelogin);
    }

}
