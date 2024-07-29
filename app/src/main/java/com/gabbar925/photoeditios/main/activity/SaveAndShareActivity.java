package com.gabbar925.photoeditios.main.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.photopicker.utils.FileUtils;
import com.gabbar925.photoeditios.main.BaseActivity;
import com.bumptech.glide.Glide;

import com.gabbaraman.edit.AdmobHelp;
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
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaveAndShareActivity extends BaseActivity {
    static final  boolean $assertionsDisabled = false;
    File imgFile;
    private ViewGroup viewGroup;
    private static final String TAG = "Tag";
    private LinearLayout ladView,adView;
    private UnifiedNativeAd nativeAd;
    // ihave a meathod let me copy

    @SuppressLint("ResourceType")
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        makeFullScreen();
        setContentView((int) R.layout.save_and_share_layout);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) getResources().getString(R.string.save_and_share));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView;
        mAdView = findViewById(R.id.banneradmob);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        MobileAds.initialize(this, getString(R.string.admob_native));
        refreshAd();

        AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
            @Override
            public void onAdClosed() {
                AdmobHelp.getInstance().init(SaveAndShareActivity.this);
            }
        });
        // i placed native ad there, its ok
        this.viewGroup = (ViewGroup) findViewById(16908290);
        String string = getIntent().getExtras().getString("path");
        this.imgFile = new File(string);
        Glide.with(getApplicationContext()).load(this.imgFile).into((ImageView) findViewById(R.id.preview));
        findViewById(R.id.preview).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SaveAndShareActivity.lambda$onCreate$0(SaveAndShareActivity.this, view);
            }
        });
        ((TextView) findViewById(R.id.path)).setText(string);
        findViewById(R.id.btnMore).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SaveAndShareActivity.lambda$onCreate$1(SaveAndShareActivity.this, view);
            }
        });
        findViewById(R.id.btnFace).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SaveAndShareActivity.this.shareImageWhatsApp("com.facebook.katana", "Facebook");
            }
        });
        findViewById(R.id.btnInta).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SaveAndShareActivity.this.shareImageWhatsApp("com.instagram.android", "Instagram");
            }
        });

    }

    @SuppressLint("WrongConstant")
    public static  void lambda$onCreate$0(SaveAndShareActivity saveAndShareActivity, View view) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(FileProvider.getUriForFile(saveAndShareActivity.getApplicationContext(), "com.gabbar925.photoeditios.provider", saveAndShareActivity.imgFile), FileUtils.MIME_TYPE_IMAGE);
        intent.addFlags(3);
        saveAndShareActivity.startActivity(intent);
    }

    @SuppressLint("WrongConstant")
    public static  void lambda$onCreate$1(SaveAndShareActivity saveAndShareActivity, View view) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(saveAndShareActivity.getApplicationContext(), "com.gabbar925.photoeditios.provider", saveAndShareActivity.imgFile));
        intent.addFlags(3);
        saveAndShareActivity.startActivity(Intent.createChooser(intent, "Share"));
    }

    @SuppressLint("WrongConstant")
    public void shareImageWhatsApp(String str, String str2) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", getString(R.string.app_name) + "Try it Now https://play.google.com/store/apps/details?id=" + getPackageName());
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.gabbar925.photoeditios.provider", this.imgFile));
        if (isPackageInstalled(str, this)) {
            intent.setPackage(str);
            startActivity(Intent.createChooser(intent, "Share"));
            return;
        }
        Context applicationContext = getApplicationContext();
        Toast.makeText(applicationContext, "Please Install " + str2, 1).show();
    }

    @SuppressLint("WrongConstant")
    private boolean isPackageInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusave, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
                public void onAdClosed() {
                    SaveAndShareActivity.this.finish();
                }
            });
            return true;
        } else if (itemId != R.id.menu_home) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
                @SuppressLint("WrongConstant")
                public void onAdClosed() {
                    Intent intent = new Intent(SaveAndShareActivity.this.getApplicationContext(), MainActivity.class);
                    intent.setFlags(268468224);
                    SaveAndShareActivity.this.startActivity(intent);
                    SaveAndShareActivity.this.finish();
                }
            });
            return true;
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
                LinearLayout frameLayout = findViewById(R.id.nativesave);

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
}
