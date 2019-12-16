package com.shanchuang.ssf.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.luck.picture.lib.permissions.RxPermissions;
import com.shanchuang.ssf.MainActivity;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        //建立线程使图片静止2S;
//
//        if (isFirst) {
//            requestPermissions();
//        } else {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private Intent intent;

            @Override
            public void run() {

                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        timer.schedule(task, 2000);
//        }

    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(granted -> {
                    Intent intent = new Intent();
                    if (granted) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        // All requested permissions are granted
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        // At least one permission is denied
                        RxToast.normal("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");

                    }
                });

    }
}
