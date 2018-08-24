package com.lzpeng.surveyor.main.fragment;

import com.lzpeng.surveyor.main.resection.ResectionActivity;
import com.lzpeng.surveyor.util.IconUtil;

import java.util.Arrays;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class FragmentProgram extends MainFragment {
    protected List<MainEntity> initData() {
        return Arrays.<MainEntity>asList(
                new MainEntity(IconUtil.getRandomIcon(), "里程计算坐标")
        );
    }
}