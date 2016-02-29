package com.onepiece.redwood.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.HomePageAct;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.customer.contracts.lib.RoundImageViewByXfermode;
import com.onepiece.redwood.self.UserBean;
import com.onepiece.redwood.utils.KeyBoardUtils;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

/**
 * 登录
 */
public class LoginAct extends Activity implements ILoginActView, View.OnClickListener {
    int width;
    private EditText id_et_uname, id_et_upsw;
    private Button but_login;
    private Button but_back;
    private WhorlView id_whorlview;
    private CheckBox id_check_autologin, id_check_psw;
   // private ImageView id_iv_img;
    RoundImageViewByXfermode id_iv_img;
    private LoginActPresenter presenter = new LoginActPresenter(this, this);
    private Boolean isRelogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginact);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        but_login.setOnClickListener(this);
        id_check_psw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //已选中记住密码
                    SPUtils.put(LoginAct.this, "ISCHECK", true);
                    //     Msg.showShort(LoginAct.this, "已选中");
                } else {
                    //未选中记住密码
                    //    Msg.showShort(LoginAct.this, "未选中");
                    SPUtils.put(LoginAct.this, "ISCHECK", false);
                }
            }
        });
        id_check_autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //已选中记住密码
                    SPUtils.put(LoginAct.this, "AUTO_LOGIN", true);
                    //     Msg.showShort(LoginAct.this, "已选中");
                } else {
                    //未选中记住密码
                    //   Msg.showShort(LoginAct.this, "未选中");
                    SPUtils.put(LoginAct.this, "AUTO_LOGIN", false);
                }
            }
        });

    }

    private void initValues() {
        isRelogin = getIntent().getBooleanExtra(ResultCode.LOGIN_RELOGIN, false);
        String imgurl = (String) SPUtils.get(this, "headimage", "");
   //     Glide.with(this).load(UrlAPI.IPADDRESS + imgurl).transform(new GlideCircleTransform(this)).into(id_iv_img);
      //  Glide.with(this).load(UrlAPI.IPADDRESS + imgurl).placeholder(R.drawable.headimage).into(id_iv_img);
    //    ImageLoader.getInstance(getApplicationContext()).loadImage(UrlAPI.IPADDRESS + imgurl,id_iv_img);

    //    ImageLoaderController.getInstance(getApplicationContext()).displayIcon(UrlAPI.IPADDRESS + imgurl,id_iv_img,null);
        ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + imgurl,id_iv_img);
        //判断是否选中(记住密码)
        if ((Boolean) SPUtils.get(this, "ISCHECK", false)) {
            id_check_psw.setChecked(true);
            id_et_uname.setText((String) SPUtils.get(this, "uname", ""));
            id_et_upsw.setText((String) SPUtils.get(this, "upsw", ""));
            //判断是否自动登录
            if ((Boolean) SPUtils.get(this, "AUTO_LOGIN", false)) {
                Intent intent = new Intent(this, HomePageAct.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        but_back = (Button) findViewById(R.id.but_back);
        id_et_uname = (EditText) findViewById(R.id.id_et_uname);
        id_et_upsw = (EditText) findViewById(R.id.id_et_upsw);
        but_login = (Button) findViewById(R.id.but_login);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_check_autologin = (CheckBox) findViewById(R.id.id_check_autologin);
        id_check_psw = (CheckBox) findViewById(R.id.id_check_psw);
       // id_iv_img = (ImageView) findViewById(R.id.id_iv_img);
        id_iv_img = (RoundImageViewByXfermode) findViewById(R.id.id_iv_img);

        id_et_uname.setPadding((int) (0.15 * width), 0, 0, 0);
        id_et_upsw.setPadding((int) (0.15 * width), 0, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_login:
                KeyBoardUtils.closeKeybord(id_et_uname, this);
                KeyBoardUtils.closeKeybord(id_et_upsw, this);

                presenter.getLoginInfo();

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
    public void ShowLoginSuccess(UserBean userBean) {
        SPUtils.put(this, "token", userBean.getToken());
        SPUtils.put(this, "businessId", userBean.getBusinessId());
        SPUtils.put(this, "headimage", userBean.getHeadImage());
        SPUtils.put(this, "phone", userBean.getPhone());
        SPUtils.put(this, "nickname", userBean.getName());
        if (id_check_psw.isChecked()) {
            //已经选中记住密码
            SPUtils.put(this, "uname", getUname());
            SPUtils.put(this, "upsw", getUpsw());
        }
        if (isRelogin) {
            //为重新登录
            Intent intent = new Intent();
            intent.setAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
            sendBroadcast(intent);
            finish();
        }else{
            //不需要重新登录
            Intent intent = new Intent(this, HomePageAct.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public String getUname() {
        return id_et_uname.getText().toString().trim();
    }

    @Override
    public String getUpsw() {
        return id_et_upsw.getText().toString().trim();
    }

    @Override
    public void showNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this, "用户名或密码错误");
    }


}
