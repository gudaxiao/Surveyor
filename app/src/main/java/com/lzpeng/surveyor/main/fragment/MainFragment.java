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
public abstract class MainFragment extends BaseFragment {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;
    private List<MainEntity> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        initRecyclerView();
        MainAdapter adapter = new MainAdapter((BaseActivity) getActivity(), mData = initData());
        mRecyclerView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);

    }

    protected abstract List<MainEntity> initData() ;


}