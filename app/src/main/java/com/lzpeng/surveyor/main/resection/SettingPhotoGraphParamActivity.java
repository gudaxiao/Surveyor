package com.lzpeng.surveyor.main.resection;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\5\27 0027.
 */

public class SettingPhotoGraphParamActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_photo_scale)
    TextInputEditText mScale;
    @BindView(R.id.edit_photo_x0)
    TextInputEditText mX0;
    @BindView(R.id.edit_photo_y0)
    TextInputEditText mY0;
    @BindView(R.id.edit_photo_f)
    TextInputEditText mF;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
    }

    private void initView() {
        mScale.setText(SPUtils.getInstance().getFloat(SPConstant.PHOTO_SCALE, 40000) + "");
        mX0.setText(SPUtils.getInstance().getFloat(SPConstant.PHOTO_X0, 0) + "");
        mY0.setText(SPUtils.getInstance().getFloat(SPConstant.PHOTO_Y0, 0) + "");
        mF.setText(SPUtils.getInstance().getFloat(SPConstant.PHOTO_F, 0.15324F) + "");
    }

    @OnClick(R.id.btn_save)
    void clickSave() {
        SPUtils.getInstance().put(SPConstant.PHOTO_SCALE, Float.parseFloat(mScale.getText().toString().trim()));
        SPUtils.getInstance().put(SPConstant.PHOTO_X0, Float.parseFloat(mX0.getText().toString().trim()));
        SPUtils.getInstance().put(SPConstant.PHOTO_Y0, Float.parseFloat(mY0.getText().toString().trim()));
        SPUtils.getInstance().put(SPConstant.PHOTO_F, Float.parseFloat(mF.getText().toString().trim()));
        ToastUtils.showShortSafe("保存成功");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_graph_param;
    }

}
