package com.lzpeng.surveyor.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.lzpeng.surveyor.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public abstract class BaseDialog extends AppCompatDialogFragment {
    Unbinder unbinder;
    private BaseActivity activity;
    private double widthPercent;
    private double heightPercent;

    public void setActivityAndSize(BaseActivity activity, double widthPercent, double heightPercent) {
        this.activity = activity;
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
    }

    public void setActivity(BaseActivity activity) {
        setActivityAndSize(activity, 0.9, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        onBindView(savedInstanceState);
        return view;
    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height =ViewGroup.LayoutParams.WRAP_CONTENT;
        if (this.widthPercent > 0) {
            width = (int) (widthPercent * ScreenUtils.getScreenWidth());
        }
        if (this.heightPercent > 0) {
            height = (int) (heightPercent * ScreenUtils.getScreenHeight());
        }
        window.setLayout(width, height);
        super.onStart();
    }

    protected abstract int getLayoutId();

    protected abstract void onBindView(Bundle savedInstanceState);

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }

    public void show(){
        super.show(activity.getSupportFragmentManager(), getClass().getSimpleName());
    }


}
