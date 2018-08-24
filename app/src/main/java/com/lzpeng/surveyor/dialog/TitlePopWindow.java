package com.lzpeng.surveyor.dialog;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SPUtils;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.greendao.DatabaseManager;
import com.lzpeng.surveyor.line.LineData;
import com.lzpeng.surveyor.line.LineDataDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\5\26 0026.
 */

public class TitlePopWindow extends PopupWindow implements AdapterView.OnItemClickListener {
    private final Activity activity;
    @BindView(R.id.pop_list)
    ListViewCompat mPopList;
    private List<LineData> lineDatas;
    private ArrayAdapter adapter;

    public TitlePopWindow(Activity activity, @LayoutRes int layoutId) {
        super(LayoutInflater.from(activity).inflate(layoutId, null),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ButterKnife.bind(this, getContentView());
        this.activity = activity;
        bindView();
    }

    private void bindView() {
        LineDataDao dao = DatabaseManager.getInstance().getDao(LineData.class);
        lineDatas = dao.loadAll();
        adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, lineDatas);
        mPopList.setAdapter(adapter);
        mPopList.setOnItemClickListener(this);
    }

    public void show(View view) {
        showAsDropDown(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LineData lineData  = (LineData) adapter.getItem(position);
        SPUtils.getInstance().put(SPConstant.CURRENT_ROAD_ID, lineData.getId());
        activity.setTitle(lineData.getRoadName());
        dismiss();
    }
}
