package com.lzpeng.surveyor;

import android.app.Application;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\5\21 0021.
 */

public class BaseApp {

    private final List<IconFontDescriptor> ICONS = new ArrayList<>();

    public static Application getApplication() {
        return APPLICATION;
    }

    private static Application APPLICATION;

    //静态内部类实现单例模式
    private static class Holder{
        private static final BaseApp INSTANCE = new BaseApp();
    }

    public static BaseApp getInstance() {
        return Holder.INSTANCE;
    }
    //所有已配置完成
    public final void configure(){
        initIcons();
    }

    //初始化字体
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }
    
    //配置字体
    public final BaseApp withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }
    //配置字体
    public final BaseApp init(Application application) {
        APPLICATION = application;
        return this;
    }
}
