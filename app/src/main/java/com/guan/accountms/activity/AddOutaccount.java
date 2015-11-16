package com.guan.accountms.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * 新增支出类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class AddOutaccount extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.txtMoney)
    EditText txtMoney;
    @Bind(R.id.txtTime)
    EditText txtTime;
    @Bind(R.id.spType)
    Spinner spType;
    @Bind(R.id.txtAddress)
    EditText txtAddress;
    @Bind(R.id.txtMark)
    EditText txtMark;

    private int mYear;
    private int mMonth;
    private int mDay;
    // 创建日期对话框常量
    protected static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoutaccount);
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
        tvTitle.setText(R.string.addoutaccount);
        tvRegister.setText(R.string.cancel);
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
    @OnClick({R.id.iv_back, R.id.txtTime, R.id.btnSave, R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.txtTime:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.btnSave:
                // 保存
                save();
                break;

            case R.id.tv_register:
                // 清空便签文本框
                cancel();
                break;
        }
    }

    /**
     * 取消
     */
    private void cancel() {
        // 设置金额文本框为空
        txtMoney.setText("");
        // 为金额文本框设置提示
        txtMoney.setHint("0.00");
        // 设置时间文本框为空
        txtTime.setText("");
        // 为时间文本框设置提示
        txtTime.setHint("2011-01-01");
        // 设置地点文本框为空
        txtAddress.setText("");
        // 设置备注文本框为空
        txtMark.setText("");
        // 设置类别下拉列表默认选择第一项
        spType.setSelection(0);
    }

    /**
     * 保存
     */
    private void save() {
        String strMoney = txtMoney.getText().toString();
        // 判断金额不为空
        if (!strMoney.isEmpty()) {
            // 创建OutaccountDAO对象
            OutaccountDAO outaccountDAO = new OutaccountDAO(
                    AddOutaccount.this);
            // 创建Tb_outaccount对象
            Tb_outaccount tb_outaccount = new Tb_outaccount(
                    outaccountDAO.getMaxId() + 1, Double
                    .parseDouble(strMoney), txtTime
                    .getText().toString(), spType
                    .getSelectedItem().toString(),
                    txtAddress.getText().toString(), txtMark
                    .getText().toString());
            // 添加支出信息
            outaccountDAO.add(tb_outaccount);
            // 弹出信息提示
            showMsg(getString(R.string.add_out_count_success));
            finish();
        } else
            showMsg(getString(R.string.money_put));
    }


    // 重写onCreateDialog方法
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            // 弹出日期选择对话框
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
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
