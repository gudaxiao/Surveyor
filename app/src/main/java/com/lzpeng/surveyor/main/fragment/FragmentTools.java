package com.lzpeng.surveyor.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.BaseFragment;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.main.resection.ResectionActivity;
import com.lzpeng.surveyor.util.IconUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class FragmentTools extends MainFragment {

    @Override
    protected List<MainEntity> initData() {
        return Arrays.<MainEntity>asList(
                new MainEntity(IconUtil.getRandomIcon(), "后方交会").withActivity(ResectionActivity.class)
        );
    }


}