package com.example.administrator.dataecs.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.dataecs.MainActivity;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.BaseServer;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by Administrator on 2018/7/3.
 */

public class Startactivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        andPermissionTools(BaseServer.REQUEST_SATRT, true,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    //-------------------------------------------权限检测开始---------------------------------------------------------------------//

    public void andPermissionTools(int code, final boolean cancelable, String... permissions) {

        AndPermission.with(this)
                .permission(permissions)
                .requestCode(code)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Startactivity.this);

                        builder.setTitle("授权界面")
                                .setMessage("你已经取消授权，请打授权界面开启权限")
                                .setCancelable(cancelable)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        rationale.resume();

                                    }
                                }).show();
                    }
                })
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

            if (requestCode == BaseServer.REQUEST_SATRT) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Startactivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }

        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

            if (requestCode == BaseServer.REQUEST_SATRT) {


                AndPermission.defaultSettingDialog(Startactivity.this, BaseServer.START_FAIL)
                        .setTitle("申请权限")
                        .setMessage("米店 需要使用存储空间权限，保存文件。")
                        .setPositiveButton("去设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BaseServer.START_FAIL) {
            if (AndPermission.hasPermission(Startactivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //有这个权限，处理的逻辑
                Intent intent = new Intent(Startactivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                //没有这个权限，处理的逻辑
                AndPermission.defaultSettingDialog(Startactivity.this, BaseServer.START_FAIL)
                        .setTitle("申请权限")
                        .setMessage("米店 需要使用存储空间权限，保存文件。")
                        .setPositiveButton("去设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        }
    }
}
