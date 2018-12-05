package com.example.administrator.dataecs.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.example.administrator.dataecs.inte.PermissionCallBack;
import com.example.administrator.dataecs.inte.PermissionFailDialogCallBack;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

public class AndpermissionUntils {

    public static void andPermissionTools(final Activity context, int code, final String title, final String tipMessage,
                                          final boolean cancelable,  PermissionCallBack perCall ,String... permissions) {

        AndPermission.with(context)
                .permission(permissions)
                .requestCode(code)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle(title)
                                .setMessage(tipMessage)
                                .setCancelable(cancelable)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        rationale.resume();

                                    }
                                }).show();
                    }
                })
                .callback(setListen(perCall))
                .start();


    }


    public static PermissionListener setListen(final PermissionCallBack perCall){
        PermissionListener listener = new PermissionListener() {

            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                perCall.onSuccess(requestCode,grantPermissions);
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                perCall.onFail(requestCode,deniedPermissions);
            }
        };
        return listener;
    }

    //拒绝之后弹出来的窗口
    public static void failDialog(Context context , int code,String title,String tipMessage){

        AndPermission.defaultSettingDialog((Activity) context, code)
                .setTitle(title)
                .setMessage(tipMessage)
                .setPositiveButton("去设置")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();

    }

    public static void failDialog(Context context , int code,String title,String tipMessage
                , final PermissionFailDialogCallBack perCall){
        AndPermission.defaultSettingDialog((Activity) context, code)
                .setTitle(title)
                .setMessage(tipMessage)
                .setPositiveButton("去设置")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        perCall.failDialog();

                    }
                })
                .show();
    }

}
