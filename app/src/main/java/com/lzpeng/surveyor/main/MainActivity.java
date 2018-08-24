package com.lzpeng.surveyor.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.dialog.DeleteCurrentRoadDialog;
import com.lzpeng.surveyor.dialog.NewRoadDialog;
import com.lzpeng.surveyor.dialog.TitlePopWindow;
import com.lzpeng.surveyor.greendao.DatabaseManager;
import com.lzpeng.surveyor.line.LineData;
import com.lzpeng.surveyor.line.LineDataDao;
import com.lzpeng.surveyor.main.fragment.FragmentAbout;
import com.lzpeng.surveyor.main.fragment.FragmentParameter;
import com.lzpeng.surveyor.main.fragment.FragmentProgram;
import com.lzpeng.surveyor.main.fragment.FragmentTools;
import com.lzpeng.surveyor.util.IconUtil;
import com.lzpeng.surveyor.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Administrator on 2018\5\20 0020.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_tab)
    LinearLayoutCompat mBottomTab;
    @BindView(R.id.left_menu)
    NavigationView mLeftMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private TextView mTitleTextView;
    private List<BottomBean> bottomBeans;
    private ISupportFragment[] fragments;
    private int mCurrentFragment = 0;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        initToolbar();
        initData();
        initView();
        mLeftMenu.setNavigationItemSelectedListener(item -> {
            ToastUtils.showShortSafe(item.getTitle());
            return true;
        });
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_more_items);
        long currentId = SPUtils.getInstance().getLong(SPConstant.CURRENT_ROAD_ID);
        if (currentId != -1L) {
            LineDataDao dao = DatabaseManager.getInstance().getDao(LineData.class);
            LineData lineData = dao.loadByRowId(currentId);
            toolbar.setTitle(lineData.getRoadName());
        } else {
            toolbar.setTitle("当前没有选择路线");
        }
        mTitleTextView = MyUtils.getToolbarTitleView(this, toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (mTitleTextView != null) {
            mTitleTextView.setOnClickListener(v ->{
                new TitlePopWindow(this, R.layout.popview_title).show(toolbar);
            });
        }

    }

    private void initView() {
        fragments = new ISupportFragment[bottomBeans.size()];
        for (int i = 0; i < bottomBeans.size(); i++) {
            LayoutInflater.from(this).inflate(R.layout.item_icon_bottom, mBottomTab);
            RelativeLayout tab = (RelativeLayout) mBottomTab.getChildAt(i);
            IconTextView icon = (IconTextView) tab.getChildAt(0);
            AppCompatTextView text = (AppCompatTextView) tab.getChildAt(1);
            icon.setText(bottomBeans.get(i).getIcon());
            text.setText(bottomBeans.get(i).getText());
            fragments[i] = bottomBeans.get(i).getFragment();
            tab.setOnClickListener(this);
            tab.setTag(i);
            if (i == mCurrentFragment) {
                icon.setTextColor(ContextCompat.getColor(this, R.color.app_main));
                text.setTextColor(ContextCompat.getColor(this, R.color.app_main));
            }
        }
        loadMultipleRootFragment(R.id.fl_container, mCurrentFragment, fragments);//加载很多布局
    }

    private void initData() {
        bottomBeans = new ArrayList<>();
        bottomBeans.add(new BottomBean(IconUtil.getRandomIcon(), "参数", new FragmentParameter()));
        bottomBeans.add(new BottomBean(IconUtil.getRandomIcon(), "程序", new FragmentProgram()));
        bottomBeans.add(new BottomBean(IconUtil.getRandomIcon(), "工具", new FragmentTools()));
        bottomBeans.add(new BottomBean(IconUtil.getRandomIcon(), "服务", new FragmentAbout()));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    //重置底部导航栏的颜色
    private void resetColor() {
        final int count = mBottomTab.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomTab.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setTextColor(Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        resetColor();
        final int tag = (int) v.getTag();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setTextColor(ContextCompat.getColor(this, R.color.app_main));
        itemTitle.setTextColor(ContextCompat.getColor(this, R.color.app_main));
        //注意先后顺序,第一个参数为要显示的,第二个为要隐藏的
//        showHideFragment(fragments[tag], fragments[mCurrentFragment]);
        showHideFragment(fragments[tag]);
        mCurrentFragment = tag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_road:
                NewRoadDialog dialog = NewRoadDialog.createDialog(this);
                dialog.setListener(dialogCase -> {
                    if (dialogCase == null){
                        ToastUtils.showShortSafe("未知错误");
                    }
                    switch (dialogCase) {
                        case UNIQUE:
                            ToastUtils.showShortSafe(dialogCase.toString());
                            break;
                        case NO_ERROR:
                            long currentId = SPUtils.getInstance().getLong(SPConstant.CURRENT_ROAD_ID);
                            LineDataDao dao = DatabaseManager.getInstance().getDao(LineData.class);
                            LineData lineData = dao.loadByRowId(currentId);
                            toolbar.setTitle(lineData.getRoadName());
                            break;
                        default:
                            break;
                    }

                });
                dialog.show();
                break;
            case R.id.action_delete_current_road:
                DeleteCurrentRoadDialog.createDialog(this).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(mLeftMenu);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

}
