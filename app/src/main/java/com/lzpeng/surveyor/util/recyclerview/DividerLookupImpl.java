package com.lzpeng.surveyor.util.recyclerview;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/***
 * 创建人: 李志鹏
 * 时间: 2017年12月01日 18:43
 ***/
public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {

    private final int COLOR;
    private final int SIZE;

    public DividerLookupImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int i) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}