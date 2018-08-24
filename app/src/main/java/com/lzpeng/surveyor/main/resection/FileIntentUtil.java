package com.lzpeng.surveyor.main.resection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;

import java.io.File;

/**
 * Created by Administrator on 2018\4\16 0016.
 */

public class FileIntentUtil {

    public static final Intent openFile(Context context, File resultFile){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //申请权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //getUriForFile的第二个参数就是Manifest中的authorities
            uri = FileProvider.getUriForFile(
                    context,
                    AppUtils.getAppPackageName() + ".fileprovider",
                    resultFile);
        } else {
            uri = Uri.fromFile(resultFile);
        }
        intent.setDataAndType(uri, FileUtil.getMimeType(resultFile.getAbsolutePath()));
        return intent;
    }

}
