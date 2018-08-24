package com.lzpeng.surveyor.main.fragment;

import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.main.horizontal.HorizontalActivity;
import com.lzpeng.surveyor.util.IconUtil;

import java.util.Arrays;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class FragmentParameter extends MainFragment {

    @Override
    protected List<MainEntity> initData() {
        return Arrays.<MainEntity>asList(
                new MainEntity(IconUtil.getRandomIcon(),"路线信息"),
                new MainEntity(IconUtil.getRandomIcon(),"平曲线").withActivity(HorizontalActivity.class),
                new MainEntity(R.drawable.test,"竖曲线")
        );
    }


}