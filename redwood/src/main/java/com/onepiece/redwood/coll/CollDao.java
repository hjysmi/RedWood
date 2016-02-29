package com.onepiece.redwood.coll;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.onepiece.redwood.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CollDao {
    Context context;
    DatabaseHelper helper;
    Dao<CollBean, Integer> dao;

    public CollDao(Context context) {
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            dao = helper.getDao(CollBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入购物车BYId
     */
    public int addCollById(CollBean collBean) {
        try {
            int i = dao.create(collBean);
            return i;
        } catch (SQLException e) {
            return 0;
        }

    }

    /**
     * 查询收藏夹ById
     */
    public Boolean queryCollById(int proId) {
        try {
            List<CollBean> list = dao.queryBuilder().where().eq("proId", proId).query();
            if (list.size() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * 查询收藏夹
     */
    public List<CollBean> queryCollAll() {
        try {
            List<CollBean> collBeans = dao.queryForAll();
            return collBeans;
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * 删除购物车ById
     */
    public int delCollByproId(int proId) {

        DeleteBuilder<CollBean, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("proId", proId);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }

    }


}
