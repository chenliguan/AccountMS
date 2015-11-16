package com.guan.accountms.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.InaccountDAO;
import com.guan.accountms.dao.OutaccountDAO;
import com.guan.accountms.model.Tb_inaccount;
import com.guan.accountms.model.Tb_outaccount;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收支管理类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class InfoManage extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvtitle;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.txtInOutMoney)
    EditText txtMoney;
    @Bind(R.id.txtInOutTime)
    EditText txtTime;
    @Bind(R.id.spInOutType)
    Spinner spType;
    @Bind(R.id.txtInOut)
    EditText txtHA;
    @Bind(R.id.txtInOutMark)
    EditText txtMark;
    @Bind(R.id.tvInOut)
    TextView textView;

    private int mYear;// 年
    private int mMonth;// 月
    private int mDay;// 日

    String[] strInfos;
    // 定义两个字符串变量，分别用来记录信息编号和管理类型
    String strid, strType;
    // 创建日期对话框常量
    protected static final int DATE_DIALOG_ID = 0;
    // 创建OutaccountDAO对象
    OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);
    // 创建InaccountDAO对象
    InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomanage);// 设置布局文件
        ButterKnife.bind(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 绑定数据
         */
        bindData();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvtitle.setText(R.string.infomanage);
        tvRegister.setText(R.string.delete);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 获取Bundle中记录的信息
        strInfos = bundle.getStringArray(Showinfo.FLAG);
        // 记录id
        strid = strInfos[0];
        // 记录类型
        strType = strInfos[1];
        if (strType.equals("btnoutinfo"))// 如果类型是btnoutinfo
        {
            // 设置标题为“支出管理”
            tvtitle.setText("支出管理");
            // 设置“地点/付款方”标签文本为“地 点：”
            textView.setText("地  点：");
            // 根据编号查找支出信息，并存储到Tb_outaccount对象中
            Tb_outaccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
            // 显示金额
            txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));
            txtTime.setText(tb_outaccount.getTime());
            spType.setPrompt(tb_outaccount.getType());
            txtHA.setText(tb_outaccount.getAddress());
            txtMark.setText(tb_outaccount.getMark());
        } // 如果类型是btnininfo
        else if (strType.equals("btnininfo")) {
            tvtitle.setText("收入管理");
            textView.setText("付款方：");
            // 根据编号查找收入信息，并存储到Tb_outaccount对象中
            Tb_inaccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));
            txtTime.setText(tb_inaccount.getTime());
            spType.setPrompt(tb_inaccount.getType());
            txtHA.setText(tb_inaccount.getHandler());
            txtMark.setText(tb_inaccount.getMark());
        }
        // 获取当前系统日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // 显示当前系统时间
        updateDisplay();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.txtInOutTime, R.id.btnInOutEdit, R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.txtInOutTime:
                // 显示日期选择对话框
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.btnInOutEdit:
                // 修改
                modify();
                break;

            case R.id.tv_register:
                // 删除
                delete();
                break;
        }
    }

    /**
     * 修改
     */
    private void modify() {
        // 判断类型如果是btnoutinfo
        if (strType.equals("btnoutinfo")) {
            // 创建Tb_outaccount对象
            Tb_outaccount tb_outaccount = new Tb_outaccount();
            // 设置编号
            tb_outaccount.setid(Integer.parseInt(strid));
            tb_outaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
            tb_outaccount.setTime(txtTime.getText().toString());
            tb_outaccount.setType(spType.getSelectedItem().toString());
            tb_outaccount.setAddress(txtHA.getText().toString());
            tb_outaccount.setMark(txtMark.getText().toString());
            outaccountDAO.update(tb_outaccount);
        } else if (strType.equals("btnininfo")) {
            // 判断类型如果是btnininfo
            Tb_inaccount tb_inaccount = new Tb_inaccount();
            tb_inaccount.setid(Integer.parseInt(strid));
            tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
            tb_inaccount.setTime(txtTime.getText().toString());
            tb_inaccount.setType(spType.getSelectedItem().toString());
            tb_inaccount.setHandler(txtHA.getText().toString());
            tb_inaccount.setMark(txtMark.getText().toString());
            // 更新收入信息
            inaccountDAO.update(tb_inaccount);
        }
        // 弹出信息提示
        showMsg(getString(R.string.modify_data_success));
    }

    /**
     * 删除
     */
    private void delete() {
        // 判断类型如果是btnoutinfo
        if (strType.equals("btnoutinfo")) {
            // 根据编号删除支出信息
            outaccountDAO.detele(Integer.parseInt(strid));
        } else if (strType.equals("btnininfo")) {
            // 判断类型如果是btnininfo
            inaccountDAO.detele(Integer.parseInt(strid));
        }
        showMsg(getString(R.string.delete_data_success));
    }

    // 重写onCreateDialog方法
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            // 弹出日期选择对话框
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    private void updateDisplay() {
        // 显示设置的时间
        txtTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}
