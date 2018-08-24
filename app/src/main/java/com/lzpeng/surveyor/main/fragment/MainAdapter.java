package com.lzpeng.surveyor.main.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

public class MainAdapter extends BaseMultiItemQuickAdapter<MainEntity, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private final String TAG = getClass().getName();
    private final Random random = new Random();
    private final BaseActivity activity;

    public MainAdapter(BaseActivity activity, List<MainEntity> data) {
        super(data);
        this.activity = activity;
        addItemType(MainEntity.ICON, R.layout.item_icon_text);
        addItemType(MainEntity.IMAGE, R.layout.item_image_text);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder holder, MainEntity item) {
        LinearLayoutCompat itemView = (LinearLayoutCompat) holder.itemView;

        itemView.setLayoutParams( new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth()/4,ScreenUtils.getScreenWidth()/4/3*5 ));
        switch (holder.getItemViewType()){
            case MainEntity.ICON:
                holder.setText(R.id.icon_item,item.getIcon());
                holder.setTextColor(R.id.icon_item, Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                holder.setText(R.id.text_item,item.getText());
                IconTextView icon = holder.getView(R.id.icon_item);
                icon.setHeight(ScreenUtils.getScreenWidth()/4/5*3);
                break;
            case MainEntity.IMAGE:
                holder.setImageResource(R.id.image_item,item.getImage());
                holder.setText(R.id.text_item,item.getText());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MainEntity entity = getData().get(position);
        if (entity.getConsumer() != null) {
            entity.getConsumer().accept(entity);
        } else if (entity.getActivity() != null){
            ActivityUtils.startActivity(activity, entity.getActivity());
        } else if (entity.getFragment() != null){
            activity.showHideFragment(entity.getFragment());
        }
    }
}
