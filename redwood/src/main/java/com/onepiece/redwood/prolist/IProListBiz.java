package com.onepiece.redwood.prolist;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
public interface IProListBiz {
   //获取产品信息
   void getProListInfo(Map<String,String> map,OnProListListener onProListListener);



}
