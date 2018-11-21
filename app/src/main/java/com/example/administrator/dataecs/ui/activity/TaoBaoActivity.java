package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.ButterKnife;

public class TaoBaoActivity extends AppCompatActivity {

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
        ButterKnife.bind(this);

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
            if ("test".equals(ConfigUtil.environment)) {
                baseResulturl = "https://test.xinyan.com/data/";
            }
            if ("product".equals(ConfigUtil.environment)) {
                baseResulturl = "https://api.xinyan.com/data/";
            }
            XinyanCallBackData xinyanCallBackData = (XinyanCallBackData) getIntent().getExtras().get("data");
            stringBuffer = new StringBuffer();
            stringBuffer.append("订单ID:" + ConfigUtil.tradeNo + "\n");
            stringBuffer.append("任务ID:" + xinyanCallBackData.getToken() + "\n");
            stringBuffer.append("任务消息:" + xinyanCallBackData.getMessage() + "\n");
            tvResult.setText(stringBuffer.toString());

            if ("YES".equals(ConfigUtil.titleConfig.getLoginSuccessQuit())) {//登录退出模式，结果需要自己轮询解析的状态，然后再获取结果
                tvResult.append("\n授权成功退出模式下要自行查询任务状态");
            } else {
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
        if (Constants.Function.FUNCTION_TAOBAO.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.TAOBAO_RESULT_URL;
        } else if (Constants.Function.FUNCTION_ALIPAY.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.ALIPAY_RESULT_URL;
        } else if (Constants.Function.FUNCTION_JINGDONG.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.JINGDONG_RESULT_URL;
        } else if (Constants.Function.FUNCTION_CARRIER.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.CARRIER_RESULT_URL;//https://api.xinyan.com/data/carrier/v2/mobile/201805222136590131070693?mobile=
        } else if (Constants.Function.FUNCTION_QQ.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.QQ_RESULT_URL;//https://api.xinyan.com/data/qq/v1/alldata/{tradeNo}
        } else if (Constants.Function.FUNCTION_FUND.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.FUND_RESULT_URL;
        } else if (Constants.Function.FUNCTION_CHSI.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.CHIS_RESULT_URL;//data/chsi/v1/all/{tradeNO}
        } else if (Constants.Function.FUNCTION_DIDI.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.DIDI_RESULT_URL;///data/chsi/v1/all/{tradeNO}
        } else if (Constants.Function.FUNCTION_SECURITY.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.SECURITY_RESULT_URL;
        } else if (Constants.Function.FUNCTION_ONLINE_BANK.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.BANK_RESULT_URL;
        } else if (Constants.Function.FUNCTION_TAOBAOPAY.equals(ConfigUtil.type)) {
            url = baseResulturl + Constants.UrlManager.TAOBAOPAY_RESULT_URL;
        }
        url = url.replaceAll("\\{[^}]*\\}", ConfigUtil.tradeNo);
        // new service
        if (Tools.isNewService(ConfigUtil.type)) {
            if (Constants.UrlManager.EN_TEST.equals(ConfigUtil.environment)) {
                baseResulturl = Constants.UrlManager.BASE_TEST_NEW_SERVICERESULTURL;
            }
            if (Constants.UrlManager.EN_PRODUCT.equals(ConfigUtil.environment)) {
                baseResulturl = Constants.UrlManager.BASE_PRODUCT_NEW_SERVICERESULTURL;
            }
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