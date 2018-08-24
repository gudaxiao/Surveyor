package com.lzpeng.surveyor.main.fragment;

import android.support.annotation.DrawableRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.util.Consumer;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

class MainEntity implements MultiItemEntity {
    public static final int ICON = 0x00;
    public static final int IMAGE = 0x01;
    private final int TYPE;

    private String icon;
    @DrawableRes
    private int image;
    private String text;
    //事件
    private Consumer<MainEntity> consumer;
    private Class<? extends BaseActivity> activity;
    private ISupportFragment fragment;

    public MainEntity(String icon, String text) {
        this.icon = icon;
        this.text = text;
        this.TYPE = ICON;
    }

    public MainEntity(@DrawableRes int image, String text) {
        this.image = image;
        this.text = text;
        this.TYPE = IMAGE;
    }

    public MainEntity withActivity(Class<? extends BaseActivity> activity){
        this.activity = activity;
        return this;
    }
    public MainEntity withFragment(ISupportFragment fragment){
        this.fragment = fragment;
        return this;
    }
    public MainEntity withConsumer(Consumer consumer){
        this.consumer = consumer;
        return this;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    public String getIcon() {
        return icon;
    }
    @DrawableRes
    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    Consumer<MainEntity> getConsumer() {
        return consumer;
    }

    Class<? extends BaseActivity> getActivity() {
        return activity;
    }

    ISupportFragment getFragment() {
        return fragment;
    }
}
