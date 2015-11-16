package com.guan.accountms.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.utils.SharedUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class RegisterActivity extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.txtId)
    EditText txtId;
    @Bind(R.id.txtPass)
    EditText txtPass;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        tvTitle.setText(R.string.title_register);
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btnRegister})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btnRegister:
                // 注册
                register(view);
                break;

            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register(View view) {
        // 对手机号码与验证码验证
        String phone = txtId.getText().toString();
        String pass = txtPass.getText().toString();
        if (phone.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.phone_input));
        } else if (pass.isEmpty()) {
            showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pwd_input));
        } else {
            // 写入登录信息
            SharedUtil.setLoginInfo(this,phone,pass);
            openActivity(MainActivity.class);
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
