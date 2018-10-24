package com.example.administrator.dataecs.weight;

import android.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Administrator on 2018/7/27.
 */

public class MyWebChromeClient extends WebChromeClient{


    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("温馨提示")

                .setMessage(message)

                .setPositiveButton("确定", null)

                .setCancelable(false)

                .create()

                .show();

        result.confirm();
        return true;
    }
}
