package com.onepiece.redwood.cart;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.onepiece.redwood.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2015/8/28.
 */
public class CartDao {
    Context context;
    DatabaseHelper helper;
    Dao<CartBean, Integer> dao;

    public CartDao(Context context) {
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            dao = helper.getDao(CartBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入购物车ByproId
     */
    public int addCart(CartBean cartBean) {
        try {
            int i = dao.create(cartBean);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询购物车ByproId
     */
    public Boolean queryByProId(int proId) {
        try {
            List<CartBean> list = dao.queryBuilder().where().eq("proId", proId).query();
            if (list.size() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询购物车
     */
    public List<CartBean> queryAll() {
        try {
            List<CartBean> cartBeans = dao.queryForAll();
            return cartBeans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除购物车ById
     */
    public int delCartByproId(int proId) {

        DeleteBuilder<CartBean, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("proId", proId);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 批量删除购物车
     */
    public int delCartByproAllId(List<Integer> prolist) {

        DeleteBuilder<CartBean, Integer> deleteBuilder = dao.deleteBuilder();

        try {
            deleteBuilder.where().in("proId",prolist);
         //   deleteBuilder.where().eq("proId", proId);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 修改购物车某个产品的数量
     */
    public int addCountByproId(int count, int proId) {
        StringBuffer sb = new StringBuffer("update cart set count=");
        sb.append(count);
        sb.append(" where proId=");
        sb.append(proId);
        try {
            int i = dao.updateRaw(sb.toString());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取购物车的总价
     */
    public Double getAllPrice() {
        try {
            Double d=0d;
            List<CartBean> cartBeans = dao.queryForAll();
            for(CartBean cartBean:cartBeans){
                d = cartBean.getPrice()*cartBean.getCount()+d;
            }
            return d;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return 0d;
    }
}
