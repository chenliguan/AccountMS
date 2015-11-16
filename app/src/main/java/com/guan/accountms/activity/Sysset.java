package com.guan.accountms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.PwdDAO;
import com.guan.accountms.model.Tb_pwd;
import com.guan.accountms.utils.SharedUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class Sysset extends FrameActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_register)
    TextView btnsetCancel;
    @Bind(R.id.txtPwd)
    EditText txtpwd;
    @Bind(R.id.txtPwds)
    EditText txtpwds;
    @Bind(R.id.btnSet)
    Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysset);
        ButterKnife.bind(this);

        /**
         * 初始化变量
         */
        initVariable();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(R.string.sysset);
        btnsetCancel.setText(R.string.cancel);
    }


    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btnSet, R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btnSet:
                // 修改密码
                modifyPwd(view);
                break;

            case R.id.tv_register:
                // 清空密码文本框
                txtpwd.setText("");
                break;

            default:
                break;
        }
    }

    /**
     * 修改密码
     */
    private void modifyPwd(View view) {
        // 创建PwdDAO对象
//        PwdDAO pwdDAO = new PwdDAO(Sysset.this);
        String pwd = txtpwd.getText().toString();
        String pwds = txtpwds.getText().toString();

        // 对密码判断
        if (pwd.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pwd_input));
        } else if (pwds.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pwds_input));
        } else if (pwd != pwds) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pwd_equal));
        } else {
//            // 根据输入的密码创建Tb_pwd对象
//            Tb_pwd tb_pwd = new Tb_pwd(pwd);
//            // 判断数据库中是否已经设置了密码
//            if (pwdDAO.getCount() == 0) {
//                // 添加用户密码
//                pwdDAO.add(tb_pwd);
//            } else {
//                // 修改用户密码
//                pwdDAO.update(tb_pwd);
//            }
            // 写入密码
            SharedUtil.setLoginPass(this,pwd);
            // 弹出信息提示
            showMsg(getString(R.string.set_pass_ok));
            finish();
        }
    }

}
