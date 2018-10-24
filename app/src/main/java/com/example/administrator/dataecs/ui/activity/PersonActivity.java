package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.PerosonInfoModel;
import com.example.administrator.dataecs.model.ShenQinCommitModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_cord)
    EditText idCord;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.rela_content)
    RelativeLayout relaContent;
    @BindView(R.id.person_content)
    TextView personContent;

    //个人信息字段
    String personName;
    String personID;
    String personPhone;

    //资料是否完善
    boolean isPerfect =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("个人信息");
        Intent intent=getIntent();
        isPerfect=intent.getBooleanExtra("person",false);
    }

    @OnClick({R.id.back, R.id.commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent=new Intent();
                intent.putExtra("person_go",isPerfect);
                setResult(BaseServer.PERSON_TO_MAIN,intent);
                finish();

                break;
            case R.id.commit_btn:

                if (name.getText().toString() == null || "".equals(name.getText().toString())) {
                    ToastUntils.ToastShort(PersonActivity.this, "输入正确的名字");
                    return;
                }
                if (idCord.getText().toString() == null || "".equals(idCord.getText().toString()) || !StringUtil.isTrueID(idCord.getText().toString())) {
                    ToastUntils.ToastShort(PersonActivity.this, "输入正确的身份证号码");
                    return;
                }

                personName=name.getText().toString();
                personPhone=SharePreferencesUtil.getUserName(this);
                personID=idCord.getText().toString();

                commitInfo(personPhone,personID,personName,null);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPerfect){
            relaContent.setVisibility(View.GONE);
            personContent.setVisibility(View.VISIBLE);
            String loginPhone= SharePreferencesUtil.getUserName(PersonActivity.this);
            getPersonInfo(loginPhone);
        }else {
            relaContent.setVisibility(View.VISIBLE);
            personContent.setVisibility(View.GONE);

        }
    }

    //提交个人信息
    public void commitInfo(String phone, String idCard, String name, String bankCard) {

        if (!SystemUntils.isNetworkConnected(PersonActivity.this)) {
            Toast.makeText(PersonActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<ShenQinCommitModel> call = inte.getCommitInfo(phone, idCard, name, bankCard);
        call.enqueue(new Callback<ShenQinCommitModel>() {
            @Override
            public void onResponse(Call<ShenQinCommitModel> call, Response<ShenQinCommitModel> response) {
                ShenQinCommitModel model=response.body();

                if (model.getMap().getCode()==0){
                    Toast.makeText(PersonActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    relaContent.setVisibility(View.GONE);
                    personContent.setVisibility(View.VISIBLE);

                    personContent.setText(
                            "姓名：" + personName + "\n"
                                    + "身份证：" + personID + "\n"
                                    + "手机：" + personPhone + "\n"
                    );
                    isPerfect=true;
                }else {
                    Toast.makeText(PersonActivity.this,model.getMap().getResult() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ShenQinCommitModel> call, Throwable t) {
                Toast.makeText(PersonActivity.this, "网络异常，提交失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取个人信息
    public void getPersonInfo(String phone){

        if (!SystemUntils.isNetworkConnected(PersonActivity.this)) {
            Toast.makeText(PersonActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);

        Call<PerosonInfoModel> call=inte.getPersonInfo(phone);
        call.enqueue(new Callback<PerosonInfoModel>() {
            @Override
            public void onResponse(Call<PerosonInfoModel> call, Response<PerosonInfoModel> response) {

                PerosonInfoModel model=response.body();
                if (model.getMap().getCode()==0){

                    personContent.setText(
                            "姓名：" + model.getMap().getResult().getName() + "\n"
                                    + "身份证：" + model.getMap().getResult().getIdCard() + "\n"
                                    + "手机：" + model.getMap().getResult().getNumber() + "\n"
                    );

                }else {
                    ToastUntils.ToastShort(PersonActivity.this,"未添加个人资料");
                }

            }

            @Override
            public void onFailure(Call<PerosonInfoModel> call, Throwable t) {
                ToastUntils.ToastShort(PersonActivity.this,t.toString());
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.putExtra("person_go",isPerfect);
            setResult(BaseServer.PERSON_TO_MAIN,intent);
            finish();
        }

        return true;
    }
}
