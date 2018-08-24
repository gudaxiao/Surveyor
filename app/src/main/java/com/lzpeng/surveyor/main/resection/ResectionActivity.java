package com.lzpeng.surveyor.main.resection;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.lzpeng.photograph.core.InElement;
import com.lzpeng.photograph.core.OutElement;
import com.lzpeng.photograph.core.PhotoGraphAdjustment;
import com.lzpeng.photograph.core.PhotoGraphSurveyData;
import com.lzpeng.photograph.core.bean.Point2D;
import com.lzpeng.photograph.core.bean.Point3D;
import com.lzpeng.surveyor.BaseActivity;
import com.lzpeng.surveyor.R;
import com.lzpeng.surveyor.SPConstant;
import com.lzpeng.surveyor.util.recyclerview.BaseDecoration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Administrator on 2018\5\26 0026.
 */

@RuntimePermissions
public class ResectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_add)
    FloatingActionButton fab;
    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;
    private final int TXT_REQUEST_CODE = 1001;
    private final int EXCEL_REQUEST_CODE = 1002;
    private List<ResectionEntity> mDataList;
    private HtmlAdapter mAdapater;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initRecyclerView();
    }

    @OnClick(R.id.btn_calculate)
    void clickBtnOk() {
        if (mDataList == null || mDataList.size() < 4) {
            ToastUtils.showShortSafe("数据量不够,请添加至至少4条数据");
            return;
        }
        List<Point2D> point2DS = new ArrayList<>();
        List<Point3D> point3DS = new ArrayList<>();
        for (ResectionEntity entity : mDataList) {
            point2DS.add(new Point2D(entity.getXx() / 1e3, entity.getXy() / 1e3));
            point3DS.add(new Point3D(entity.getDx(), entity.getDy(), entity.getDz()));
        }
        PhotoGraphSurveyData data = PhotoGraphSurveyData.builder()
                .m(SPUtils.getInstance().getFloat(SPConstant.PHOTO_SCALE, 40000))
                .inElement(new InElement(
                        SPUtils.getInstance().getFloat(SPConstant.PHOTO_X0, 0),
                        SPUtils.getInstance().getFloat(SPConstant.PHOTO_Y0, 0),
                        SPUtils.getInstance().getFloat(SPConstant.PHOTO_F, 0.15324F)))
                .picturePoints(point2DS)
                .landPoints(point3DS)
                .build();
        PhotoGraphAdjustment adjustment = PhotoGraphAdjustment.create(data);
        adjustment.get();
        OutElement result = adjustment.getResult();
        OutElement qqResult = adjustment.getQQResult();
        Intent intent = new Intent(this, ResectionResultActivity.class);
        intent.putExtra("result", (Serializable) result);
        intent.putExtra("qqResult", (Serializable) qqResult);
        startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resection;
    }

    @OnClick(R.id.fab_add)
    void addStartPoint() {
        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_import_data_by_text:
                importDataByText();
                break;
            case R.id.action_import_data_by_excel:
                importDataByExcel();
                break;
            case R.id.action_setting_param:
                startActivity(SettingPhotoGraphParamActivity.class);
                break;
            default:
                break;
        }
        return true;
    }


    private void importDataByExcel() {
        ResectionActivityPermissionsDispatcher.showChooserWithCheck(this, "请选择Excel文件", EXCEL_REQUEST_CODE);
    }

    private void importDataByText() {
        ResectionActivityPermissionsDispatcher.showChooserWithCheck(this, "请选择文本文件", TXT_REQUEST_CODE);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showChooser(String title, int requestCode) {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, title);
        try {
            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
        }
    }

    private void showOpenFile(File file, String title) {
        Intent target = FileIntentUtil.openFile(this, file);
        Intent intent = Intent.createChooser(target, title);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (data != null) {
                final Uri uri = data.getData();
                final String fileName = FileUtils.getPath(this, uri);
                switch (requestCode) {
                    case TXT_REQUEST_CODE:
                        ToastUtils.showShortSafe("sss");
                        readTxtFile(fileName);
                        break;
                    case EXCEL_REQUEST_CODE:
                        readExcelFile(fileName);
                        break;
                }
            }
        }

    }

    private void readExcelFile(String fileName) {
        ToastUtils.showShortSafe("读取Excel文件功能将在后续版本实现");
    }

    private void readTxtFile(String fileName) {

        String fileContent = FileIOUtils.readFile2String(fileName);
        String[] lines = fileContent.split("(\\r\\n)|(\\n)|(\\r)");
//        if (lines.length < 4) {
//            ToastUtils.showShortSafe(fileName +"文件中数据量不够,请添加数据");
//            return;
//        }
        List<ResectionEntity> tempData = new ArrayList<>();
        for (int j = 0; j < lines.length; j++) {
            String[] datas = lines[j].trim().split("(\\,)|,");
            if (datas.length != 5) {
                ToastUtils.showShortSafe(fileName + "文件中第" + (j + 1) + "数据格式错误,请检查");
                return;
            }
            tempData.add(new ResectionEntity("resection.html",
                    Double.parseDouble(datas[0]), Double.parseDouble(datas[1]),
                    Double.parseDouble(datas[2]), Double.parseDouble(datas[3]), Double.parseDouble(datas[4])));

        }
        mDataList = tempData;
        mAdapater = new HtmlAdapter(this, mDataList);
        mRecyclerView.setAdapter(mAdapater);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ResectionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(manager);
        //设置分割线 浅灰色,宽度为5
        mRecyclerView.addItemDecoration(BaseDecoration.create(
                ContextCompat.getColor(this, android.R.color.darker_gray), 5
        ));
//        mDataList = Arrays.asList(
//                new ResectionEntity("resection.html", -86.15, -68.99, 36589.41, 25273.32, 2195.17),
//                new ResectionEntity("resection.html", -53.4, 82.21, 37631.08, 31324.51, 728.69),
//                new ResectionEntity("resection.html", -14.78, -76.63, 39100.97, 24934.98, 2386.5),
//                new ResectionEntity("resection.html", 10.46, 64.43, 40426.54, 30319.81, 757.31)
//        );
        mAdapater = new HtmlAdapter(this, mDataList);
        mRecyclerView.setAdapter(mAdapater);
    }
}
