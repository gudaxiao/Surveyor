package com.lzpeng.surveyor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018\5\20 0020.
 */


public abstract class BaseActivity extends SupportActivity {

    protected final String TAG = getClass().getName();
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onBindView(savedInstanceState);
    }

    protected abstract void onBindView(Bundle savedInstanceState);
    protected abstract int getLayoutId();

    protected final void startActivity(Class<? extends Activity> activity) {
        super.startActivity(new Intent(this, activity));
    }

//    @Override
//    public void showHideFragment(ISupportFragment showFragment) {
//        //注意先后顺序,第一个参数为要显示的,第二个为要隐藏的
//        showHideFragment(showFragment, fragments[mCurrentFragment]);
//    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
