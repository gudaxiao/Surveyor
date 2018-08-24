package com.lzpeng.surveyor.greendao;

import android.content.Context;

import com.lzpeng.surveyor.line.DaoMaster;
import com.lzpeng.surveyor.line.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/***
 * 创建人: 李志鹏
 * 时间: 2017年11月29日 13:00
 ***/
public class DatabaseManager {

    private DaoSession daoSession;

    private DatabaseManager(){
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private static class Holder{
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    private void initDao(Context context){
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "surveyor.db");
        final Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public final <T extends AbstractDao> T getDao(Class beanClass){
        return (T) daoSession.getDao(beanClass);
    }
}
