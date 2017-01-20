package com.bhx.weitu.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.bhx.weitu.tools.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个还没有写完
 * 作者：bianhuixiang on 2017/1/19 17:16
 * 邮箱：2466921206@qq.com
 */
public abstract class BasePermissionActivity extends AppCompatActivity {
    private static final int PERMISSION_GRANTED = 0x01; // 权限授权
    private static final int PERMISSION_DENID = 0x02; // 权限拒绝

    private static final int PERMISSION_REQUEST_CODE = 0x001; // 系统权限管理页面的参数


    private PermissionUtils mPermissionUtils;
    private boolean isRequireCheck; // 是否需要系统权限检测

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissionUtils = new PermissionUtils(this);
        isRequireCheck = true;
    }

    // 请求权限兼容低版本
    public void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 检查权限组
     *
     * @param permissions
     */
    public void checkPermissions(String... permissions) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permission);
            } else {
                grantedList.add(permission);
            }
        }
        // 需要申请权限
        if (deniedList.size() != 0) {
            String[] pers = (String[]) deniedList.toArray(new String[deniedList.size()]);//使用了第二种接口，返回值和参数均为结果
            ActivityCompat.requestPermissions(this, pers, PERMISSION_REQUEST_CODE);
        } else {
            doRequest();
        }
    }

    public abstract void doRequest();

    /**
     * 处理权限的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
