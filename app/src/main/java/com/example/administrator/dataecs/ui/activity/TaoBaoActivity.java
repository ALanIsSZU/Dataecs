package com.example.administrator.dataecs.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.CommonAsyncTask;
import com.example.administrator.dataecs.util.ConfigUtil;
import com.example.administrator.dataecs.util.Constants;
import com.example.administrator.dataecs.util.Tools;
import com.xinyan.bigdata.XinYanSDK;
import com.xinyan.bigdata.bean.XinyanCallBackData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaoBaoActivity extends Activity {

    private TextView tvResult;
    private StringBuffer stringBuffer;
    private ProgressBar progressBar;
    private ImageView ivUpDown;
    private ScrollView mScrollView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new LoadTask().execute();
        }
    };
    private String url = "";
    private String baseResulturl;
    private boolean isScrollViewUp = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tao_bao_layout);

        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.pb);
        ivUpDown = findViewById(R.id.toolbar_right_image);
        mScrollView = findViewById(R.id.mScrollView);
        tvResult.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setContentInsetsAbsolute(0, 0);
        }


//        ivUpDown.setVisibility(View.VISIBLE);
//        ivUpDown.setImageResource(R.drawable.back);
        ivUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isScrollViewUp) {
                    isScrollViewUp = false;
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                } else {
                    isScrollViewUp = true;
                    mScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });
    }

    class FormatJson extends CommonAsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringJson = strings[0];
            return Tools.stringToJSON(stringJson);
        }

        @Override
        protected void onPostExecute(String stringJson) {
            // stringBuffer.append("结果地址："+url);
            stringBuffer.append(stringJson);
            runOnUiThread(new Runnable() {
                public void run() {
                    if (!isFinishing()) {
                        tvResult.setText(stringBuffer.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

        }
    }

    private void initData() {
        try {
            baseResulturl = "https://api.xinyan.com/data";
            XinyanCallBackData xinyanCallBackData =
                    (XinyanCallBackData) getIntent().getExtras().get("data");
            stringBuffer = new StringBuffer();
            stringBuffer.append("任务ID:" + xinyanCallBackData.getToken() + "\n");
            stringBuffer.append("任务消息:" + xinyanCallBackData.getMessage() + "\n");
            tvResult.setText(stringBuffer.toString());

            if ("YES".equals(ConfigUtil.titleConfig.getLoginSuccessQuit())) {//登录退出模式，结果需要自己轮询解析的状态，然后再获取结果
                tvResult.append("\n授权成功退出模式下要自行查询任务状态");

                finish();
            } else {
                if(xinyanCallBackData.getCode()==1){

//                    if (xinyanCallBackData.getTaskType() != null && !"".equals(xinyanCallBackData.getTaskType())
//                            && "taobaopay".equals(xinyanCallBackData.getTaskType())) {
//                        SPUtils.put(TaoBaoActivity.this, Config.TAO_BAO_PERFECT, true);
//                    } else if (xinyanCallBackData.getTaskType() != null && !"".equals(xinyanCallBackData.getTaskType())
//                            && "carrier".equals(xinyanCallBackData.getTaskType())) {
//                        SPUtils.put(TaoBaoActivity.this, Config.PHONE_STORE_PERFECT, true);
//                    }
                    finish();
                }else if (xinyanCallBackData.getCode()==0){
                    finish();
                }
                handler.sendEmptyMessageDelayed(1, 500);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LoadTask extends CommonAsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        protected String doInBackground(String[] params) {
            getResulturl();
            Log.i("tag", "url == " + url);
            String orderinfo = loadOrderInFo(url);
            Log.i("tag", "orderinfo == " + orderinfo);
            return orderinfo;
        }

        @Override
        protected void onPostExecute(String orderinfo) {
            new FormatJson().execute(orderinfo);
        }
    }

    private String getResulturl() {
        // new service
        if (Tools.isNewService(ConfigUtil.type)) {
            baseResulturl = "https://qz.xinyan.com";
            url = baseResulturl + Constants.UrlManager.NEW_SERVICE_RESULT_URL;
            url = String.format(url, XinYanSDK.getInstance().getApiUser(),
                    XinYanSDK.getInstance().getApiEnc(), ConfigUtil.token);
        }
        return url;

    }

    public String loadOrderInFo(String url) {

        StringBuilder json = new StringBuilder();
        BufferedReader in;
        try {
            URL urlObject = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) urlObject.openConnection();
            uc.addRequestProperty("memberId", ConfigUtil.memberId);
            in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();

    }

}