package com.gabbaraman.edit;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gabbar925.photoeditios.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class AdmobHelp {
    public static long TimeReload = 60000;
    private static AdmobHelp instance;
    public static long timeLoad;

    public AdCloseListener adCloseListener;

    public boolean isReloaded = false;
    PublisherInterstitialAd mPublisherInterstitialAd;

    public UnifiedNativeAd nativeAd;

    public interface AdCloseListener {
        void onAdClosed();
    }

    public static AdmobHelp getInstance() {
        if (instance == null) {
            instance = new AdmobHelp();
        }
        return instance;
    }

    private AdmobHelp() {
    }

    public void init(Context context) {
        MobileAds.initialize(context, context.getString(R.string.admob_app_id));
        this.mPublisherInterstitialAd = new PublisherInterstitialAd(context);
        this.mPublisherInterstitialAd.setAdUnitId(context.getString(R.string.admob_full));
        this.mPublisherInterstitialAd.setAdListener(new AdListener() {
            public void onAdClicked() {
            }

            public void onAdLeftApplication() {
            }

            public void onAdLoaded() {
            }

            public void onAdOpened() {
            }

            public void onAdFailedToLoad(int i) {
                if (!AdmobHelp.this.isReloaded) {
                    boolean unused = AdmobHelp.this.isReloaded = true;
                    AdmobHelp.this.loadInterstitialAd();
                }
            }

            public void onAdClosed() {
                if (AdmobHelp.this.adCloseListener != null) {
                    AdmobHelp.this.adCloseListener.onAdClosed();
                    AdmobHelp.this.loadInterstitialAd();
                }
            }
        });
        loadInterstitialAd();
    }


    public void loadInterstitialAd() {
        if (this.mPublisherInterstitialAd != null && !this.mPublisherInterstitialAd.isLoading() && !this.mPublisherInterstitialAd.isLoaded()) {
            this.mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        }
    }

    public void showInterstitialAd(AdCloseListener adCloseListener2) {
        if (timeLoad + TimeReload >= System.currentTimeMillis()) {
            adCloseListener2.onAdClosed();
        } else if (canShowInterstitialAd()) {
            this.adCloseListener = adCloseListener2;
            this.mPublisherInterstitialAd.show();
            timeLoad = System.currentTimeMillis();
        } else {
            adCloseListener2.onAdClosed();
        }
    }

    private boolean canShowInterstitialAd() {
        return this.mPublisherInterstitialAd != null && this.mPublisherInterstitialAd.isLoaded();
    }

    public void loadBanner(Activity activity) {
        final ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) activity.findViewById(R.id.shimmer_container);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        final LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.banner_container);
        try {
            AdView adView = new AdView(activity);
            adView.setAdUnitId(activity.getString(R.string.admob_banner));
            linearLayout.addView(adView);
            adView.setAdSize(getAdSize(activity));
            adView.loadAd(new AdRequest.Builder().build());
            adView.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);

                    linearLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                public void onAdLoaded() {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception unused) {
        }
    }

    private AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }

    public void loadBannerFragment(Activity activity, View view) {
        final ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.banner_container);
        try {
            AdView adView = new AdView(activity);
            adView.setAdUnitId(activity.getString(R.string.admob_banner));
            linearLayout.addView(adView);
            adView.setAdSize(getAdSize(activity));
            adView.loadAd(new AdRequest.Builder().build());
            adView.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    linearLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                public void onAdLoaded() {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception unused) {
        }
    }


    public void loadNativeFragment(final Activity activity, final View view) {
        final ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        new AdLoader.Builder((Context) activity, activity.getString(R.string.admob_native)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (AdmobHelp.this.nativeAd != null) {
                    AdmobHelp.this.nativeAd.destroy();
                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                UnifiedNativeAd unused = AdmobHelp.this.nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fl_adplaceholder);
                if (frameLayout != null) {
                    frameLayout.setVisibility(View.VISIBLE);
                    UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(R.layout.native_admob_ad, (ViewGroup) null);
                    AdmobHelp.this.populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(unifiedNativeAdView);
                }
            }
        }).withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(false).build()).build()).build().loadAd(new PublisherAdRequest.Builder().build());
    }


    public void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
        unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {

            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getPrice() == null) {
            unifiedNativeAdView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getPriceView()).setText(unifiedNativeAd.getPrice());
        }
        if (unifiedNativeAd.getStore() == null) {
            unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
        }
        if (unifiedNativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getAdvertiser() == null) {
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void destroyNative() {
        if (this.nativeAd != null) {
            this.nativeAd.destroy();
        }
    }
}
