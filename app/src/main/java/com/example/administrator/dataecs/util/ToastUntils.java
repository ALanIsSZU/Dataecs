package com.example.administrator.dataecs.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUntils {

    public static void ToastLong(Context context,String tip){
        Toast.makeText(context,tip,Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context,String tip){
        Toast.makeText(context,tip,Toast.LENGTH_SHORT).show();
    }

}
