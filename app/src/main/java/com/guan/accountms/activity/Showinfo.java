package com.guan.accountms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guan.accountms.R;
import com.guan.accountms.dao.FlagDAO;
import com.guan.accountms.dao.InaccountDAO;
import com.guan.accountms.dao.OutaccountDAO;
import com.guan.accountms.model.Tb_flag;
import com.guan.accountms.model.Tb_inaccount;
import com.guan.accountms.model.Tb_outaccount;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 数据管理类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/14
 * @Version 1.0
 */
public class Showinfo extends FrameActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btnoutinfo)
    TextView btnoutinfo;
    @Bind(R.id.btnininfo)
    TextView btnininfo;
    @Bind(R.id.btnflaginfo)
    TextView btnflaginfo;
    @Bind(R.id.iv_bottom_line)
    ImageView ivBottomLine;
    @Bind(R.id.lvinfo)
    ListView lvinfo;

    private int mOffset;
    private int mPosition;
    private int mCurrIndex;
    private int mPositionOne;
    private int mPositionTwo;
    private int mColorMainBlue;
    private int mBottomLineWidth;
    private int mColorMainTextGrey;
    // 创建字符串，记录管理类型
    String strType = "";
    // 定义一个常量，用来作为请求码
    public static final String FLAG = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);
        ButterKnife.bind(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 初始化顶栏宽度
         */
        initWidth();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        mCurrIndex = 0;
        tvTitle.setText(R.string.showinfo);
        mColorMainBlue = getResources().getColor(R.color.main_blue);
        mColorMainTextGrey = getResources().getColor(R.color.texts_grey);
        // 默认显示支出信息
        ShowInfo(R.id.btnoutinfo);
        btnoutinfo.setTextColor(mColorMainBlue);
    }

    /**
     * 初始化顶栏宽度
     */
    private void initWidth() {
        mBottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        mOffset = (int) ((screenW / 3.0 - mBottomLineWidth) / 2);
        mPositionOne = (int) (screenW / 3.0);
        mPositionTwo = mPositionOne * 2;

        // 编码中动态设置横条的宽度
        ivBottomLine.setLayoutParams(new LinearLayout.LayoutParams(mPositionOne, 10));
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btnoutinfo, R.id.btnininfo, R.id.btnflaginfo})
    public void onClick(View view) {

        Animation animation = null;

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btnoutinfo:
                // 显示支出信息
                ShowInfo(R.id.btnoutinfo);

                // 顶部导航栏
                mPosition = 0;
                if (mCurrIndex == 1)
                    animation = new TranslateAnimation(mPositionOne, 0, 0, 0);
                else if (mCurrIndex == 2)
                    animation = new TranslateAnimation(mPositionTwo, 0, 0, 0);
                btnoutinfo.setTextColor(mColorMainBlue);
                btnininfo.setTextColor(mColorMainTextGrey);
                btnflaginfo.setTextColor(mColorMainTextGrey);
                // 设置动画
                startAnimation(animation);
                break;

            case R.id.btnininfo:
                // 显示收入信息
                ShowInfo(R.id.btnininfo);

                // 顶部导航栏
                mPosition = 1;
                if (mCurrIndex == 0)
                    animation = new TranslateAnimation(mOffset, mPositionOne, 0, 0);
                else if (mCurrIndex == 2)
                    animation = new TranslateAnimation(mPositionTwo, mPositionOne, 0, 0);
                btnoutinfo.setTextColor(mColorMainTextGrey);
                btnininfo.setTextColor(mColorMainBlue);
                btnflaginfo.setTextColor(mColorMainTextGrey);
                // 设置动画
                startAnimation(animation);
                break;

            case R.id.btnflaginfo:
                // 显示便签信息
                ShowInfo(R.id.btnflaginfo);

                // 顶部导航栏
                mPosition = 2;
                if (mCurrIndex == 0)
                    animation = new TranslateAnimation(mOffset, mPositionTwo, 0, 0);
                else if (mCurrIndex == 1)
                    animation = new TranslateAnimation(mPositionOne, mPositionTwo, 0, 0);
                btnoutinfo.setTextColor(mColorMainTextGrey);
                btnininfo.setTextColor(mColorMainTextGrey);
                btnflaginfo.setTextColor(mColorMainBlue);
                // 设置动画
                startAnimation(animation);
                break;
            default:
                break;
        }
    }

    /**
     * 设置动画
     */
    private void startAnimation(Animation animation) {
        // 记录当前position，作为下一项的前position
        mCurrIndex = mPosition;
        if (animation != null) {
            animation.setFillAfter(true);
        }
        if (animation != null) {
            animation.setDuration(300);
        }
        ivBottomLine.startAnimation(animation);
    }

    /**
     * ListView点击触发监听
     *
     * @param adapterView
     * @param view
     * @param position
     * @param arg3
     */
    @OnItemClick(R.id.lvinfo)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
        // 记录单击的项信息
        String strInfo = String.valueOf(((TextView) view).getText());
        // 从项信息中截取编号
        String strid = strInfo.substring(0, strInfo.indexOf('|'));
        Intent intent = null;
        // 判断如果是支出或者收入信息
        if (strType == "btnoutinfo" | strType == "btnininfo") {
            intent = new Intent(Showinfo.this, InfoManage.class);
            intent.putExtra(FLAG, new String[]{strid, strType});
        } else if (strType == "btnflaginfo") {
            intent = new Intent(Showinfo.this, FlagManage.class);
            intent.putExtra(FLAG, strid);
        }
        startActivity(intent);
    }

    /**
     * 用来根据传入的管理类型，显示相应的信息
     */
    private void ShowInfo(int intType) {
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;

        // 以intType为条件进行判断
        switch (intType) {
            // 如果是btnoutinfo按钮
            case R.id.btnoutinfo:
                strType = "btnoutinfo";
                // 创建OutaccountDAO对象
                OutaccountDAO outaccountinfo = new OutaccountDAO(Showinfo.this);
                // 获取所有支出信息，并存储到List泛型集合中
                List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
                        (int) outaccountinfo.getCount());
                // 设置字符串数组的长度
                strInfos = new String[listoutinfos.size()];
                int i = 0;
                for (Tb_outaccount tb_outaccount : listoutinfos) {
                    // 将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strInfos[i] = tb_outaccount.getid() + "|"
                            + tb_outaccount.getType() + " "
                            + String.valueOf(tb_outaccount.getMoney()) + "元     "
                            + tb_outaccount.getTime();
                    i++;
                }
                break;

            case R.id.btnininfo:
                strType = "btnininfo";
                InaccountDAO inaccountinfo = new InaccountDAO(Showinfo.this);
                // 获取所有收入信息，并存储到List泛型集合中
                List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
                        (int) inaccountinfo.getCount());
                strInfos = new String[listinfos.size()];
                // 定义一个开始标识
                int m = 0;
                for (Tb_inaccount tb_inaccount : listinfos) {
                    // 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strInfos[m] = tb_inaccount.getid() + "|"
                            + tb_inaccount.getType() + " "
                            + String.valueOf(tb_inaccount.getMoney()) + "元     "
                            + tb_inaccount.getTime();
                    m++;
                }
                break;

            case R.id.btnflaginfo:
                strType = "btnflaginfo";
                FlagDAO flaginfo = new FlagDAO(Showinfo.this);
                // 获取所有便签信息，并存储到List泛型集合中
                List<Tb_flag> listFlags = flaginfo.getScrollData(0,
                        (int) flaginfo.getCount());
                strInfos = new String[listFlags.size()];
                int n = 0;
                for (Tb_flag tb_flag : listFlags) {
                    // 将便签相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strInfos[n] = tb_flag.getid() + "|" + tb_flag.getFlag();
                    // 判断便签信息的长度是否大于15
                    if (strInfos[n].length() > 15)
                        strInfos[n] = strInfos[n].substring(0, 15) + "……";
                    n++;
                }
                break;
        }
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strInfos);
        // 为ListView列表设置数据源
        lvinfo.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 显示支出信息
        ShowInfo(R.id.btnoutinfo);
    }
}
