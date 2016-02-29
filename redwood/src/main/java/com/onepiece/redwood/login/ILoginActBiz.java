package com.onepiece.redwood.login;

/**
 * Created by Administrator on 2015/8/28.
 */
public interface ILoginActBiz {
   void getUserInfo(String uname,String upsw,OnLoginListenerI onLoginListener);
}
