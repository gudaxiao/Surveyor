package com.lzpeng.surveyor.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.blankj.utilcode.util.SPUtils;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.greendao.DatabaseManager;
import com.lzpeng.surveyor.line.LineData;
import com.lzpeng.surveyor.line.LineDataDao;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Administrator on 2018\5\25 0025.
 */

public class DeleteCurrentRoadDialog extends AppCompatDialogFragment {

    private BaseActivity activity;

    public static DeleteCurrentRoadDialog createDialog(BaseActivity activity) {
        DeleteCurrentRoadDialog dialog = new DeleteCurrentRoadDialog();
        dialog.activity = activity;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(activity)
                .setMessage("确认删除当前路线吗")
                .setPositiveButton("是", (dialog, which) -> {
                    long currentId = SPUtils.getInstance().getLong(SPConstant.CURRENT_ROAD_ID);
                    LineDataDao dao = DatabaseManager.getInstance().getDao(LineData.class);
                    dao.deleteByKey(currentId);
                    SPUtils.getInstance().put(SPConstant.CURRENT_ROAD_ID, -1L);
                    activity.setTitle("当前没有选择路线");
                    dialog.dismiss();
                })
                .setNegativeButton("否", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();
    }

    public void show() {
        super.show(activity.getSupportFragmentManager(), getClass().getSimpleName());
    }
}
