package com.guan.accountms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.guan.accountms.common.Constant;

/**
 * 基础类封装业务无关的方法
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class BaseActivity extends Activity {

    /**
     * 把最常用的与业务无关的方法封装,简化编码编写过程
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Toast公共方法
     * @param pMsg
     */
    public void showMsg(String pMsg) {
        Toast.makeText(BaseActivity.this, pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * intent 跳转Activity公共方法
     * @param pClass
     */
    public void openActivity(Class<?> pClass) {
        Intent _intent = new Intent(this,pClass);
        startActivity(_intent);
    }

    /**
     * intent 跳转Activity并finish()公共方法
     * @param pClass
     */
    public void openActivityFn(Class<?> pClass) {
        Intent _intent = new Intent(this,pClass);
        startActivity(_intent);
        finish();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
