package com.gabbar925.photoeditios.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.gabbaraman.edit.AdmobHelp;
import com.gabbaraman.edit.Rate;
import com.gabbaraman.edit.funtion.UtilsApp;
import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.PhotoPicker;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.utils.ImageCaptureManager;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.utils.PermissionsUtils;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.photopicker.activity.PickImageActivity;
import com.gabbar925.photoeditios.main.BaseActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private ImageCaptureManager captureManager;
    private UnifiedNativeAd nativeAd;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public final void onClick(View view) {

            MainActivity.lambda$new$2(MainActivity.this, view);
        }
    };

    private ViewGroup viewGroup;
    private static final String TAG = "Tag";
    private InterstitialAd mInterstitialAd;

    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(bundle);
        makeFullScreen();
        setContentView((int) R.layout.activity_main_two);
        this.viewGroup = (ViewGroup) findViewById(16908290);




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdmobHelp.getInstance().init(this);


        AdView mAdView;
        mAdView = findViewById(R.id.banneradmob);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        MobileAds.initialize(this, getString(R.string.admob_native));
        refreshAd();
        //native banner end


//native banner
        ((LinearLayout) findViewById(R.id.editFunction)).setOnClickListener(this.onClickListener);
        ((RelativeLayout) findViewById(R.id.takePhoto)).setOnClickListener(this.onClickListener);
        ((LinearLayout) findViewById(R.id.collage)).setOnClickListener(this.onClickListener);


        this.captureManager = new ImageCaptureManager(this);
        findViewById(R.id.action_item_setting).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        findViewById(R.id.ratingApp).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UtilsApp.RateApp(MainActivity.this);
            }
        });

    }


    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            super.onActivityResult(i, i2, intent);
        } else if (i == 1) {
            if (this.captureManager == null) {
                this.captureManager = new ImageCaptureManager(this);
            }
            new Handler().post(new Runnable() {
                public final void run() {
                    MainActivity.this.captureManager.galleryAddPic();
                }
            });
            Intent intent2 = new Intent(getApplicationContext(), EditImageActivity.class);
            intent2.putExtra(PhotoPicker.KEY_SELECTED_PHOTOS, this.captureManager.getCurrentPhotoPath());
            startActivity(intent2);
        }
    }
    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                CardView frameLayout = findViewById(R.id.re_stay_in_this_page);

                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.native_admob_ad, null);

                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);

            }
        });

        NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener (new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {

            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

        ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());

        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);

        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public void onResume() {
        super.onResume();
    }

    public static void lambda$new$2(MainActivity mainActivity, View view) {
        int id = view.getId();
        if (id != R.id.collage) {
            if (id != R.id.editFunction) {
                if (id == R.id.takePhoto && PermissionsUtils.checkCameraPermission((Activity) mainActivity) && PermissionsUtils.checkWriteStoragePermission((Activity) mainActivity)) {
                    mainActivity.openCamera();
                }
            } else if (PermissionsUtils.checkWriteStoragePermission((Activity) mainActivity)) {
                PhotoPicker.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).start(mainActivity);
            }
        }
        else if (PermissionsUtils.checkWriteStoragePermission((Activity) mainActivity)) {
            Intent intent = new Intent(mainActivity, PickImageActivity.class);
            intent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 9);
            intent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 2);
            mainActivity.startActivityForResult(intent, 1001);
        }
    }


    private void openCamera() {
        try {
            startActivityForResult(this.captureManager.dispatchTakePictureIntent(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException unused) {
        }
    }

    public void onBackPressed() {
        Rate.Show(this, 1);
    }



}