package com.lzpeng.surveyor.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lzpeng.surveyor.line.DaoMaster;

/***
 * 创建人: 李志鹏
 * 时间: 2017年11月29日 12:59
 ***/
public class ReleaseOpenHelper extends DaoMaster.OpenHelper {
    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
