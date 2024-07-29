package com.gabbaraman.edit;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;

import com.gabbar925.photoeditios.R;
import com.gabbaraman.edit.funtion.UtilsApp;

public class Rate {
    public static void Show(Context context, int i) {
        try {
            if (!UtilsApp.isConnectionAvailable(context)) {
                ((Activity) context).finish();
            } else if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("Show_rate", false)) {
                RateApp rateApp = new RateApp(context, context.getString(R.string.email_feedback), context.getString(R.string.Title_email), i);

                rateApp.show();
            } else {
                ((Activity) context).finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ((Activity) context).finish();
        }
    }
}
