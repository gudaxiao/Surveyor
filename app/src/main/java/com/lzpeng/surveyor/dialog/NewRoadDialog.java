package com.lzpeng.surveyor.dialog;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.greendao.DatabaseManager;
import com.lzpeng.surveyor.line.LineData;
import com.lzpeng.surveyor.line.LineDataDao;
import com.lzpeng.surveyor.line.LineType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

@SuppressLint("ValidFragment")
public class NewRoadDialog extends BaseDialog {

    @BindView(R.id.edit_road_name)
    TextInputEditText mRoadName;
    @BindView(R.id.edit_road_name_prefix)
    TextInputEditText mRoadNamePrefix;


    private LineType type = LineType.POINT;
    private String roadName;
    private String roadNamePrefix;

    public static NewRoadDialog createDialog(BaseActivity activity, double widthPercent, double heightPercent) {
        NewRoadDialog dialog = new NewRoadDialog();
        dialog.setActivityAndSize(activity, widthPercent, heightPercent);
        return dialog;
    }

    public static NewRoadDialog createDialog(BaseActivity activity) {
        NewRoadDialog dialog = new NewRoadDialog();
        dialog.setActivity(activity);
        return dialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_new_road;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.rb_jd)
    void clickJd() {
        this.type = LineType.POINT;
    }

    @OnClick(R.id.rb_lines)
    void clickLine() {
        this.type = LineType.LINE;
    }

    @OnClick(R.id.btn_cancel)
    void clickCancel() {
        dismiss();
    }

    private ConfirmListener listener = dialogCase -> {
    };

    public void setListener(ConfirmListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.btn_ok)
    void clickOk() {
        if (check()) {
            try {
                LineData data = new LineData(type, roadName, roadNamePrefix);
                LineDataDao dao = DatabaseManager.getInstance().getDao(LineData.class);
                long currentId = dao.insert(data);
                SPUtils.getInstance().put(SPConstant.CURRENT_ROAD_ID, currentId);
                listener.onConfirm(DialogCase.NO_ERROR);
                dismiss();
            } catch (SQLiteConstraintException e) {
                //UNIQUE constraint failed: LINE_DATA.ROAD_NAME (code 2067)
                if (e.getMessage().startsWith("UNIQUE")) {
                    mRoadName.setError(DialogCase.UNIQUE.toString());
                    listener.onConfirm(DialogCase.UNIQUE);
                } else
                    listener.onConfirm(null);
            }

        }
    }

    private boolean check() {
        boolean result = true;
        if (TextUtils.isEmpty(mRoadName.getText().toString().trim())) {
            mRoadName.setError("请输入线路名称");
            result = false;
        }
        this.roadName = mRoadName.getText().toString().trim();
        if (TextUtils.isEmpty(mRoadNamePrefix.getText().toString().trim())) {
            this.roadNamePrefix = "K";
        } else {
            this.roadNamePrefix = mRoadNamePrefix.getText().toString().trim();
        }
        return result;
    }

    public interface ConfirmListener {
        void onConfirm(DialogCase unique);
    }
}
