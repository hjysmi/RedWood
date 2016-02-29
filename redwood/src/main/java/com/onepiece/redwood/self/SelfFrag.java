package com.onepiece.redwood.self;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.BaseFragment;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.coll.CollAct;
import com.onepiece.redwood.customer.contracts.lib.RoundImageViewByXfermode;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.self.update.UpdateUserAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.mime.TypedFile;

/**
 * 个人信息管理
 */
public class SelfFrag extends BaseFragment implements ISelfView, View.OnClickListener {
    private View mView;
    Dialog dialog;
    private PercentRelativeLayout rl_headimage, rl_phone, rl_coll;
    // private ImageView id_iv_img;
    private TextView tv_nickname, tv_phone;
    private WhorlView id_whorlview;
    private PercentRelativeLayout rl_name;
    private Button but_exit;
    private SelfPrestener prestener = new SelfPrestener(this);
    private RoundImageViewByXfermode id_iv_img;
    private BroadCastRelogin broadCastRelogin;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.self, null);
        initViews();
        initValues();
        ininEvents();
        return mView;
    }

    private void ininEvents() {
        rl_headimage.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_coll.setOnClickListener(this);
        but_exit.setOnClickListener(this);
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                Msg.showShort(getActivity(),"请重新上传头像");
                id_iv_img.setImageResource(R.drawable.headimage);

            }
        });
    }

    private void initValues() {
        // String img_url = SPUtils.get(getActivity(),"")
        registerBroadcastReceiver();
       /* Glide.with(getActivity()).load(UrlAPI.IPADDRESS + (String) SPUtils.get(getActivity(), "headimage", ""))
                .into(id_iv_img);*/
        ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + (String) SPUtils.get(getActivity(), "headimage", ""),id_iv_img);
        tv_nickname.setText((String) SPUtils.get(getActivity(), "nickname", ""));
        tv_phone.setText((String) SPUtils.get(getActivity(), "phone", ""));
    }

    private void initViews() {
        rl_headimage = (PercentRelativeLayout) mView.findViewById(R.id.rl_headimage);
        id_iv_img = (RoundImageViewByXfermode) mView.findViewById(R.id.id_iv_img);
        id_whorlview = (WhorlView) mView.findViewById(R.id.id_whorlview);
        rl_name = (PercentRelativeLayout) mView.findViewById(R.id.rl_name);
        rl_phone = (PercentRelativeLayout) mView.findViewById(R.id.rl_phone);
        rl_coll = (PercentRelativeLayout) mView.findViewById(R.id.rl_coll);
        tv_nickname = (TextView) mView.findViewById(R.id.tv_nickname);
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        but_exit = (Button) mView.findViewById(R.id.but_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_exit:
                SPUtils.clear(getActivity());
                Intent intent_login = new Intent(getActivity(), LoginAct.class);
                getActivity().startActivity(intent_login);
                getActivity().finish();
                break;
            case R.id.rl_coll:
                Intent intent = new Intent(getActivity(), CollAct.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_phone:
                Intent intent_phone = new Intent(getActivity(), UpdateUserAct.class);
                intent_phone.putExtra(ResultCode.USER_UPDATE_NAME, "电话");
                intent_phone.putExtra(ResultCode.USER_UPDATE_KEY, "phone");
                intent_phone.putExtra(ResultCode.USER_UPDATE_VALUE, tv_phone.getText().toString().trim());
                startActivityForResult(intent_phone, ResultCode.USER_UPDATE_RESULT_CODE);
                // getActivity().startActivity(intent_phone);
                break;
            case R.id.rl_name:
                Intent intent_name = new Intent(getActivity(), UpdateUserAct.class);
                intent_name.putExtra(ResultCode.USER_UPDATE_NAME, "昵称");
                intent_name.putExtra(ResultCode.USER_UPDATE_KEY, "name");
                intent_name.putExtra(ResultCode.USER_UPDATE_VALUE, tv_nickname.getText().toString().trim());
                startActivityForResult(intent_name, ResultCode.USER_UPDATE_RESULT_CODE);
                //   getActivity().startActivityForResult(intent_name,ResultCode.SELF_NAME_CODE);
                break;
            case R.id.rl_headimage:
                initDialog();
                break;
            case R.id.but_cancel:
                dialog.dismiss();
                break;
            case R.id.but_camera:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, ResultCode.SELF_CAMERA_CODE);
                dialog.dismiss();
                break;
            case R.id.but_photo:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("image/*");
                startActivityForResult(intent2, ResultCode.SELF_PHOTO_CODE);
                dialog.dismiss();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ResultCode.USER_UPDATE_RESULT_CODE:
                if (data == null) {
                    return;
                } else {
                    Bundle bundle = data.getBundleExtra(ResultCode.USER_UPDATE_RESULT_DATA);
                    if (bundle == null) {
                        return;
                    } else {
                        if ("name".equals(bundle.getString(ResultCode.USER_UPDATE_RESULT_KEY))) {
                            tv_nickname.setText(bundle.getString(ResultCode.USER_UPDATE_RESULT));
                        } else if ("phone".equals(bundle.getString(ResultCode.USER_UPDATE_RESULT_KEY))) {
                            tv_phone.setText(bundle.getString(ResultCode.USER_UPDATE_RESULT));
                        }
                    }
                }

                break;
            case ResultCode.SELF_CAMERA_CODE:

                if (data == null) {
                    return;
                } else {
                    Bundle bundle = data.getExtras();
                    Bitmap bm = bundle.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
                break;
            case ResultCode.SELF_PHOTO_CODE:
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                Uri fileUri = convertUri(uri);
                startImageZoom(fileUri);
                break;
            case ResultCode.SELF_CROP_CODE:
                if (data == null) return;
                Bundle bundle = data.getExtras();
                if (bundle == null) return;
                Bitmap bm = bundle.getParcelable("data");
                id_iv_img.setImageBitmap(bm);
                break;
        }
    }


    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.onepiece.redwood");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "avater.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //    write(Bitmap2Bytes(bm));
            fos.flush();
            fos.close();
            if (img.exists()) {
                String mimeType = "image/jpg";
                TypedFile fileToSend = new TypedFile(mimeType, img);
                String token = (String) SPUtils.get(getActivity(), "token", "");
                prestener.upImage(token, fileToSend);
            } else {
                Msg.showShort(getActivity(), "没有找到该图片");
            }
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ResultCode.SELF_CROP_CODE);
    }

    private void initDialog() {
        dialog = new Dialog(getActivity(), R.style.self_dialog);
        dialog.setContentView(R.layout.selft_dialog_content_view);
        Button but_cancel = (Button) dialog.findViewById(R.id.but_cancel);
        Button but_camera = (Button) dialog.findViewById(R.id.but_camera);
        Button but_photo = (Button) dialog.findViewById(R.id.but_photo);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.self_dialog_anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置起始坐标
        wl.x = 0;
        wl.y = getActivity().getResources().getDisplayMetrics().heightPixels;
        //wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        but_cancel.setOnClickListener(this);
        but_camera.setOnClickListener(this);
        but_photo.setOnClickListener(this);
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void hideBar() {
        id_whorlview.stop();
        id_whorlview.setVisibility(View.GONE);

    }

    @Override
    public void showUpImageSuccess() {
        Msg.showShort(getActivity(), "上传成功");
    }

    @Override
    public void showNetError() {
        Msg.showShort(getActivity(), "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(getActivity(), "上传失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(getActivity(), LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
    }
    private void registerBroadcastReceiver() {
        broadCastRelogin = new BroadCastRelogin();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
        getActivity().registerReceiver(broadCastRelogin, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadCastRelogin);
    }

}
