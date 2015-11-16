package com.guan.accountms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.OutaccountDAO;
import com.guan.accountms.model.Tb_outaccount;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 我的支出类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class Outaccountinfo extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lvoutaccountinfo)
    ListView lvinfo;

    // 创建字符串，记录管理类型
    String strType = "";
    // 定义一个常量，用来作为请求码
    public static final String FLAG = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outaccountinfo);// 设置布局文件
        ButterKnife.bind(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 调用自定义方法显示收入信息
         */
        ShowInfo(R.id.btnoutinfo);
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(R.string.outaccountinfo);
    }

    /**
     * 调用自定义方法显示收入信息
     */
    private void ShowInfo(int intType) {
        // 定义字符串数组，用来存储支出信息
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        // 为strType变量赋值
        strType = "btnoutinfo";
        // 创建OutaccountDAO对象
        OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this);
        // 获取所有支出信息，并存储到List泛型集合中
        List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
                (int) outaccountinfo.getCount());
        // 设置字符串数组的长度
        strInfos = new String[listoutinfos.size()];
        // 定义一个开始标识
        int i = 0;
        for (Tb_outaccount tb_outaccount : listoutinfos) {
            // 将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
            strInfos[i] = tb_outaccount.getid() + "|" + tb_outaccount.getType()
                    + " " + String.valueOf(tb_outaccount.getMoney()) + "元     "
                    + tb_outaccount.getTime();
            // 标识加1
            i++;
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);
    }

    /**
     * 监听实现
     */
    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        finish();
    }

    /**
     * ListView点击触发监听
     *
     * @param adapterView
     * @param view
     * @param position
     * @param arg3
     */
    @OnItemClick(R.id.lvoutaccountinfo)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
        // 记录支出信息
        String strInfo = String.valueOf(((TextView) view).getText());
        // 从支出信息中截取支出编号
        String strid = strInfo.substring(0, strInfo.indexOf('|'));
        Intent intent = new Intent(Outaccountinfo.this,
                InfoManage.class);
        intent.putExtra(FLAG, new String[]{strid, strType});
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 显示收入信息
        ShowInfo(R.id.btnoutinfo);
    }
}