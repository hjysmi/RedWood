package com.onepiece.redwood.customer.contracts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.contracts.lib.CharacterParser;
import com.onepiece.redwood.customer.contracts.lib.SortModel;
import com.onepiece.redwood.customer.create.ICreateCustomerBiz;
import com.onepiece.redwood.customer.create.OnCreateCustomerListener;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/25.
 */
public class ContractBiz implements IContractBiz,ICreateCustomerBiz {
    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private List<SortModel> mSortList = new ArrayList<SortModel>();
    private CharacterParser characterParser;
    @Override
    public void getPhoneContacts(final Context context, final OnPhoneContactsListener onPhoneContactsListener) {
        new Thread(){
            @Override
            public void run() {
                {
                    //实例化汉字转拼音类
                    characterParser = CharacterParser.getInstance();
                    ContentResolver resolver = context.getContentResolver();

                    // 获取手机联系人
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);


                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {

                            //得到手机号码
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            //当手机号码为空的或者为空字段 跳过当前循环
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;

                            //得到联系人名称
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                            //得到联系人ID
                            Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                            //得到联系人头像ID
                            Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                            //得到联系人头像Bitamp
                            Bitmap contactPhoto = null;
                            SortModel sortModel = new SortModel();
                            //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                            if (photoid > 0) {
                                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                                sortModel.setUri(uri);
                                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                                contactPhoto = BitmapFactory.decodeStream(input);
                            } else {
                                contactPhoto = null;
                            }

                            sortModel.setContactId(contactid);
                            sortModel.setDesplayName(contactName);
                            sortModel.setPhotoId(photoid);
                            sortModel.setPhoneNum(phoneNumber);
                            sortModel.setImgBitMap(contactPhoto);
                            sortModel.setName(contactName);
                            //汉字转换成拼音
                            String pinyin = characterParser.getSelling(contactName);
                            String sortString = pinyin.substring(0, 1).toUpperCase();

                            // 正则表达式，判断首字母是否是英文字母
                            if (sortString.matches("[A-Z]")) {
                                sortModel.setSortLetters(sortString.toUpperCase());
                            } else {
                                sortModel.setSortLetters("#");
                            }

                            mSortList.add(sortModel);
                        }
                        phoneCursor.close();
                        Logger.e("url="+mSortList.toString());
                        onPhoneContactsListener.OnSuccess(mSortList);
                    } else {
                        onPhoneContactsListener.OnFail();
                    }
                }

            }
        }.start();
    }

    @Override
    public void createCustomerInfo(Map<String, String> map, final OnCreateCustomerListener onCreateCustomerListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.CreateCustomerInfo(map, new Callback<DataBean<CustomerBean>>() {
            @Override
            public void success(DataBean<CustomerBean> customerBeanDataBean, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }




}
