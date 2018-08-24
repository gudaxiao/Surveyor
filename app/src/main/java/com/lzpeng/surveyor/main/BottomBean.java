package com.lzpeng.surveyor.main;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Administrator on 2018\5\21 0021.
 */


public class BottomBean {

    private final String icon;
    private final String text;
    private final ISupportFragment fragment;

    public BottomBean(String icon, String text, ISupportFragment fragment) {
        this.icon = icon;
        this.text = text;
        this.fragment = fragment;
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public ISupportFragment getFragment() {
        return fragment;
    }
}
