package com.gabbar925.photoeditios.main.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.main.BaseActivity;
import com.gabbaraman.edit.funtion.UtilsApp;

public class SettingsActivity extends BaseActivity {

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        makeFullScreen();
        setContentView((int) R.layout.setting_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) getResources().getString(R.string.settings));
        findViewById(R.id.rate_us).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UtilsApp.RateApp(SettingsActivity.this);
            }
        });
        findViewById(R.id.share_app).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UtilsApp.shareApp(SettingsActivity.this);
            }
        });
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UtilsApp.SendFeedBack(SettingsActivity.this, SettingsActivity.this.getString(R.string.email_feedback), SettingsActivity.this.getString(R.string.Title_email));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
