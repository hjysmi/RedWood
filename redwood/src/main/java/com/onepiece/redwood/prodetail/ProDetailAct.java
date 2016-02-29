package com.onepiece.redwood.prodetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.cycleviewpager.lib.BannerBean;
import com.android.cycleviewpager.lib.CycleViewPager;
import com.android.cycleviewpager.lib.ViewFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.cart.CartBean;
import com.onepiece.redwood.cart.CartDao;
import com.onepiece.redwood.coll.CollBean;
import com.onepiece.redwood.prodetail.cartact.CartAct;
import com.onepiece.redwood.prodetail.photoview.PicShowAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.onepiece.redwood.utils.StringUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情展示
 */
public class ProDetailAct extends Activity implements IProdetailView, View.OnClickListener {
    private List<ImageView> views = new ArrayList<ImageView>();

    private int proId;
    private PercentRelativeLayout id_rl_share, id_rl_coll;
    private Button but_back,but_cart;
    private TextView id_tv_name, id_tv_price, id_tv_detailmarketprice;
    private WhorlView id_whorlview;
    private ImageView id_iv_cart;
    private TextView id_tv_number;
    private TextView id_tv_material;
    private TextView id_tv_format;
    private TextView id_tv_style;
    private TextView id_tv_prodetail;
    private LayoutInflater inflater_detail;
    private PercentLinearLayout id_ll_main;
    private ArrayList<String> str_list;
    private ArrayList<Integer> color_list;
    private ArrayList<Float> text_size_list;
    private ProDetailPresenter presenter = new ProDetailPresenter(this, this);
    private CycleViewPager cycleViewPager;
    private List<BannerBean> list_banner = new ArrayList<BannerBean>();
    private String[] imgurl;
    private List<String> list_img = new ArrayList<String>();
    private int width;
    private ProDetailBean detailBean;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
    private StringBuffer share_img_url = new StringBuffer(UrlAPI.IPADDRESS);
    private String name;
    UMImage urlImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prodetail);
        initViews();
        initValues();
        initEvents();

    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {

        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
                "1104779707", "PuJDGEDPXFiRdZDe");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent(name + " " + share_img_url.toString());


        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent(name);
        weixinContent.setTitle(name);
        weixinContent.setTargetUrl(share_img_url.toString());
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent(name);
        circleMedia.setTitle(name);
        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl(share_img_url.toString());
        mController.setShareMedia(circleMedia);


       /* UMImage qzoneImage = new UMImage(this,
                UrlAPI.IPADDRESS);
        qzoneImage
                .setTargetUrl("http://www.umeng.com/images/pic/social/integrated_3.png");*/

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(name);
        qzone.setTargetUrl(share_img_url.toString());
        qzone.setTitle(name);
        qzone.setShareMedia(urlImage);
        // qzone.setShareMedia(uMusic);
        mController.setShareMedia(qzone);

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(name);
        qqShareContent.setTitle(name);
        //   qqShareContent.setShareMedia(image);
        qqShareContent.setTargetUrl(share_img_url.toString());
        mController.setShareMedia(qqShareContent);

        SinaShareContent sinaContent = new SinaShareContent();

        sinaContent
                .setShareContent(name + " " + share_img_url.toString());

        sinaContent.setShareImage(urlImage);
        mController.setShareMedia(sinaContent);

    }

    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();

        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wxf2d691a76fc0f375";
        String appSecret = "8ae1e03bd2cfae7cccc76ca817c989bf";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {
        String appId = "1104779707";
        String appKey = "PuJDGEDPXFiRdZDe";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl(share_img_url.toString());
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        id_iv_cart.setOnClickListener(this);
        id_rl_share.setOnClickListener(this);
        id_rl_coll.setOnClickListener(this);
        but_cart.setOnClickListener(this);
    }

    private void initValues() {
        proId = getIntent().getIntExtra(ResultCode.PRO_ID, 0);
        share_img_url.append("/m/");
        share_img_url.append((Integer) SPUtils.get(this, "businessId", 0));
        share_img_url.append("_details?id=");
        share_img_url.append(proId);
        presenter.getProDetailInfo(proId);

    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        but_back = (Button) findViewById(R.id.but_back);
        id_tv_name = (TextView) findViewById(R.id.id_tv_name);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_ll_main = (PercentLinearLayout) findViewById(R.id.id_ll_main);
        id_tv_price = (TextView) findViewById(R.id.id_tv_price);
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        but_cart = (Button) findViewById(R.id.but_cart);
        id_tv_detailmarketprice = (TextView) findViewById(R.id.id_tv_detailmarketprice);
        id_tv_detailmarketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        id_tv_number = (TextView) findViewById(R.id.id_tv_number);
        id_tv_material = (TextView) findViewById(R.id.id_tv_material);
        id_tv_format = (TextView) findViewById(R.id.id_tv_format);
        id_tv_style = (TextView) findViewById(R.id.id_tv_style);
        id_tv_prodetail = (TextView) findViewById(R.id.id_tv_prodetail);
        id_tv_prodetail.setPadding((int) (0.05 * width), 0, 0, 0);
        inflater_detail = LayoutInflater.from(this);
        id_iv_cart = (ImageView) findViewById(R.id.id_iv_cart);
        id_rl_share = (PercentRelativeLayout) findViewById(R.id.id_rl_share);
        id_rl_coll = (PercentRelativeLayout) findViewById(R.id.id_rl_coll);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_cart:
                Intent intent = new Intent(this, CartAct.class);
                startActivity(intent);
                break;
            case R.id.id_iv_cart:
                Msg.showShort(this, "加入购物车");
                //加入购物车
                addCart();
                break;
            case R.id.id_rl_coll:
                presenter.queryCollById(detailBean.getId());

                //   Msg.showShort(this, "收藏");
                break;
            case R.id.id_rl_share:
                mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA);
                mController.openShare(this, false);
                break;

        }
    }

    /**
     * 加入购物车
     */
    private void addCart() {
        CartDao dao = new CartDao(this);
        if (!dao.queryByProId(detailBean.getId())) {
            String img = "";
            //购物车不存在
            if (detailBean.getPicArray().split(",").length > 0) {
                img = detailBean.getPicArray().split(",")[0];
            }
            CartBean cartBean = new CartBean(1,
                    img, detailBean.getName(), detailBean.getPrice(), detailBean.getId());
            int i = dao.addCart(cartBean);
            if (i >= 0) {
                Msg.showShort(this, "该商品已加入购物车");
            } else {
                Msg.showShort(this, "该商品加入购物车失败,请重新加入");
            }
        } else {
            //  Msg.showShort(this, "该商品已加入购物车");
        }


    }

    @Override
    public void showNetError() {
        Msg.showShort(this,"请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this,"请求失败，请重试");
    }

    @Override
    public void showDetailSuccess(ProDetailBean detailBean) {
        this.detailBean = detailBean;
        if (detailBean == null) {
            Msg.showShort(this, "没有找到该产品的详细信息");
            return;
        } else {
            name = detailBean.getName();
            urlImage = new UMImage(this,UrlAPI.IPADDRESS+detailBean.getPicArray().split(",")[0]);

        //    imgurl = new UMImage(this, UrlAPI.IPADDRESS);
         //   urlImage.setTargetUrl(detailBean.getPicArray().split(",")[0]);
            //urlImage = new UMImage(this,detailBean.getPicArray().split(",")[0]);
            id_tv_name.setText(detailBean.getName());
            id_tv_price.setText("￥" + detailBean.getPrice());
            id_tv_detailmarketprice.setText("￥" + detailBean.getPrice() * 2);
            initBanner(detailBean);
            initFormat(detailBean);
            initDeatil(detailBean.getDescription());
            // 配置需要分享的相关平台
            configPlatforms();
            // 设置分享的内容
            setShareContent();
        }

    }

    /**
     * 加载产品详情
     *
     * @param description
     */
    private void initDeatil(String description) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        String data = null;
        try {
            jsonObject = new JSONObject(description);
            data = jsonObject.getString("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(data)) {
            List<ProDescriptionBean> ps = gson.fromJson(data, new TypeToken<List<ProDescriptionBean>>() {
            }.getType());
            for (int i = 0; i < ps.size(); i++) {
                View view_detail = inflater_detail.inflate(R.layout.prodetail_detail, null);
                ImageView iv = (ImageView) view_detail.findViewById(R.id.id_iv_detail);
                TextView tv = (TextView) view_detail.findViewById(R.id.id_tv_detail);
                ProDescriptionBean bean = ps.get(i);
                if (bean.getType().equals("image")) {
                    iv.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + bean.getValue(), iv);
                 //   Glide.with(getApplicationContext()).load(UrlAPI.IPADDRESS + bean.getValue()+"_400x400.jpg").into(iv);
                } else {
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(bean.getValue());
                }

                id_ll_main.addView(view_detail);

            }
        }


    }

    /**
     * 加载产品规格数据
     */
    private void initFormat(ProDetailBean detailBean) {
        str_list = new ArrayList<String>();
        color_list = new ArrayList<Integer>();
        text_size_list = new ArrayList<Float>();
        str_list.add("编       号      ");
        str_list.add(detailBean.getNumber());
        color_list.add(Color.parseColor("#000000"));
        color_list.add(Color.parseColor("#828282"));
        text_size_list.add((float) (0.011 * width));
        text_size_list.add((float) (0.011 * width));
        TextViewUtil.setText(this, id_tv_number, str_list, color_list, text_size_list);
        str_list = new ArrayList<String>();
        str_list.add("材       质      ");
        str_list.add(detailBean.getMaterial());
        TextViewUtil.setText(this, id_tv_material, str_list, color_list, text_size_list);
        str_list = new ArrayList<String>();
        str_list.add("规       格      ");
        str_list.add(detailBean.getStandard());
        TextViewUtil.setText(this, id_tv_format, str_list, color_list, text_size_list);
        str_list = new ArrayList<String>();
        str_list.add("款       式      ");
        str_list.add(detailBean.getStyle());
        TextViewUtil.setText(this, id_tv_style, str_list, color_list, text_size_list);

    }

    /**
     * 加载幻灯片
     *
     * @param detailBean
     */
    private void initBanner(ProDetailBean detailBean) {
        imgurl = detailBean.getPicArray().split(",");
        for (int i = 0; i < imgurl.length; i++) {
            BannerBean bean = new BannerBean();
            bean.setUrl(UrlAPI.IPADDRESS + imgurl[i]+"_400x400.jpg");
            bean.setContent("图片>>" + i);
            list_banner.add(bean);
            list_img.add(imgurl[i]);
        }
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getApplicationContext(), list_banner.get(list_banner.size() - 1).getUrl()));
        for (int i = 0; i < list_banner.size(); i++) {
            views.add(ViewFactory.getImageView(getApplicationContext(), list_banner.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getApplicationContext(), list_banner.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, list_banner, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
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
    public void addCollSuccess() {
        Msg.showShort(this, "已加入收藏夹");
    }

    @Override
    public void addCollFail() {
        Msg.showShort(this, "加入收藏夹失败");
    }

    @Override
    public void queryCollSuccess() {
        //收藏夹已存在该产品
        Msg.showShort(this, "已加入收藏夹");
    }

    @Override
    public void queryCollFail() {
        //收藏夹不存在该产品
        CollBean collBean = new CollBean(detailBean.getPicArray().split(",")[0], detailBean.getName(),
                detailBean.getPrice(), detailBean.getId());
        presenter.addColl(collBean);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerBean info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                ArrayList<String> img_url = new ArrayList<String>();
                for (int i = 0; i < imgurl.length; i++) {
                    img_url.add(UrlAPI.IPADDRESS + imgurl[i]);
                }
                Intent intent = new Intent(ProDetailAct.this, PicShowAct.class);
                intent.putExtra(ResultCode.PIC_SHOW_POSITION, position);
                intent.putStringArrayListExtra(ResultCode.PIC_SHOW_IMGURL, img_url);
                startActivity(intent);
            }

        }

    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
