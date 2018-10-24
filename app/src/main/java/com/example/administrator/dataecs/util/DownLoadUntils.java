package com.example.administrator.dataecs.util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.dialog.CommonProgressDialog;
import com.example.administrator.dataecs.dialog.MaterialDialog;
import com.example.administrator.dataecs.inte.OnBtnClickL;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/7/24.
 */

public class DownLoadUntils {

    private Context context;
    //后台返回的版本号
    private String visionNumber;
    //要更新的APP的URL
    private String downloadUrl;

    private CommonProgressDialog pBar;
    private static final String DOWNLOAD_NAME = "JieDai.apk";
    private static final int REQUEST_CODE_PERMISSION_SD = 101;

    public DownLoadUntils(Context context, String visionNumber, String downloadUrl,int vision) {
        this.context = context;
        this.visionNumber = visionNumber;
        this.downloadUrl = downloadUrl;
        // 获取本版本号，是否更新
        getVersion(vision);

    }

    // 获取更新版本号
    private void getVersion(final int vision) {
//         {"data":{"content":"其他bug修复。","id":"2","api_key":"android",
//         // "version":"2.1"},"msg":"获取成功","status":1}
        String data = "";
        //网络请求获取当前版本号和下载链接
        //实际操作是从服务器获取
        //demo写死了
        int num = Integer.parseInt(visionNumber);
//        String newversion = "2.1";//更新新的版本号
        String content = "检测到新版本,";//更新内容
//        String url = "http://openbox.mobilem.360.cn/index/d/sid/3429345";//安装包下载地址

//        double newversioncode = Double
//                .parseDouble(newversion);

//        System.out.println(newversion + "v" + vision + ",,"
//                + visionNumber);

        if (num != vision) {
            if (vision < num) {
                System.out.println(num + "v"
                        + vision);
                // 版本号不同
                ShowDialog(vision, visionNumber, content, downloadUrl);
            }
        }
    }

    /**
     * 升级系统
     *
     * @param content
     * @param url
     */

    MaterialDialog dialog;

    private void ShowDialog(int vision, String newversion, String content,
                            final String url) {

        dialog = new MaterialDialog(context);//自定义的对话框，可以呀alertdialog
        dialog.content(content).btnText("更新").title("版本更新 ")
                .titleTextSize(15f).show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setOnBtnClickL(new OnBtnClickL() {// left btn click listener
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {// right btn click listener

            @Override
            public void onBtnClick() {
//                dialog.dismiss();
                // pBar = new ProgressDialog(MainActivity.this,
                // R.style.dialog);
                dialog.setCancelable(false);
                pBar = new CommonProgressDialog(context);
                pBar.setCanceledOnTouchOutside(false);
                pBar.setTitle("正在下载");
                pBar.setCustomTitle(LayoutInflater.from(
                        context).inflate(
                        R.layout.title_dialog, null));
                pBar.setMessage("正在下载");
                pBar.setIndeterminate(true);
                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pBar.setCancelable(false);
                // downFile(URLData.DOWNLOAD_URL);
                final DownloadTask downloadTask = new DownloadTask(
                        context);
                downloadTask.execute(url);
                pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        });
    }


    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(Environment.getExternalStorageDirectory(),
                            DOWNLOAD_NAME);

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }

                } else {
                    Toast.makeText(context, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            pBar.dismiss();
            if (result != null) {

//                // 申请多个权限。大神的界面
//                AndPermission.with(MainActivity.this)
//                        .requestCode(REQUEST_CODE_PERMISSION_OTHER)
//                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
//                        .rationale(new RationaleListener() {
//                                       @Override
//                                       public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
//                                           // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
//                                           AndPermission.rationaleDialog(MainActivity.this, rationale).show();
//                                       }
//                                   }
//                        )
//                        .send();
                // 申请多个权限。
                AndPermission.with((Activity) context)
                        .requestCode(REQUEST_CODE_PERMISSION_SD)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener
                        )
                        .send();


                Toast.makeText(context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(context, "File downloaded",
                // Toast.LENGTH_SHORT)
                // .show();
//                String apkPath = context.getExternalCacheDir().getPath()+ File.separator+"app"+File.separator;
                File file = new File(Environment.getExternalStorageDirectory(),
                        DOWNLOAD_NAME);
                Log.e("me", "==apkpath==" + file.getPath());
                update(context, file.getPath());
            }

        }
    }

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.build(context)
                    .setTitle(R.string.title_dialog)
                    .setMessage(R.string.message_permission_rationale)
                    .setPositiveButton(R.string.btn_dialog_yes_permission, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })

                    .setNegativeButton(R.string.btn_dialog_no_permission, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    })
                    .show();
        }
    };

    //----------------------------------SD权限----------------------------------//
    //安装应用
    private void update(final Context context, final String apkPath) {

        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(context, "更新失败！未找到安装包", Toast.LENGTH_SHORT).show();
            return;
        }

        File apkFile = new File(apkPath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Android 7.0 系统共享文件需要通过 FileProvider 添加临时权限，否则系统会抛出 FileUriExposedException .
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(this,"com.skyrin.bingo.fileprovider",apkFile);
            Log.e("me", "==apkFile==" + apkFile.getPath());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkUri = FileProvider.getUriForFile(context, "com.example.administrator.dataecs.fileprovider", apkFile);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");

        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(
                    Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);


    }

}
