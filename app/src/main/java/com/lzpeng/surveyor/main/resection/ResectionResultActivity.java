package com.lzpeng.surveyor.main.resection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lzpeng.photograph.core.OutElement;
import com.lzpeng.surveyor.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018\5\27 0027.
 */

public class ResectionResultActivity extends BaseActivity{

    @BindView(android.R.id.text1)
    TextView mResult;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        OutElement qqResult = (OutElement) intent.getSerializableExtra("qqResult");
        OutElement result = (OutElement) intent.getSerializableExtra("result");
        mResult.setText("" +
                "\r\nXs:\t\t" +result.getXs()+"\r\nXs m:\t\t" +qqResult.getXs()+
                "\r\nYs:\t\t"  +result.getYs()+"\r\nYs m:\t\t"  +qqResult.getYs()+
                "\r\nZs:\t\t" +result.getZs()+"\r\nZs m:\t\t" +qqResult.getZs()+
                "\r\nphi:\t\t" +result.getPhiOrA()+"\r\nphi m:\t\t" +qqResult.getPhiOrA()+
                "\r\nomega:\t\t" +result.getOmegaOrAlpha()+"\r\nomega m:\t\t" +qqResult.getOmegaOrAlpha()+
                "\r\nkappa:\t\t"  +result.getKappa()+"\r\nkappa m:\t\t"  +qqResult.getKappa()+
                "");
    }

    @Override
    protected int getLayoutId() {
        return android.R.layout.simple_list_item_1;
    }
}
