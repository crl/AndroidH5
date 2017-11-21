package com.lingyu.shushanH5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.xtw.xtwsdk.XtwSdk;
import com.xtw.xtwsdk.XtwSdkUtility;

import java.util.Date;


public class MainActivity extends Activity {
    public static String TAG="Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date=new Date();
        long t=date.getTime();
        Log.d(TAG, "onCreate: "+t);
        XtwSdk.getInstance().xtwH5Init(this, "http://test.shushanh5.lingyunetwork.com/gate/micro/login.aspx?t="+t,
                new XtwSdkUtility.InitListener() {
                    @Override
                    public void result(XtwSdkUtility.InitCode initCode, String ret) {
                        System.out.println("XtwSdk init ret:" + initCode + "\ndes: " + ret);
                        if (XtwSdkUtility.InitCode.Fail == initCode) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("初始化结果:" + initCode)
                                    .setMessage(ret)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });

        checkNetwork();

        //SharedUtil
    }

    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        // 获取当前的网络连接是否可用
        boolean available = networkInfo.isAvailable();
        if (available==false) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("网络连接")
                    .setMessage("当前的网络连接不可用!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到无限wifi网络设置界面
                            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setCancelable(false)
                    .show();
            return false;
        } else {
            return true;
        }
    }
}
