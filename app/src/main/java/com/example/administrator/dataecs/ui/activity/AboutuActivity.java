package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.Tools;

/**
 * Created by Administrator on 2018/7/17.
 */

public class AboutuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        TextView introduce = (TextView) findViewById(R.id.introduce);
        TextView appName = (TextView) findViewById(R.id.app_name);
        if (Tools.getAppName(this) != null && Tools.getVersionName(this) != null) {

            appName.setText(Tools.getAppName(this) + "  " + Tools.getVersionName(this));
        }

        introduce.setText("想要福利更棒、薪资更好的工作?来米店" + "\n"
                + "大量知名企业在优款发布校园招聘和社会" + "\n"
                + "招聘信息，每天空缺岗位多。各行业的人" + "\n"
                + "才都在优款找到了合适的职业发展机会。" + "\n"
                + "[职位发现] 职位太多懒得看?轻戳，适合你" + "\n"
                + "的工作都在这儿" + "\n"
                + "[职位搜索] 支持附近查找全职兼职，强大" + "\n"
                + "的互联网搜索功能" + "\n"
                + "[简历中心] 刷新简历，让HR优先看到你" + "\n"
                + "找工作快人一步" + "\n"
                + "[谁看过我] HR猎头看过你简历?求职跳槽" + "\n"
                + "so easy你懂的");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
