package com.onepiece.redwood.self;

import com.onepiece.redwood.self.update.OnUpdateUserListener;

import java.util.Map;

import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface ISelfBiz {
    void upHeadImage(String token,TypedFile file,OnUpImageListener onUpImageListener);
    void updateUserInfo(Map<String,String> map , OnUpdateUserListener onUpdateUserListener);
}
