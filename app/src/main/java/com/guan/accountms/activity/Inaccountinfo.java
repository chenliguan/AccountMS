package com.guan.accountms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.InaccountDAO;
import com.guan.accountms.model.Tb_inaccount;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 我的收入类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class Inaccountinfo extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lvinaccountinfo)
    ListView lvinfo;

    // 创建字符串，记录管理类型
    String strType = "";
    // 定义一个常量，用来作为请求码
    public static final String FLAG = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inaccountinfo);
        ButterKnife.bind(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 调用自定义方法显示收入信息
         */
        ShowInfo(R.id.btnininfo);
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(R.string.inaccountinfo);
    }

    /**
     * 调用自定义方法显示收入信息
     */
    private void ShowInfo(int intType) {
        // 定义字符串数组，用来存储收入信息
        String[] strInfos = null;
        // 创建ArrayAdapter对象
        ArrayAdapter<String> arrayAdapter = null;
        // 为strType变量赋值
        strType = "btnininfo";
        // 创建InaccountDAO对象
        InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this);
        // 获取所有收入信息，并存储到List泛型集合中
        List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
                (int) inaccountinfo.getCount());
        // 设置字符串数组的长度
        strInfos = new String[listinfos.size()];
        // 定义一个开始标识
        int m = 0;
        for (Tb_inaccount tb_inaccount : listinfos) {
            // 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
            strInfos[m] = tb_inaccount.getid() + "|" + tb_inaccount.getType()
                    + " " + String.valueOf(tb_inaccount.getMoney()) + "元     "
                    + tb_inaccount.getTime();
            m++;// 标识加1
        }
        // 使用字符串数组初始化ArrayAdapter对象
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strInfos);
        // 为ListView列表设置数据源
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
    @OnItemClick(R.id.lvinaccountinfo)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
        // 记录收入信息
        String strInfo = String.valueOf(((TextView) view).getText());
        // 从收入信息中截取收入编号
        String strid = strInfo.substring(0, strInfo.indexOf('|'));
        // 创建Intent对象
        Intent intent = new Intent(Inaccountinfo.this, InfoManage.class);
        // 设置传递数据
        intent.putExtra(FLAG, new String[]{strid, strType});
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 显示收入信息
        ShowInfo(R.id.btnininfo);
    }
}
