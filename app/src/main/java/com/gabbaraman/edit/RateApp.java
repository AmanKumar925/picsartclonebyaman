package com.gabbaraman.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.gabbar925.photoeditios.R;
import com.gabbaraman.edit.funtion.UtilsApp;

public class RateApp extends Dialog {
    Context mContext;
    String mEmail;
    int mStyle = 0;
    String mTitleEmail;

    public RateApp(Context context, String str, String str2, int i) {
        super(context);
        this.mContext = context;
        this.mEmail = str;
        this.mTitleEmail = str2;
        this.mStyle = i;
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (this.mStyle == 0) {
            setContentView(R.layout.dialog_rate_app);
        }
        if (this.mStyle == 1) {
            setContentView(R.layout.dialog_rate_app);
        }
        if (this.mStyle == 2) {
            setContentView(R.layout.dialog_rate_app);
        }
        setContentView(R.layout.dialog_rate_app);
        ((TextView) findViewById(R.id.btn_good)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(RateApp.this.mContext).edit();
                edit.putBoolean("Show_rate", true);
                edit.commit();
                UtilsApp.RateApp(RateApp.this.mContext);
                UtilsApp.ShowToastLong(RateApp.this.mContext, "Thanks for rate and review ^^ ");
                RateApp.this.dismiss();
                ((Activity) RateApp.this.mContext).finish();
            }
        });
        ((TextView) findViewById(R.id.btn_not_good)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(RateApp.this.mContext).edit();
                edit.putBoolean("Show_rate", true);
                edit.commit();
                RateApp.this.showFeedBackDialog();
                RateApp.this.dismiss();
            }
        });
        ((TextView) findViewById(R.id.btn_late)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RateApp.this.dismiss();
                ((Activity) RateApp.this.mContext).finish();
            }
        });
    }


    public void showFeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(this.mContext.getString(R.string.title_dialog_feed_back));
        builder.setMessage(this.mContext.getString(R.string.message_dialog_feed_back));
        builder.setPositiveButton("feedback", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                UtilsApp.SendFeedBack(RateApp.this.mContext, RateApp.this.mEmail, RateApp.this.mTitleEmail);
                ((Activity) RateApp.this.mContext).finish();
            }
        });
        builder.setNegativeButton(this.mContext.getString(R.string.exit_app), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((Activity) RateApp.this.mContext).finish();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.setCancelable(false);
        create.show();
    }
}
