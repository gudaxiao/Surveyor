package com.lzpeng.surveyor.util.recyclerview;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/***
 * 创建人: 李志鹏
 * 时间: 2017年12月01日 18:40
 ***/
public class BaseDecoration extends DividerItemDecoration {

    //线的颜色,线的粗细
    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }

}