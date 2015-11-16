package com.guan.accountms.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guan.accountms.R;
import com.guan.accountms.dao.FlagDAO;
import com.guan.accountms.model.Tb_flag;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收支便签类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class Accountflag extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.txtFlag)
    EditText txtFlag;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountflag);
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
        tvTitle.setText(R.string.title_feek_back);
        tvRegister.setText(R.string.cancel);
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back,R.id.btnflagSave,R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btnflagSave:
                // 保存
                save();
                break;

            case R.id.tv_register:
                // 清空便签文本框
                txtFlag.setText("");
                break;
        }
    }

    /**
     * 保存
     */
    private void save() {
        String strFlag = txtFlag.getText().toString();
        if (!strFlag.isEmpty()) {
            // 创建FlagDAO对象
            FlagDAO flagDAO = new FlagDAO(Accountflag.this);
            // 创建Tb_flag对象
            Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId() + 1, strFlag);
            // 添加便签信息
            flagDAO.add(tb_flag);
            showMsg(getString(R.string.add_flag_success));
            finish();
        } else
            showMsg(getString(R.string.flag_input));
    }
}

