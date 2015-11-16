package com.guan.accountms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.FlagDAO;
import com.guan.accountms.model.Tb_flag;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 便签管理类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/14
 * @Version 1.0
 */
public class FlagManage extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.txtFlagManage)
    EditText txtFlag;
    @Bind(R.id.btnFlagManageEdit)
    Button btnEdit;

    // 创建字符串，表示便签的id
    private String strid;
    private FlagDAO flagDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flagmanage);
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
        tvTitle.setText(R.string.flagmanage);
        tvRegister.setText(R.string.cancel);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strid = bundle.getString(Showinfo.FLAG);
        // 创建FlagDAO对象
        flagDAO = new FlagDAO(FlagManage.this);
        // 根据便签id查找便签信息，并显示在文本框中
        txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back,R.id.btnFlagManageEdit,R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btnFlagManageEdit:
                // 保存
                modify();
                break;

            case R.id.tv_register:
                // 根据指定的id删除便签信息
                flagDAO.detele(Integer.parseInt(strid));
                showMsg(getString(R.string.delete_flag_success));
                break;
        }
    }

    private void modify() {
        // 创建Tb_flag对象
        Tb_flag tb_flag = new Tb_flag();
        // 设置便签id
        tb_flag.setid(Integer.parseInt(strid));
        // 设置便签值
        tb_flag.setFlag(txtFlag.getText().toString());
        // 修改便签信息
        flagDAO.update(tb_flag);
        // 弹出信息提示
        showMsg(getString(R.string.modify_flag_success));
    }
}
