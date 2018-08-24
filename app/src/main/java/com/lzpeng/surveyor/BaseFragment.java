package com.lzpeng.surveyor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

public abstract class BaseFragment extends SupportFragment {
    Unbinder unbinder;
    protected final String TAG = getClass().getName();
    protected BaseFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        onBindView(savedInstanceState);
        return view;
    }

    protected abstract int getLayoutId();
    protected abstract void onBindView(Bundle savedInstanceState);
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
