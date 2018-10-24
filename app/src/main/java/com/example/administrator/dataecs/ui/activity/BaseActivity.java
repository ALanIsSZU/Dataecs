package com.example.administrator.dataecs.ui.activity;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.dialog.AlertDialog;

/**
 * Created by Administrator on 2018/7/3.
 */

public abstract class BaseActivity extends CheckPermissionsActivity{

    /**
     * 短暂显示Toast提示(来自res)
     **/
    protected void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    protected void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    protected void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 含有标题和内容的对话框
     **/
    public void showAlertDialog(String title,String messsge){
        new AlertDialog(this).builder().setTitle(title)
                .setMsg(messsge).show();

    }

    /**
     * 含有内容、两个按钮的对话框
     **/
    public void showAlertDialog(String message,
                                String positiveText,
                                View.OnClickListener onPositiveClickListener,
                                String negativeText,
                                View.OnClickListener onNegativeClickListener) {
        new AlertDialog(this).builder()
                .setMsg(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
    }

    /**
     * 含有内容、两个按钮的对话框
     **/
    public void showAlertDialog(String message,
                                String positiveText,
                                View.OnClickListener onPositiveClickListener,
                                String negativeText,
                                View.OnClickListener onNegativeClickListener, DialogInterface.OnCancelListener onCancelListener) {
        new AlertDialog(this).builder()
                .setMsg(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .setOnCancelListener(onCancelListener)
                .show();
    }

    /**
     * 含有标题、内容、图标、两个按钮的对话框
     **/
    public void showAlertDialog(String title, String message,
                                int icon, String positiveText,
                                View.OnClickListener onPositiveClickListener,
                                String negativeText,
                                View.OnClickListener onNegativeClickListener) {
        new AlertDialog(this).builder().setTitle(title)
                .setMsg(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
    }

    /**
     * 显示自定义Toast提示(来自String)
     **/
    public void showCustomToast(String text) {
        View toastRoot = LayoutInflater.from(this).inflate(
                R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(BaseActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

}
