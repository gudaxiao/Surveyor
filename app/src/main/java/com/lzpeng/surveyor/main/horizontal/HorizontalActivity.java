package com.lzpeng.surveyor.main.horizontal;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.dialog.DeleteCurrentRoadDialog;
import com.lzpeng.surveyor.dialog.NewRoadDialog;
import com.lzpeng.surveyor.greendao.DatabaseManager;
import com.lzpeng.surveyor.line.LineData;
import com.lzpeng.surveyor.line.LineDataDao;

import butterknife.BindView;
import butterknife.OnClick;

public class HorizontalActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu fab;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal;
    }


    @OnClick(R.id.btn_add_point_start)
    void addStartPoint() {
        ToastUtils.showShortSafe("添加起点");
    }

    @OnClick(R.id.btn_add_point_jd)
    void addJdPoint() {
        ToastUtils.showShortSafe("添加交点");
    }

    @OnClick(R.id.btn_add_point_zx)
    void addZxPoint() {
        ToastUtils.showShortSafe("添加折线点");
    }

    @OnClick(R.id.btn_add_point_end)
    void addEndPoint() {
        ToastUtils.showShortSafe("设置终点");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.clear();
        getMenuInflater().inflate(R.menu.menu_resection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }


}
