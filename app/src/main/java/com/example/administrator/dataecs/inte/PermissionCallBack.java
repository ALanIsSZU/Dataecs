package com.example.administrator.dataecs.inte;

import java.util.List;

public abstract class PermissionCallBack {

    public abstract void onSuccess(int requestCode, List<String> grantPermissions);
    public abstract void onFail(int requestCode, List<String> grantPermissions);

}
