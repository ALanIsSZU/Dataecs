package com.example.administrator.dataecs;

import android.app.Application;

/**
 * Created by Administrator on 2018/7/18.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }*/
    }
}
