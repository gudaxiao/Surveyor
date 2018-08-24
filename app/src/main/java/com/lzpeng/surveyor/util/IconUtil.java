package com.lzpeng.surveyor.util;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.Random;

/**
 * Created by Administrator on 2018\5\25 0025.
 */

public final class IconUtil {

    public static final Random random = new Random();


    public static final String getRandomIcon(){
        FontAwesomeIcons[] icons = FontAwesomeIcons.values();
        int size = icons.length;
        return "{" + icons[random.nextInt(size)].key() + "}";
    }
}
