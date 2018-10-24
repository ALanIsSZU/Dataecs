package com.example.administrator.dataecs.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.weight.MyWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2018/7/4.
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.gif_img)
    GifImageView gifImg;
    @BindView(R.id.gif_rela)
    RelativeLayout gifRela;

    private String content;
    private String Url;
    //要跳转的qq包名
    String qqPackName="com.tencent.mobileqq";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        ButterKnife.bind(this);
        intView();

    }


    @SuppressLint("JavascriptInterface")
    private void intView() {
        back.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        content = bundle.getString("title");
        Url = bundle.getString("url");


        title.setText(content);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        //支持H5调本地方法设置
        webView.addJavascriptInterface(this,"android");
        webView.loadUrl(Url);

        //与H5交互的弹窗
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                gifRela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                gifRela.setVisibility(View.GONE);
            }

            //android7.0的webView为空的情况
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 默认的处理方式，WebView变成空白页
                //接受证书
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Url.equals(url)) {

                    webView.loadUrl(Url);
                    return true;
                } else {
                    webView.loadUrl(url);
                    return false;
                }
            }
        });
    }

    //H5调取app本地的方法(跳转qq)
    @JavascriptInterface
    public void fuckQQ(String GoToUrl) {
        if (checkApkExist(this,qqPackName)){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GoToUrl)));
        }else {
            Toast.makeText(this,"本机未安装QQ应用,请下载QQ",Toast.LENGTH_SHORT).show();
        }
    }

    //检查手机有没有安装qq
    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
