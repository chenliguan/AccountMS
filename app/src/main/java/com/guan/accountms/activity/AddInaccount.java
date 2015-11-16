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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guan.accountms.R;
import com.guan.accountms.dao.InaccountDAO;
import com.guan.accountms.model.Tb_inaccount;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增收入类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class AddInaccount extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.txtInMoney)
    EditText txtInMoney;
    @Bind(R.id.txtInTime)
    EditText txtInTime;
    @Bind(R.id.spInType)
    Spinner spInType;
    @Bind(R.id.txtInHandler)
    EditText txtInHandler;
    @Bind(R.id.txtInMark)
    EditText txtInMark;

    private int mYear;
    private int mMonth;
    private int mDay;
    // 创建日期对话框常量
    protected static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);
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
        tvTitle.setText(R.string.addinaccount);
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
    @OnClick({R.id.iv_back, R.id.txtInTime, R.id.btnInSave, R.id.tv_register})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.txtInTime:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.btnInSave:
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
        txtInMoney.setText("");
        txtInMoney.setHint("0.00");
        txtInTime.setText("");
        txtInTime.setHint("2011-01-01");
        txtInHandler.setText("");
        txtInMark.setText("");
        spInType.setSelection(0);
    }

    /**
     * 保存
     */
    private void save() {
        String strInMoney = txtInMoney.getText().toString();
        if (!strInMoney.isEmpty()) {
            // 创建InaccountDAO对象
            InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
            // 创建Tb_inaccount对象
            Tb_inaccount tb_inaccount = new Tb_inaccount(
                    inaccountDAO.getMaxId() + 1, Double
                    .parseDouble(strInMoney), txtInTime
                    .getText().toString(), spInType
                    .getSelectedItem().toString(),
                    txtInHandler.getText().toString(),
                    txtInMark.getText().toString());
            // 添加收入信息
            inaccountDAO.add(tb_inaccount);
            // 弹出信息提示
            showMsg(getString(R.string.add_count_success));
            finish();
        } else
            showMsg(getString(R.string.money_input));
    }

    // 重写onCreateDialog方法
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
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
        txtInTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}
