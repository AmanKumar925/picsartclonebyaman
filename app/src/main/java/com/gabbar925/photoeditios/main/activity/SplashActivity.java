package com.gabbar925.photoeditios.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gabbar925.photoeditios.R;
import com.gabbaraman.edit.AdmobHelp;

public class SplashActivity extends AppCompatActivity {
    Handler mHandler;

    /* renamed from: r */
    Runnable f209r;

    public void onBackPressed() {
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);

        this.mHandler = new Handler();
        this.f209r = new Runnable() {
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        };
        this.mHandler.postDelayed(this.f209r, 3000);
    }


    public void onDestroy() {
        if (!(this.mHandler == null || this.f209r == null)) {
            this.mHandler.removeCallbacks(this.f209r);
        }
        super.onDestroy();
    }
}
