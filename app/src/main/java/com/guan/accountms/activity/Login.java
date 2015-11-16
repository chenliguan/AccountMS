package com.guan.accountms.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.PwdDAO;
import com.guan.accountms.utils.SharedUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class Login extends FrameActivity {

    @Bind(R.id.txtId)
    EditText txtId;
    @Bind(R.id.txtLogin)
    EditText txtlogin;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        mExitTime = 0;
        tvTitle.setText(R.string.title_login);
        ivBack.setVisibility(View.INVISIBLE);
        // 默认记住密码
        txtId.setText(SharedUtil.getLoginPhone(this));
        txtlogin.setText(SharedUtil.getLoginPass(this));
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.btnLogin, R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                // 登录
                login(view);
                break;

            case R.id.tv_register:
                openActivity(RegisterActivity.class);
                break;

            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login(View view) {
//        // 创建PwdDAO对象
//        PwdDAO pwdDAO = new PwdDAO(Login.this);
//        // 判断是否有密码及是否输入了密码
//        if ((pwdDAO.getCount() == 0 || pwdDAO.find().getPassword().isEmpty())
//                && txtlogin.getText().toString().isEmpty()) {
//            openActivityFn(MainActivity.class);
//        } else {
//            // 判断输入的密码是否与数据库中的密码一致
//            if (pwdDAO.find().getPassword().equals(txtlogin.getText().toString()))
//                openActivityFn(MainActivity.class);
//            else
//                showMsg(getString(R.string.input_pass));
//        }

        // 对手机号码与验证码验证
        String phone = txtId.getText().toString();
        String pass = txtlogin.getText().toString();
        if (phone.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.phone_input));
        } else if (pass.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pwd_input));
        } else {
            // 判断输入的密码是否与数据库中的密码一致
            if (SharedUtil.getLoginPhone(this).equals(phone)
                    & SharedUtil.getLoginPass(this).equals(pass))
                openActivityFn(MainActivity.class);
            else
                showMsg(getString(R.string.input_pass));
        }
    }

    /**
     * 再按一次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
                showMsg(getResources().getString(R.string.msg_repress));
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
