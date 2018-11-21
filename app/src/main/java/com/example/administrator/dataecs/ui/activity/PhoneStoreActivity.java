package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.ConfigUtil;
import com.example.administrator.dataecs.util.Constants;
import com.example.administrator.dataecs.util.StartXYSDKUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.ToastUntils;
import com.xinyan.bigdata.XinYanSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneStoreActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.xinyan_username)
    EditText xinyanUsername;
    @BindView(R.id.user_name_clear)
    ImageView userNameClear;
    @BindView(R.id.xinyan_idnum_input)
    EditText xinyanIdnumInput;
    @BindView(R.id.user_id_clear)
    ImageView userIdClear;
    @BindView(R.id.xinyanphonenum)
    EditText xinyanphonenum;
    @BindView(R.id.user_phonenum_clear)
    ImageView userPhonenumClear;
    @BindView(R.id.fwpwd)
    EditText fwpwd;
    @BindView(R.id.user_fupwd_clear)
    ImageView userFupwdClear;
    @BindView(R.id.kfpwd)
    EditText kfpwd;
    @BindView(R.id.next)
    Button next;

    //新颜的apiUser和apiKey
    private String apiUser = "8150716192";
    private String apiKey = "f275fde017e44bb29f79f186dcfe3422";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_carrier);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("运营商认证");

    }


    @OnClick({R.id.back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.next:

                if (xinyanUsername.getText().toString() == null || "".equals(xinyanUsername.getText().toString())) {
                    ToastUntils.ToastShort(PhoneStoreActivity.this, "输入正确的名字");
                    return;
                }
                if (xinyanIdnumInput.getText().toString() == null || "".equals(xinyanIdnumInput.getText().toString())
                        || !StringUtil.isTrueID(xinyanIdnumInput.getText().toString())
                        ) {
                    ToastUntils.ToastShort(PhoneStoreActivity.this, "输入正确的身份证号码");
                    return;
                }
                if (xinyanphonenum.getText().toString() == null || "".equals(xinyanphonenum.getText().toString())
                        || !StringUtil.isMobileNO(xinyanphonenum.getText().toString())) {
                    ToastUntils.ToastShort(PhoneStoreActivity.this, "请输入正确手机号码");
                    return;
                }
                if (fwpwd.getText().toString() == null || "".equals(fwpwd.getText().toString())) {
                    ToastUntils.ToastShort(PhoneStoreActivity.this, "请输入正确服务密码");
                    return;
                }

                ConfigUtil.idcard = xinyanIdnumInput.getText().toString().replaceAll(" ", "");
                ConfigUtil.realname = xinyanUsername.getText().toString().replaceAll(" ", "");
                ConfigUtil.phoneNum = xinyanphonenum.getText().toString().replaceAll(" ", "");
                ConfigUtil.phoneServerCode = fwpwd.getText().toString().replaceAll(" ", "");
                ConfigUtil.carrierCanInput = Constants.YES;
                ConfigUtil.carrierIDandNameShow = Constants.YES;

                XinYanSDK.getInstance().setMerchantAttribute(apiUser, apiKey);
                StartXYSDKUtil.startSDK(PhoneStoreActivity.this, Constants.Function.FUNCTION_CARRIER, Config.orderEn, false);
                finish();
                break;

        }
    }
}
