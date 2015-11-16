package com.guan.accountms.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guan.accountms.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 系统主窗体类
 *
 * @author Guan
 * @file com.guan.accountms.activity
 * @date 2015/11/13
 * @Version 1.0
 */
public class MainActivity extends FrameActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.gvInfo)
    GridView gvInfo;

    private long mExitTime;
    // 定义字符串数组，存储系统功能
    String[] titles = new String[]{"新增支出", "新增收入", "我的支出",
            "我的收入","数据管理", "系统设置", "收支便签", "退出"};
    // 定义int数组，存储功能对应的图标
    int[] images = new int[]{R.mipmap.addoutaccount, R.mipmap.addinaccount, R.mipmap.outaccountinfo,
            R.mipmap.inaccountinfo, R.mipmap.showinfo, R.mipmap.sysset, R.mipmap.accountflag, R.mipmap.exit};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        mExitTime = 0;
        tvTitle.setText(R.string.main);
        ivBack.setVisibility(View.INVISIBLE);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        pictureAdapter adapter = new pictureAdapter(titles, images, this);
        // 为GridView设置数据源
        gvInfo.setAdapter(adapter);
    }

    /**
     * GridView触发监听
     * @param adapterView
     * @param view
     * @param position
     * @param arg3
     */
    @OnItemClick(R.id.gvInfo)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {

            switch (position) {
                case 0:
                    openActivity(AddOutaccount.class);
                    break;

                case 1:
                    openActivity(AddInaccount.class);
                    break;

                case 2:
                    openActivity(Outaccountinfo.class);
                    break;

                case 3:
                    openActivity(Inaccountinfo.class);
                    break;

                case 4:
                    openActivity(Showinfo.class);
                    break;

                case 5:
                    openActivity(Sysset.class);
                    break;

                case 6:
                    openActivity(Accountflag.class);
                    break;

                case 7:
                    finish();
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

/**
 * 创建基于BaseAdapter的子类
 */
class pictureAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Picture> pictures;

    // 为类创建构造函数
    public pictureAdapter(String[] titles, int[] images, Context context) {
        super();
        // 初始化泛型集合对象
        pictures = new ArrayList<Picture>();
        // 初始化LayoutInflater对象
        inflater = LayoutInflater.from(context);
        // 遍历图像数组
        for (int i = 0; i < images.length; i++) {
            // 使用标题和图像生成Picture对象
            Picture picture = new Picture(titles[i], images[i]);
            // 将Picture对象添加到泛型集合中
            pictures.add(picture);
        }
    }

    // 获取泛型集合的长度
    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int arg0) {
        // 获取泛型集合指定索引处的项
        return pictures.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // 返回泛型集合的索引
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (arg1 == null) {
            arg1 = inflater.inflate(R.layout.gvitem, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);
            viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);
            arg1.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) arg1.getTag();
        }
        viewHolder.title.setText(pictures.get(arg0).getTitle());
        viewHolder.image.setImageResource(pictures.get(arg0).getImageId());
        return arg1;
    }
}

/**
 * 创建ViewHolder类
 */
class ViewHolder {
    public TextView title;
    public ImageView image;
}

/**
 * 创建Picture类
 */
class Picture {
    private String title;
    private int imageId;

    public Picture() {
        super();
    }

    public Picture(String title, int imageId) {
        super();
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setimageId(int imageId) {
        this.imageId = imageId;
    }
}