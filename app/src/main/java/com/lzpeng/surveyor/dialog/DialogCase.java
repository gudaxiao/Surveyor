package com.lzpeng.surveyor.dialog;

/**
 * Created by Administrator on 2018\5\26 0026.
 */

public enum  DialogCase {
    UNIQUE("出现相同名字，请换一个"), NO_ERROR("");

    private final String text;

    DialogCase(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return text ;
    }
}
