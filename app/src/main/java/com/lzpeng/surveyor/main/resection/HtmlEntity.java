package com.lzpeng.surveyor.main.resection;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.BaseApp;
import com.lzpeng.surveyor.util.Consumer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

abstract class HtmlEntity implements MultiItemEntity {

    public static final int TYPE = 0x00;
    protected Document document ;
    //事件
    private Consumer<HtmlEntity> consumer;
    private Class<? extends BaseActivity> activity;
    private ISupportFragment fragment;

    public HtmlEntity(String assetFileName) {
        try {
            InputStream inputStream = BaseApp.getApplication().getAssets().open(assetFileName);
            document = Jsoup.parse(inputStream, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HtmlEntity withActivity(Class<? extends BaseActivity> activity){
        this.activity = activity;
        return this;
    }
    public HtmlEntity withFragment(ISupportFragment fragment){
        this.fragment = fragment;
        return this;
    }
    public HtmlEntity withConsumer(Consumer consumer){
        this.consumer = consumer;
        return this;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }


    Consumer<HtmlEntity> getConsumer() {
        return consumer;
    }

    Class<? extends BaseActivity> getActivity() {
        return activity;
    }

    ISupportFragment getFragment() {
        return fragment;
    }

    @Override
    public String toString() {
        if (document != null){
            return outerHtml();
        }
        return "请传入正确的Html路径";
    }

    protected abstract String outerHtml();
}
