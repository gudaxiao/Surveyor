package com.lzpeng.surveyor;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lzpeng.surveyor.greendao.DatabaseManager;


/**
 * Created by Administrator on 2018\5\20 0020.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        DatabaseManager.getInstance().init(this);
        BaseApp.getInstance()
                .init(this)
                .withIcon(new FontAwesomeModule())
                .configure();
    }
}
