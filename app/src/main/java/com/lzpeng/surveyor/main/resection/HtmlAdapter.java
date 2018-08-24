package com.lzpeng.surveyor.main.resection;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzpeng.surveyor.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

public class HtmlAdapter<T extends HtmlEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private final String TAG = getClass().getName();
    private final Random random = new Random();
    private final BaseActivity activity;

    public HtmlAdapter(BaseActivity activity, List<T> data) {
        super(data);
        this.activity = activity;
        addItemType(HtmlEntity.TYPE, android.R.layout.simple_list_item_1);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder holder, T item) {
        switch (holder.getItemViewType()) {
            case HtmlEntity.TYPE:
                TextView html = holder.getView(android.R.id.text1);
                holder.setText(android.R.id.text1, Html.fromHtml(item.toString()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HtmlEntity entity = getData().get(position);
        if (entity.getConsumer() != null) {
            entity.getConsumer().accept(entity);
        } else if (entity.getActivity() != null) {
            ActivityUtils.startActivity(activity, entity.getActivity());
        } else if (entity.getFragment() != null) {
            activity.showHideFragment(entity.getFragment());
        }
    }
}
