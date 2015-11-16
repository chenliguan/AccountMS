package com.guan.accountms.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.guan.accountms.common.Constant;

/**
 * 数据持久化Utils工具类
 *
 * @author Guan
 * @file com.guan.o2o.utils
 * @date 2015/9/19
 * @Version 1.0
 */
public class SharedUtil {

    /**
     * 写入登录信息
     */
    public static void setLoginInfo(Context context, String loginPhone, String loginCode) {
        // 1.实例化SharedPreferences对象&SharedPreferences.Editor对象
        Editor editor = context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE).edit();
        // 2.editor.put()存入数据
        editor.putString(Constant.SHARED_KEY_PHONE, loginPhone);
        editor.putString(Constant.SHARED_KEY_PASS, loginCode);
        // 3.commit()提交修改
        editor.apply();
    }

    /**
     * 写入密码
     */
    public static void setLoginPass(Context context,String loginCode) {
        // 1.实例化SharedPreferences对象&SharedPreferences.Editor对象
        Editor editor = context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE).edit();
        // 2.editor.put()存入数据
        editor.putString(Constant.SHARED_KEY_PASS, loginCode);
        // 3.commit()提交修改
        editor.apply();
    }

    /**
     * 获取登录手机号
     */
    public static String getLoginPhone(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getString(Constant.SHARED_KEY_PHONE, "");
    }

    /**
     * 获取登录密码
     */
    public static String getLoginPass(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getString(Constant.SHARED_KEY_PASS, "");
    }
}
