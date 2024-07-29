package com.gabbar925.photoeditios.main.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.ColorSplashDialog;
import com.gabbar925.photoeditios.editor.featuresfoto.addtext.AddTextProperties;
import com.gabbar925.photoeditios.editor.featuresfoto.addtext.TextEditorDialogFragment;
import com.gabbar925.photoeditios.editor.featuresfoto.adjust.AdjustAdapter;
import com.gabbar925.photoeditios.editor.featuresfoto.adjust.AdjustListener;
import com.gabbar925.photoeditios.editor.featuresfoto.beauty.BeautyDialog;
import com.gabbar925.photoeditios.editor.featuresfoto.crop.CropDialogFragment;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.BrushColorListener;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.BrushMagicListener;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.ColorAdapter;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.MagicBrushAdapter;
import com.gabbar925.photoeditios.editor.featuresfoto.insta.InstaDialog;
import com.gabbar925.photoeditios.editor.featuresfoto.mosaic.MosaicDialog;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.PhotoPicker;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.utils.PermissionsUtils;
import com.gabbar925.photoeditios.editor.featuresfoto.splash.SplashDialog;
import com.gabbar925.photoeditios.editor.featuresfoto.sticker.adapter.RecyclerTabLayout;
import com.gabbar925.photoeditios.editor.featuresfoto.sticker.adapter.StickerAdapter;
import com.gabbar925.photoeditios.editor.featuresfoto.sticker.adapter.TopTabEditAdapter;
import com.gabbar925.photoeditios.editor.filterscolor.DegreeSeekBar;
import com.gabbar925.photoeditios.editor.filterscolor.FilterListener;
import com.gabbar925.photoeditios.editor.filterscolor.FilterUtils;
import com.gabbar925.photoeditios.editor.filterscolor.FilterViewAdapter;
import com.gabbar925.photoeditios.editor.sticker.BitmapStickerIcon;
import com.gabbar925.photoeditios.editor.sticker.DrawableSticker;
import com.gabbar925.photoeditios.editor.sticker.Sticker;
import com.gabbar925.photoeditios.editor.sticker.StickerView;
import com.gabbar925.photoeditios.editor.sticker.TextSticker;
import com.gabbar925.photoeditios.editor.sticker.event.AlignHorizontallyEvent;
import com.gabbar925.photoeditios.editor.sticker.event.DeleteIconEvent;
import com.gabbar925.photoeditios.editor.sticker.event.EditTextIconEvent;
import com.gabbar925.photoeditios.editor.sticker.event.FlipHorizontallyEvent;
import com.gabbar925.photoeditios.editor.sticker.event.ZoomIconEvent;
import com.gabbar925.photoeditios.main.BaseActivity;
import com.gabbar925.photoeditios.main.adapter.EditingToolsAdapter;
import com.gabbar925.photoeditios.main.adapter.ToolType;
import com.gabbar925.photoeditios.util.AssetUtils;
import com.gabbar925.photoeditios.util.FileUtils;
import com.gabbar925.photoeditios.util.SharePreferenceUtil;
import com.gabbar925.photoeditios.util.SystemUtil;
import com.gabbar925.photoeditios.view.DrawBitmapModel;
import com.gabbar925.photoeditios.view.OnPhotoEditorListener;
import com.gabbar925.photoeditios.view.OnSaveBitmap;
import com.gabbar925.photoeditios.view.PhotoEditor;
import com.gabbar925.photoeditios.view.PhotoEditorView;
import com.gabbar925.photoeditios.view.ViewType;
import com.gabbaraman.edit.AdmobHelp;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hold1.keyboardheightprovider.KeyboardHeightProvider;

import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener, View.OnClickListener, StickerAdapter.OnClickStickerListener, CropDialogFragment.OnCropPhoto, BrushColorListener, BrushMagicListener, InstaDialog.InstaSaveListener, SplashDialog.SplashDialogListener, BeautyDialog.OnBeautySave, MosaicDialog.MosaicDialogListener, EditingToolsAdapter.OnItemSelected, FilterListener, AdjustListener {
    private static final String TAG = "EditImageActivity";
    private static EditImageActivity instance;
    private LinearLayout ladView,adView;
    public ImageView addNewSticker;
    private ImageView addNewText;
    private ConstraintLayout adjustLayout;
    private DegreeSeekBar adjustSeekBar;
    private TextView brush;
    private TextView brushBlur;
    private ConstraintLayout brushLayout;
    private SeekBar brushSize;
    private ImageView compareAdjust;

    public ImageView compareFilter;

    public ImageView compareOverlay;
    private ToolType currentMode = ToolType.NONE;
    private ImageView erase;
    private SeekBar eraseSize;

    public SeekBar filterIntensity;

    public ConstraintLayout filterLayout;
    private KeyboardHeightProvider keyboardHeightProvider;
    private RelativeLayout loadingView;

    public List<Bitmap> lstBitmapWithFilter = new ArrayList();

    public List<Bitmap> lstBitmapWithOverlay = new ArrayList();

    public AdjustAdapter mAdjustAdapter;
    private RecyclerView mColorBush;
    private EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    public CGENativeLibrary.LoadImageCallback mLoadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String str, Object obj) {
            try {
                return BitmapFactory.decodeStream(EditImageActivity.this.getAssets().open(str));
            } catch (IOException unused) {
                return null;
            }
        }

        public void loadImageOK(Bitmap bitmap, Object obj) {
            bitmap.recycle();
        }
    };
    private RecyclerView mMagicBrush;

    public PhotoEditor mPhotoEditor;

    public PhotoEditorView mPhotoEditorView;
    private ConstraintLayout mRootView;
    private RecyclerView mRvAdjust;

    public RecyclerView mRvFilters;

    public RecyclerView mRvOverlays;

    public RecyclerView mRvTools;
    private TextView magicBrush;
    View.OnTouchListener onCompareTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(0.0f);
                    return true;
                case 1:
                    EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(1.0f);
                    return false;
                default:
                    return true;
            }
        }
    };

    public SeekBar overlayIntensity;

    public ConstraintLayout overlayLayout;
    private ImageView redo;
    private Button saveBitmap;
    private ConstraintLayout saveControl;

    public SeekBar stickerAlpha;
    private ConstraintLayout stickerLayout;

    public TextEditorDialogFragment.TextEditor textEditor;

    public TextEditorDialogFragment textEditorDialogFragment;
    private ConstraintLayout textLayout;
    private ImageView undo;
    public RelativeLayout wrapPhotoView;

    public LinearLayout wrapStickerList;
//i think there is anothor actuvity for collage
    public void onEditTextChangeListener(View view, String str, int i) {
    }

    public static EditImageActivity getInstance() {
        return instance;
    }


    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        makeFullScreen();

        setContentView((int) R.layout.activity_edit_image);
        initViews();
       //i called it

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView;
        mAdView = findViewById(R.id.banneradmob);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
            @Override
            public void onAdClosed() {

            }
        });
//banner ads
        AdmobHelp.getInstance().init(this);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
                            @Override
                            public void onAdClosed() {

                            }
                        });
                    }

                });
            }
        },2,2, TimeUnit.MINUTES );








        CGENativeLibrary.setLoadImageCallback(this.mLoadImageCallback, (Object) null);


        if (Build.VERSION.SDK_INT < 26) {
            getWindow().setSoftInputMode(48);
        }
        this.mRvTools.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mRvTools.setAdapter(this.mEditingToolsAdapter);
        this.mRvFilters.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mRvFilters.setHasFixedSize(true);
        this.mRvOverlays.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mRvOverlays.setHasFixedSize(true);
        this.mRvAdjust.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mRvAdjust.setHasFixedSize(true);
        this.mAdjustAdapter = new AdjustAdapter(getApplicationContext(), this);
        this.mRvAdjust.setAdapter(this.mAdjustAdapter);
        this.mColorBush.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mColorBush.setHasFixedSize(true);
        this.mColorBush.setAdapter(new ColorAdapter(getApplicationContext(), this));
        this.mMagicBrush.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mMagicBrush.setHasFixedSize(true);
        this.mMagicBrush.setAdapter(new MagicBrushAdapter(getApplicationContext(), this));
        this.mPhotoEditor = new PhotoEditor.Builder(this, this.mPhotoEditorView).setPinchTextScalable(true).build();
        this.mPhotoEditor.setOnPhotoEditorListener(this);
        toogleDrawBottomToolbar(false);
        this.brushLayout.setAlpha(0.0f);
        this.adjustLayout.setAlpha(0.0f);
        this.filterLayout.setAlpha(0.0f);
        this.stickerLayout.setAlpha(0.0f);
        this.textLayout.setAlpha(0.0f);
        this.overlayLayout.setAlpha(0.0f);
        findViewById(R.id.activitylayout).post(new Runnable() {
            public final void run() {
                EditImageActivity.lambda$onCreate$0(EditImageActivity.this);
            }
        });
        new Handler().postDelayed(new Runnable() {
            public final void run() {
                EditImageActivity.lambda$onCreate$1(EditImageActivity.this);
            }
        }, 1000);
        SharePreferenceUtil.setHeightOfKeyboard(getApplicationContext(), 0);
        this.keyboardHeightProvider = new KeyboardHeightProvider(this);
        this.keyboardHeightProvider.addKeyboardListener(new KeyboardHeightProvider.KeyboardListener() {
            public final void onHeightChanged(int i) {
                EditImageActivity.lambda$onCreate$2(EditImageActivity.this, i);
            }
        });
        instance = this;
        Bundle extras = getIntent().getExtras();
        new OnLoadBitmapFromUri().execute(new String[]{extras.getString(PhotoPicker.KEY_SELECTED_PHOTOS)});
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public static void lambda$onCreate$0(EditImageActivity editImageActivity) {
        editImageActivity.slideDown(editImageActivity.brushLayout);
        editImageActivity.slideDown(editImageActivity.adjustLayout);
        editImageActivity.slideDown(editImageActivity.filterLayout);
        editImageActivity.slideDown(editImageActivity.stickerLayout);
        editImageActivity.slideDown(editImageActivity.textLayout);
        editImageActivity.slideDown(editImageActivity.overlayLayout);
    }

    public static void lambda$onCreate$1(EditImageActivity editImageActivity) {
        editImageActivity.brushLayout.setAlpha(1.0f);
        editImageActivity.adjustLayout.setAlpha(1.0f);
        editImageActivity.filterLayout.setAlpha(1.0f);
        editImageActivity.stickerLayout.setAlpha(1.0f);
        editImageActivity.textLayout.setAlpha(1.0f);
        editImageActivity.overlayLayout.setAlpha(1.0f);
    }

    public static void lambda$onCreate$2(EditImageActivity editImageActivity, int i) {
        if (i <= 0) {
            SharePreferenceUtil.setHeightOfNotch(editImageActivity.getApplicationContext(), -i);
        } else if (editImageActivity.textEditorDialogFragment != null) {
            editImageActivity.textEditorDialogFragment.updateAddTextBottomToolbarHeight(SharePreferenceUtil.getHeightOfNotch(editImageActivity.getApplicationContext()) + i);
            SharePreferenceUtil.setHeightOfKeyboard(editImageActivity.getApplicationContext(), i + SharePreferenceUtil.getHeightOfNotch(editImageActivity.getApplicationContext()));
        }
    }

    private void toogleDrawBottomToolbar(boolean z) {
        int i = !z ? View.GONE : View.VISIBLE;
        this.brush.setVisibility(i);
        this.magicBrush.setVisibility(i);
        this.brushBlur.setVisibility(i);
        this.erase.setVisibility(i);
        this.undo.setVisibility(i);
        this.redo.setVisibility(i);
    }


    public void showEraseBrush() {
        this.brushSize.setVisibility(View.GONE);
        this.mColorBush.setVisibility(View.GONE);
        this.eraseSize.setVisibility(View.VISIBLE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.erase.setImageResource(R.drawable.erase_selected);
        this.mPhotoEditor.brushEraser();
        this.eraseSize.setProgress(20);
    }


    public void showColorBlurBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.VISIBLE);
        ColorAdapter colorAdapter = (ColorAdapter) this.mColorBush.getAdapter();
        colorAdapter.setSelectedColorIndex(0);
        this.mColorBush.scrollToPosition(0);
        colorAdapter.notifyDataSetChanged();
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.erase.setImageResource(R.drawable.erase);
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.brushBlur.setBackground(getResources().getDrawable(R.drawable.border_bottom));
        this.brushBlur.setTextColor(getResources().getColor(R.color.colorAccent));
        this.mPhotoEditor.setBrushMode(2);
        this.mPhotoEditor.setBrushDrawingMode(true);
        this.brushSize.setProgress(20);
    }


    public void showColorBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.VISIBLE);
        this.mColorBush.scrollToPosition(0);
        ColorAdapter colorAdapter = (ColorAdapter) this.mColorBush.getAdapter();
        colorAdapter.setSelectedColorIndex(0);
        colorAdapter.notifyDataSetChanged();
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.erase.setImageResource(R.drawable.erase);
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.brush.setBackground(getResources().getDrawable(R.drawable.border_bottom));
        this.brush.setTextColor(getResources().getColor(R.color.colorAccent));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.mPhotoEditor.setBrushMode(1);
        this.mPhotoEditor.setBrushDrawingMode(true);
        this.brushSize.setProgress(20);
    }


    public void showMagicBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.GONE);
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.VISIBLE);
        this.erase.setImageResource(R.drawable.erase);
        this.magicBrush.setBackground(getResources().getDrawable(R.drawable.border_bottom));
        this.magicBrush.setTextColor(getResources().getColor(R.color.colorAccent));
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(getResources().getColor(R.color.text_color_edit));
        this.mPhotoEditor.setBrushMagic(MagicBrushAdapter.lstDrawBitmapModel(getApplicationContext()).get(0));
        this.mPhotoEditor.setBrushMode(3);
        this.mPhotoEditor.setBrushDrawingMode(true);
        MagicBrushAdapter magicBrushAdapter = (MagicBrushAdapter) this.mMagicBrush.getAdapter();
        magicBrushAdapter.setSelectedColorIndex(0);
        this.mMagicBrush.scrollToPosition(0);
        magicBrushAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        this.wrapStickerList = (LinearLayout) findViewById(R.id.wrapStickerList);
        this.mPhotoEditorView = (PhotoEditorView) findViewById(R.id.photoEditorView);
        this.mPhotoEditorView.setVisibility(View.INVISIBLE);
        this.mRvTools = (RecyclerView) findViewById(R.id.rvConstraintTools);
        this.mRvFilters = (RecyclerView) findViewById(R.id.rvFilterView);
        this.mRvOverlays = (RecyclerView) findViewById(R.id.rvOverlayView);
        this.mRvAdjust = (RecyclerView) findViewById(R.id.rvAdjustView);
        this.mRootView = (ConstraintLayout) findViewById(R.id.rootView);
        this.filterLayout = (ConstraintLayout) findViewById(R.id.filterLayout);
        this.overlayLayout = (ConstraintLayout) findViewById(R.id.overlayLayout);
        this.adjustLayout = (ConstraintLayout) findViewById(R.id.adjustLayout);
        this.stickerLayout = (ConstraintLayout) findViewById(R.id.stickerLayout);
        this.textLayout = (ConstraintLayout) findViewById(R.id.textControl);
        ViewPager viewPager = (ViewPager) findViewById(R.id.sticker_viewpaper);
        this.filterIntensity = (SeekBar) findViewById(R.id.filterIntensity);
        this.overlayIntensity = (SeekBar) findViewById(R.id.overlayIntensity);
        this.stickerAlpha = (SeekBar) findViewById(R.id.stickerAlpha);
        this.stickerAlpha.setVisibility(View.GONE);
        this.brushLayout = (ConstraintLayout) findViewById(R.id.brushLayout);
        this.mColorBush = (RecyclerView) findViewById(R.id.rvColorBush);
        this.mMagicBrush = (RecyclerView) findViewById(R.id.rvMagicBush);
        this.wrapPhotoView = (RelativeLayout) findViewById(R.id.wrap_photo_view);
        this.brush = (TextView) findViewById(R.id.draw);
        this.magicBrush = (TextView) findViewById(R.id.brush_magic);
        this.erase = (ImageView) findViewById(R.id.erase);
        this.undo = (ImageView) findViewById(R.id.undo);
        this.undo.setVisibility(View.GONE);
        this.redo = (ImageView) findViewById(R.id.redo);
        this.redo.setVisibility(View.GONE);
        this.brushBlur = (TextView) findViewById(R.id.brush_blur);
        this.brushSize = (SeekBar) findViewById(R.id.brushSize);
        this.eraseSize = (SeekBar) findViewById(R.id.eraseSize);
        this.loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        this.loadingView.setVisibility(View.VISIBLE);
        this.saveBitmap = (Button) findViewById(R.id.save);
        this.saveControl = (ConstraintLayout) findViewById(R.id.saveControl);
        this.saveBitmap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (PermissionsUtils.checkWriteStoragePermission((Activity) EditImageActivity.this)) {
                    new SaveBitmapAsFile().execute(new Void[0]);

                }


            }
        });
        this.compareAdjust = (ImageView) findViewById(R.id.compareAdjust);
        this.compareAdjust.setOnTouchListener(this.onCompareTouchListener);
        this.compareAdjust.setVisibility(View.GONE);
        this.compareFilter = (ImageView) findViewById(R.id.compareFilter);
        this.compareFilter.setOnTouchListener(this.onCompareTouchListener);
        this.compareFilter.setVisibility(View.GONE);
        this.compareOverlay = (ImageView) findViewById(R.id.compareOverlay);
        this.compareOverlay.setOnTouchListener(this.onCompareTouchListener);
        this.compareOverlay.setVisibility(View.GONE);
        findViewById(R.id.exitEditMode).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.super.onBackPressed();
            }
        });
        this.erase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.showEraseBrush();
            }
        });
        this.brush.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.showColorBrush();
            }
        });
        this.magicBrush.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.showMagicBrush();
            }
        });
        this.brushBlur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.showColorBlurBrush();
            }
        });
        this.eraseSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditImageActivity.this.mPhotoEditor.setBrushEraserSize((float) i);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                EditImageActivity.this.mPhotoEditor.brushEraser();
            }
        });
        this.brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditImageActivity.this.mPhotoEditor.setBrushSize((float) (i + 10));
            }
        });
        this.stickerAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = EditImageActivity.this.mPhotoEditorView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.addNewSticker = (ImageView) findViewById(R.id.addNewSticker);
        this.addNewSticker.setVisibility(View.GONE);
        this.addNewSticker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.addNewSticker.setVisibility(View.GONE);
                EditImageActivity.this.slideUp(EditImageActivity.this.wrapStickerList);
            }
        });
        this.addNewText = (ImageView) findViewById(R.id.addNewText);
        this.addNewText.setVisibility(View.GONE);
        this.addNewText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditImageActivity.this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                EditImageActivity.this.openTextFragment();
            }
        });
        this.adjustSeekBar = (DegreeSeekBar) findViewById(R.id.adjustLevel);
        this.adjustSeekBar.setDegreeRange(-50, 50);
        this.adjustSeekBar.setScrollingListener(new DegreeSeekBar.ScrollingListener() {
            public void onScrollEnd() {
            }

            public void onScrollStart() {
            }

            public void onScroll(int i) {
                AdjustAdapter.AdjustModel currentAdjustModel = EditImageActivity.this.mAdjustAdapter.getCurrentAdjustModel();
                currentAdjustModel.originValue = (((float) Math.abs(i + 50)) * ((currentAdjustModel.maxValue - ((currentAdjustModel.maxValue + currentAdjustModel.minValue) / 2.0f)) / 50.0f)) + currentAdjustModel.minValue;
                EditImageActivity.this.mPhotoEditor.setAdjustFilter(EditImageActivity.this.mAdjustAdapter.getFilterConfig());
            }
        });
        BitmapStickerIcon bitmapStickerIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_close_white_18dp), 0, BitmapStickerIcon.REMOVE);
        bitmapStickerIcon.setIconEvent(new DeleteIconEvent());
        BitmapStickerIcon bitmapStickerIcon2 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_scale_white_18dp), 3, BitmapStickerIcon.ZOOM);
        bitmapStickerIcon2.setIconEvent(new ZoomIconEvent());
        BitmapStickerIcon bitmapStickerIcon3 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_flip_white_18dp), 1, BitmapStickerIcon.FLIP);
        bitmapStickerIcon3.setIconEvent(new FlipHorizontallyEvent());
        BitmapStickerIcon bitmapStickerIcon4 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.icon_rotate), 3, BitmapStickerIcon.ROTATE);
        bitmapStickerIcon4.setIconEvent(new ZoomIconEvent());
        BitmapStickerIcon bitmapStickerIcon5 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.icon_edit), 1, BitmapStickerIcon.EDIT);
        bitmapStickerIcon5.setIconEvent(new EditTextIconEvent());
        BitmapStickerIcon bitmapStickerIcon6 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.icon_center), 2, BitmapStickerIcon.ALIGN_HORIZONTALLY);
        bitmapStickerIcon6.setIconEvent(new AlignHorizontallyEvent());
        this.mPhotoEditorView.setIcons(Arrays.asList(new BitmapStickerIcon[]{bitmapStickerIcon, bitmapStickerIcon2, bitmapStickerIcon3, bitmapStickerIcon5, bitmapStickerIcon4, bitmapStickerIcon6}));
        this.mPhotoEditorView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPhotoEditorView.setLocked(false);
        this.mPhotoEditorView.setConstrained(true);
        this.mPhotoEditorView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            public void onStickerDragFinished(@NonNull Sticker sticker) {
            }

            public void onStickerFlipped(@NonNull Sticker sticker) {
            }

            public void onStickerTouchedDown(@NonNull Sticker sticker) {
            }

            public void onStickerZoomFinished(@NonNull Sticker sticker) {
            }

            public void onTouchDownForBeauty(float f, float f2) {
            }

            public void onTouchDragForBeauty(float f, float f2) {
            }

            public void onTouchUpForBeauty(float f, float f2) {
            }

            public void onStickerAdded(@NonNull Sticker sticker) {
                EditImageActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
                EditImageActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
            }

            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(SupportMenu.CATEGORY_MASK);
                    EditImageActivity.this.mPhotoEditorView.replace(sticker);
                    EditImageActivity.this.mPhotoEditorView.invalidate();
                }
                EditImageActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
                EditImageActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
            }

            public void onStickerDeleted(@NonNull Sticker sticker) {
                EditImageActivity.this.stickerAlpha.setVisibility(View.GONE);
            }

            public void onStickerTouchOutside() {
                EditImageActivity.this.stickerAlpha.setVisibility(View.GONE);
            }

            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    sticker.setShow(false);
                    EditImageActivity.this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                    TextEditorDialogFragment unused = EditImageActivity.this.textEditorDialogFragment = TextEditorDialogFragment.show(EditImageActivity.this, ((TextSticker) sticker).getAddTextProperties());
                    TextEditorDialogFragment.TextEditor unused2 = EditImageActivity.this.textEditor = new TextEditorDialogFragment.TextEditor() {
                        public void onDone(AddTextProperties addTextProperties) {
                            EditImageActivity.this.mPhotoEditorView.getStickers().remove(EditImageActivity.this.mPhotoEditorView.getLastHandlingSticker());
                            EditImageActivity.this.mPhotoEditorView.addSticker(new TextSticker(EditImageActivity.this, addTextProperties));
                        }

                        public void onBackButton() {
                            EditImageActivity.this.mPhotoEditorView.showLastHandlingSticker();
                        }
                    };
                    EditImageActivity.this.textEditorDialogFragment.setOnTextEditorListener(EditImageActivity.this.textEditor);
                }
            }
        });
        this.filterIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditImageActivity.this.mPhotoEditorView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
        this.overlayIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditImageActivity.this.mPhotoEditorView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        final int i = point.x;
        viewPager.setAdapter(new PagerAdapter() {
            public int getCount() {
                return 13;
            }

            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view.equals(obj);
            }

            public void destroyItem(@NonNull View view, int i, @NonNull Object obj) {
                ((ViewPager) view).removeView((View) obj);
            }

            @NonNull
            public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(EditImageActivity.this.getBaseContext()).inflate(R.layout.sticker_items, (ViewGroup) null, false);
                RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(EditImageActivity.this.getApplicationContext(), 4));
                switch (i) {
                    case 0:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstCatFaces(), i, EditImageActivity.this));
                        break;
                    case 1:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstCkeeks(), i, EditImageActivity.this));
                        break;
                    case 2:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstDiadems(), i, EditImageActivity.this));
                        break;
                    case 3:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstEyes(), i, EditImageActivity.this));
                        break;
                    case 4:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstGiddy(), i, EditImageActivity.this));
                        break;
                    case 5:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstGlasses(), i, EditImageActivity.this));
                        break;
                    case 6:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstTies(), i, EditImageActivity.this));
                        break;
                    case 7:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstHeardes(), i, EditImageActivity.this));
                        break;
                    case 8:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstEmoj(), i, EditImageActivity.this));
                        break;
                    case 9:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstTexts(), i, EditImageActivity.this));
                        break;
                    case 10:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstOthers(), i, EditImageActivity.this));
                        break;
                    case 11:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstMuscle(), i, EditImageActivity.this));
                        break;
                    case 12:
                        recyclerView.setAdapter(new StickerAdapter(EditImageActivity.this.getApplicationContext(), AssetUtils.lstTatoos(), i, EditImageActivity.this));
                        break;
                }
                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new TopTabEditAdapter(viewPager, getApplicationContext()));
        recyclerTabLayout.setPositionThreshold(0.5f);
        recyclerTabLayout.setBackgroundColor(getResources().getColor(R.color.basic_white));
    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.loadingView.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.loadingView.setVisibility(View.GONE);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void onAddViewListener(ViewType viewType, int i) {
        String str = TAG;
        Log.d(str, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(int i) {
        String str = TAG;
        Log.d(str, "onRemoveViewListener() called with: numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(ViewType viewType, int i) {
        String str = TAG;
        Log.d(str, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onStartViewChangeListener(ViewType viewType) {
        String str = TAG;
        Log.d(str, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onStopViewChangeListener(ViewType viewType) {
        String str = TAG;
        Log.d(str, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCloseAdjust:
            case R.id.imgCloseBrush:
            case R.id.imgCloseFilter:
            case R.id.imgCloseOverlay:
            case R.id.imgCloseSticker:
            case R.id.imgCloseText:
                slideDownSaveView();
                onBackPressed();
                return;
            case R.id.imgSaveAdjust:
                new SaveFilterAsBitmap().execute(new Void[0]);
                this.compareAdjust.setVisibility(View.GONE);
                slideDown(this.adjustLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.imgSaveBrush:
                showLoading(true);
                runOnUiThread(new Runnable() {
                    public final void run() {
                        EditImageActivity.lambda$onClick$3(EditImageActivity.this);
                    }
                });
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.imgSaveFilter:
                new SaveFilterAsBitmap().execute(new Void[0]);
                this.compareFilter.setVisibility(View.GONE);
                slideDown(this.filterLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.imgSaveOverlay:
                new SaveFilterAsBitmap().execute(new Void[0]);
                slideDown(this.overlayLayout);
                slideUp(this.mRvTools);
                this.compareOverlay.setVisibility(View.GONE);
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.imgSaveSticker:
                this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                this.mPhotoEditorView.setLocked(true);
                this.stickerAlpha.setVisibility(View.GONE);
                this.addNewSticker.setVisibility(View.GONE);
                if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                    new SaveStickerAsBitmap().execute(new Void[0]);
                }
                slideUp(this.wrapStickerList);
                slideDown(this.stickerLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.imgSaveText:
                this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                this.mPhotoEditorView.setLocked(true);
                this.addNewText.setVisibility(View.GONE);
                if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                    new SaveStickerAsBitmap().execute(new Void[0]);
                }
                slideDown(this.textLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = ToolType.NONE;
                return;
            case R.id.redo:
                this.mPhotoEditor.redoBrush();
                return;
            case R.id.undo:
                this.mPhotoEditor.undoBrush();
                return;
            default:
                return;
        }
    }

    public static void lambda$onClick$3(EditImageActivity editImageActivity) {
        editImageActivity.mPhotoEditor.setBrushDrawingMode(false);
        editImageActivity.undo.setVisibility(View.GONE);
        editImageActivity.redo.setVisibility(View.GONE);
        editImageActivity.erase.setVisibility(View.GONE);
        editImageActivity.slideDown(editImageActivity.brushLayout);
        editImageActivity.slideUp(editImageActivity.mRvTools);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(editImageActivity.mRootView);
        if (!SharePreferenceUtil.isPurchased(editImageActivity.getApplicationContext())) {
            constraintSet.connect(editImageActivity.wrapPhotoView.getId(), 3, editImageActivity.mRootView.getId(), 3, SystemUtil.dpToPx(editImageActivity.getApplicationContext(), 50));
        } else {
            constraintSet.connect(editImageActivity.wrapPhotoView.getId(), 3, editImageActivity.mRootView.getId(), 3, 0);
        }
        constraintSet.connect(editImageActivity.wrapPhotoView.getId(), 1, editImageActivity.mRootView.getId(), 1, 0);
        constraintSet.connect(editImageActivity.wrapPhotoView.getId(), 4, editImageActivity.mRvTools.getId(), 3, 0);
        constraintSet.connect(editImageActivity.wrapPhotoView.getId(), 2, editImageActivity.mRootView.getId(), 2, 0);
        constraintSet.applyTo(editImageActivity.mRootView);
        editImageActivity.mPhotoEditorView.setImageSource(editImageActivity.mPhotoEditor.getBrushDrawingView().getDrawBitmap(editImageActivity.mPhotoEditorView.getCurrentBitmap()));
        editImageActivity.mPhotoEditor.clearBrushAllViews();
        editImageActivity.showLoading(false);
        editImageActivity.updateLayout();
    }


    public void onPause() {
        super.onPause();
        this.keyboardHeightProvider.onPause();
    }

    public void onResume() {
        super.onResume();
        this.keyboardHeightProvider.onResume();
    }

    public void isPermissionGranted(boolean z, String str) {
        if (z) {
            new SaveBitmapAsFile().execute(new Void[0]);
        }
    }

    public void onFilterSelected(String str) {
        this.mPhotoEditor.setFilterEffect(str);
        this.filterIntensity.setProgress(100);
        this.overlayIntensity.setProgress(70);
        if (this.currentMode == ToolType.OVERLAY) {
            this.mPhotoEditorView.getGLSurfaceView().setFilterIntensity(0.7f);
        }
    }


    public void openTextFragment() {
        this.textEditorDialogFragment = TextEditorDialogFragment.show(this);
        this.textEditor = new TextEditorDialogFragment.TextEditor() {
            public void onDone(AddTextProperties addTextProperties) {
                EditImageActivity.this.mPhotoEditorView.addSticker(new TextSticker(EditImageActivity.this.getApplicationContext(), addTextProperties));
            }

            public void onBackButton() {
                if (EditImageActivity.this.mPhotoEditorView.getStickers().isEmpty()) {
                    EditImageActivity.this.onBackPressed();
                }
            }
        };
        this.textEditorDialogFragment.setOnTextEditorListener(this.textEditor);
    }

    public void onToolSelected(ToolType toolType) {
        this.currentMode = toolType;
        switch (toolType) {
            case BRUSH:
                showColorBrush();
                this.mPhotoEditor.setBrushDrawingMode(true);
                slideDown(this.mRvTools);
                slideUp(this.brushLayout);
                slideUpSaveControl();
                // delete this method,ok
                toogleDrawBottomToolbar(true);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(this.mRootView);
                if (!SharePreferenceUtil.isPurchased(getApplicationContext())) {
                    constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, SystemUtil.dpToPx(getApplicationContext(), 50));
                } else {
                    constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, 0);
                }
                ConstraintSet constraintSet2 = constraintSet;
                constraintSet2.connect(this.wrapPhotoView.getId(), 1, this.mRootView.getId(), 1, 0);
                constraintSet2.connect(this.wrapPhotoView.getId(), 4, this.brushLayout.getId(), 3, 0);
                constraintSet2.connect(this.wrapPhotoView.getId(), 2, this.mRootView.getId(), 2, 0);
                constraintSet.applyTo(this.mRootView);
                this.mPhotoEditor.setBrushMode(1);
                updateLayout();
                break;
            case TEXT:
                this.saveControl.setVisibility(View.INVISIBLE);
                this.mPhotoEditorView.setLocked(false);
                openTextFragment();
                slideDown(this.mRvTools);
                slideUp(this.textLayout);
                this.addNewText.setVisibility(View.VISIBLE);
                break;
            case ADJUST:
                slideUpSaveView();
                this.compareAdjust.setVisibility(View.VISIBLE);
                this.adjustSeekBar.setCurrentDegrees(0);
                this.mAdjustAdapter = new AdjustAdapter(getApplicationContext(), this);
                this.mRvAdjust.setAdapter(this.mAdjustAdapter);
                this.mPhotoEditor.setAdjustFilter(this.mAdjustAdapter.getFilterConfig());
                slideUp(this.adjustLayout);
                slideDown(this.mRvTools);
                break;
            case FILTER:
                slideUpSaveView();
                new LoadFilterBitmap().execute(new Void[0]);
                break;
            case STICKER:
                this.saveControl.setVisibility(View.INVISIBLE);
                this.mPhotoEditorView.setLocked(false);
                slideDown(this.mRvTools);
                slideUp(this.stickerLayout);
                break;
            case OVERLAY:
                slideUpSaveView();
                new LoadOverlayBitmap().execute(new Void[0]);
                break;
            case INSTA:
                new ShowInstaDialog().execute(new Void[0]);
                break;
            case SPLASH:
                new ShowSplashDialog(true).execute(new Void[0]);
                break;
            case BLUR:
                new ShowSplashDialog(false).execute(new Void[0]);
                break;
            case MOSAIC:
                new ShowMosaicDialog().execute(new Void[0]);
                break;
            case COLOR:
                ColorSplashDialog.show(this, this.mPhotoEditorView.getCurrentBitmap());
                break;
            case CROP:
                CropDialogFragment.show(this, this, this.mPhotoEditorView.getCurrentBitmap());
                break;
            case BEAUTY:
                try {
                    BeautyDialog.show(this, this.mPhotoEditorView.getCurrentBitmap(), this);
                    break;
                } catch (Exception unused) {
                    break;
                }
        }
        this.mPhotoEditorView.setHandlingSticker((Sticker) null);
    }

    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getHeight(), 0.0f}).start();
    }

    public void slideUpSaveView() {
        this.saveControl.setVisibility(View.GONE);
    }

    public void slideUpSaveControl() {
        this.saveControl.setVisibility(View.GONE);
    }

    public void slideDownSaveControl() {
        this.saveControl.setVisibility(View.VISIBLE);
    }

    public void slideDownSaveView() {
        this.saveControl.setVisibility(View.VISIBLE);
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0f, (float) view.getHeight()}).start();
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        if (this.currentMode == null) {
            AdmobHelp.getInstance().showInterstitialAd(new AdmobHelp.AdCloseListener() {
                public void onAdClosed() {
                    EditImageActivity.this.finish();
                }
            });
            return;
        }
        try {
            switch (this.currentMode) {
                case BRUSH:
                    slideDown(this.brushLayout);
                    slideUp(this.mRvTools);
                    slideDownSaveControl();
                    this.undo.setVisibility(View.GONE);
                    this.redo.setVisibility(View.GONE);
                    this.erase.setVisibility(View.GONE);
                    this.mPhotoEditor.setBrushDrawingMode(false);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(this.mRootView);
                    if (!SharePreferenceUtil.isPurchased(getApplicationContext())) {
                        constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, SystemUtil.dpToPx(getApplicationContext(), 50));
                    } else {
                        constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, 0);
                    }
                    constraintSet.connect(this.wrapPhotoView.getId(), 1, this.mRootView.getId(), 1, 0);
                    constraintSet.connect(this.wrapPhotoView.getId(), 4, this.mRvTools.getId(), 3, 0);
                    constraintSet.connect(this.wrapPhotoView.getId(), 2, this.mRootView.getId(), 2, 0);
                    constraintSet.applyTo(this.mRootView);
                    this.mPhotoEditor.clearBrushAllViews();
                    slideDownSaveView();
                    this.currentMode = ToolType.NONE;
                    updateLayout();
                    return;
                case TEXT:
                    if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                        this.mPhotoEditorView.getStickers().clear();
                        this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                    }
                    slideDown(this.textLayout);
                    this.addNewText.setVisibility(View.GONE);
                    this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                    slideUp(this.mRvTools);
                    this.mPhotoEditorView.setLocked(true);
                    slideDownSaveView();
                    this.currentMode = ToolType.NONE;
                    return;
                case ADJUST:
                    this.mPhotoEditor.setFilterEffect("");
                    this.compareAdjust.setVisibility(View.GONE);
                    slideDown(this.adjustLayout);
                    slideUp(this.mRvTools);
                    slideDownSaveView();
                    this.currentMode = ToolType.NONE;
                    return;
                case FILTER:
                    slideDown(this.filterLayout);
                    slideUp(this.mRvTools);
                    slideDownSaveView();
                    this.mPhotoEditor.setFilterEffect("");
                    this.compareFilter.setVisibility(View.GONE);
                    this.lstBitmapWithFilter.clear();
                    this.mRvFilters.getAdapter().notifyDataSetChanged();
                    this.currentMode = ToolType.NONE;
                    return;
                case STICKER:
                    if (this.mPhotoEditorView.getStickers().size() <= 0) {
                        slideUp(this.wrapStickerList);
                        slideDown(this.stickerLayout);
                        this.addNewSticker.setVisibility(View.GONE);
                        this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                        slideUp(this.mRvTools);
                        this.mPhotoEditorView.setLocked(true);
                        this.currentMode = ToolType.NONE;
                    } else if (this.addNewSticker.getVisibility() == 0) {
                        this.mPhotoEditorView.getStickers().clear();
                        this.addNewSticker.setVisibility(View.GONE);
                        this.mPhotoEditorView.setHandlingSticker((Sticker) null);
                        slideUp(this.wrapStickerList);
                        slideDown(this.stickerLayout);
                        slideUp(this.mRvTools);
                        this.currentMode = ToolType.NONE;
                    } else {
                        slideDown(this.wrapStickerList);
                        this.addNewSticker.setVisibility(View.VISIBLE);
                    }
                    slideDownSaveView();
                    return;
                case OVERLAY:
                    this.mPhotoEditor.setFilterEffect("");
                    this.compareOverlay.setVisibility(View.GONE);
                    this.lstBitmapWithOverlay.clear();
                    slideUp(this.mRvTools);
                    slideDown(this.overlayLayout);
                    slideDownSaveView();
                    this.mRvOverlays.getAdapter().notifyDataSetChanged();
                    this.currentMode = ToolType.NONE;
                    return;
                case SPLASH:
                case BLUR:
                case MOSAIC:
                case CROP:
                case BEAUTY:
                    Toast.makeText(getApplicationContext(), getString(R.string.press_one_more_time), 0).show();
                    this.currentMode = null;
                    return;
                case NONE:
                    Toast.makeText(getApplicationContext(), getString(R.string.press_one_more_time), 0).show();
                    this.currentMode = null;
                    return;
                default:
                    super.onBackPressed();
                    return;
            }
        } catch (Exception unused) {
        }
    }


    public void onAdjustSelected(AdjustAdapter.AdjustModel adjustModel) {
        this.adjustSeekBar.setCurrentDegrees(((int) ((adjustModel.originValue - adjustModel.minValue) / ((adjustModel.maxValue - ((adjustModel.maxValue + adjustModel.minValue) / 2.0f)) / 50.0f))) - 50);
    }

    public void addSticker(Bitmap bitmap) {
        this.mPhotoEditorView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        slideDown(this.wrapStickerList);
        this.addNewSticker.setVisibility(View.VISIBLE);
    }

    public void finishCrop(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = ToolType.NONE;
        updateLayout();
    }

    public void onColorChanged(String str) {
        this.mPhotoEditor.setBrushColor(Color.parseColor(str));
    }

    public void instaSavedBitmap(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = ToolType.NONE;
        updateLayout();
    }

    public void onMagicChanged(DrawBitmapModel drawBitmapModel) {
        this.mPhotoEditor.setBrushMagic(drawBitmapModel);
    }

    public void onSaveSplash(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = ToolType.NONE;
    }

    public void onSaveMosaic(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = ToolType.NONE;
    }

    public void onBeautySave(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = ToolType.NONE;
    }

    class LoadFilterBitmap extends AsyncTask<Void, Void, Void> {
        LoadFilterBitmap() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public Void doInBackground(Void... voidArr) {
            try {
                EditImageActivity.this.lstBitmapWithFilter.clear();
                EditImageActivity.this.lstBitmapWithFilter.addAll(FilterUtils.getLstBitmapWithFilter(ThumbnailUtils.extractThumbnail(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 150, 150)));
                return null;
            } catch (Exception unused) {
                return null;
            }
        }


        public void onPostExecute(Void voidR) {
            try {
                if (EditImageActivity.this.lstBitmapWithFilter != null && EditImageActivity.this.lstBitmapWithFilter.size() > 0) {
                    EditImageActivity.this.mRvFilters.setAdapter(new FilterViewAdapter(EditImageActivity.this.lstBitmapWithFilter, EditImageActivity.this, EditImageActivity.this.getApplicationContext(), Arrays.asList(FilterUtils.EFFECT_CONFIGS)));
                    EditImageActivity.this.slideDown(EditImageActivity.this.mRvTools);
                    EditImageActivity.this.slideUp(EditImageActivity.this.filterLayout);
                    EditImageActivity.this.compareFilter.setVisibility(View.VISIBLE);
                    EditImageActivity.this.filterIntensity.setProgress(100);
                }
                EditImageActivity.this.showLoading(false);
            } catch (Exception unused) {
            }
        }
    }

    class ShowInstaDialog extends AsyncTask<Void, Bitmap, Bitmap> {
        ShowInstaDialog() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            return FilterUtils.getBlurImageFromBitmap(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 5.0f);
        }


        public void onPostExecute(Bitmap bitmap) {
            EditImageActivity.this.showLoading(false);
            InstaDialog.show(EditImageActivity.this, EditImageActivity.this, EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), bitmap);
        }
    }

    class ShowMosaicDialog extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        ShowMosaicDialog() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public List<Bitmap> doInBackground(Void... voidArr) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(FilterUtils.cloneBitmap(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap()));
            arrayList.add(FilterUtils.getBlurImageFromBitmap(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 8.0f));
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            EditImageActivity.this.showLoading(false);
            MosaicDialog.show(EditImageActivity.this, list.get(0), list.get(1), EditImageActivity.this);
        }
    }

    class LoadOverlayBitmap extends AsyncTask<Void, Void, Void> {
        LoadOverlayBitmap() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public Void doInBackground(Void... voidArr) {
            EditImageActivity.this.lstBitmapWithOverlay.clear();
            EditImageActivity.this.lstBitmapWithOverlay.addAll(FilterUtils.getLstBitmapWithOverlay(ThumbnailUtils.extractThumbnail(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 150, 150)));
            return null;
        }


        public void onPostExecute(Void voidR) {
            EditImageActivity.this.mRvOverlays.setAdapter(new FilterViewAdapter(EditImageActivity.this.lstBitmapWithOverlay, EditImageActivity.this, EditImageActivity.this.getApplicationContext(), Arrays.asList(FilterUtils.OVERLAY_CONFIG)));
            EditImageActivity.this.slideDown(EditImageActivity.this.mRvTools);
            EditImageActivity.this.slideUp(EditImageActivity.this.overlayLayout);
            EditImageActivity.this.compareOverlay.setVisibility(View.VISIBLE);
            EditImageActivity.this.overlayIntensity.setProgress(100);
            EditImageActivity.this.showLoading(false);
        }
    }

    class ShowSplashDialog extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplash;

        public ShowSplashDialog(boolean z) {
            this.isSplash = z;
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = EditImageActivity.this.mPhotoEditorView.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSplash) {
                arrayList.add(FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            } else {
                arrayList.add(FilterUtils.getBlurImageFromBitmap(currentBitmap, 3.0f));
            }
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            List<Bitmap> list2 = list;
            if (this.isSplash) {
                SplashDialog.show(EditImageActivity.this, list2.get(0), (Bitmap) null, list2.get(1), EditImageActivity.this, true);
            } else {
                SplashDialog.show(EditImageActivity.this, list2.get(0), list2.get(1), (Bitmap) null, EditImageActivity.this, false);
            }
            EditImageActivity.this.showLoading(false);
        }
    }

    class SaveFilterAsBitmap extends AsyncTask<Void, Void, Bitmap> {
        SaveFilterAsBitmap() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            final Bitmap[] bitmapArr = {null};
            EditImageActivity.this.mPhotoEditorView.saveGLSurfaceViewAsBitmap(new OnSaveBitmap() {
                public void onFailure(Exception exc) {
                }

                public void onBitmapReady(Bitmap bitmap) {
                    bitmapArr[0] = bitmap;
                }
            });
            while (bitmapArr[0] == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bitmapArr[0];
        }


        public void onPostExecute(Bitmap bitmap) {
            EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            EditImageActivity.this.mPhotoEditorView.setFilterEffect("");
            EditImageActivity.this.showLoading(false);
        }
    }

    class SaveStickerAsBitmap extends AsyncTask<Void, Void, Bitmap> {
        SaveStickerAsBitmap() {
        }


        public void onPreExecute() {
            EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(0.0f);
            EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            final Bitmap[] bitmapArr = {null};
            while (bitmapArr[0] == null) {
                try {
                    EditImageActivity.this.mPhotoEditor.saveStickerAsBitmap(new OnSaveBitmap() {
                        public void onFailure(Exception exc) {
                        }

                        public void onBitmapReady(Bitmap bitmap) {
                            bitmapArr[0] = bitmap;
                        }
                    });
                    while (bitmapArr[0] == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception unused) {
                }
            }
            return bitmapArr[0];
        }


        public void onPostExecute(Bitmap bitmap) {
            EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            EditImageActivity.this.mPhotoEditorView.getStickers().clear();
            EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(1.0f);
            EditImageActivity.this.showLoading(false);
            EditImageActivity.this.updateLayout();
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }


        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(String... strArr) {
            try {
                boolean z = false;
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditImageActivity.this.getContentResolver(), fromFile);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                if (width > height) {
                    z = true;
                }
                if (1920 < Math.min(height, width)) {
                    float f = !z ? 1920.0f / ((float) width) : 1920.0f / ((float) height);
                    bitmap = FileUtils.getResizedBitmap(bitmap, (int) (((float) width) * f), (int) (((float) height) * f));
                }
                Bitmap rotateBitmap = SystemUtil.rotateBitmap(bitmap, new ExifInterface(EditImageActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
                if (rotateBitmap != bitmap) {
                    bitmap.recycle();
                }
                return rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(Bitmap bitmap) {
            EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            EditImageActivity.this.updateLayout();
        }
    }

    public void updateLayout() {
        this.mPhotoEditorView.postDelayed(new Runnable() {
            public void run() {
                try {
                    Display defaultDisplay = EditImageActivity.this.getWindowManager().getDefaultDisplay();
                    Point point = new Point();
                    defaultDisplay.getSize(point);
                    int i = point.x;
                    int height = EditImageActivity.this.wrapPhotoView.getHeight();
                    int i2 = EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().getRenderViewport().width;
                    float f = (float) EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().getRenderViewport().height;
                    float f2 = (float) i2;
                    if (((int) ((((float) i) * f) / f2)) <= height) {
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
                        layoutParams.addRule(13);
                        EditImageActivity.this.mPhotoEditorView.setLayoutParams(layoutParams);
                        EditImageActivity.this.mPhotoEditorView.setVisibility(View.VISIBLE);
                    } else {
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) ((((float) height) * f2) / f), -1);
                        layoutParams2.addRule(13);
                        EditImageActivity.this.mPhotoEditorView.setLayoutParams(layoutParams2);
                        EditImageActivity.this.mPhotoEditorView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception unused) {
                }
                EditImageActivity.this.showLoading(false);
            }
        }, 300);
    }

    class SaveBitmapAsFile extends AsyncTask<Void, String, String> {
        void lambda$doInBackground$0(String str, Uri uri) {
        }

        SaveBitmapAsFile() {
        }
// where is loadnative method

        public void onPreExecute() {
            EditImageActivity.this.showLoading(true);
        }


        public String doInBackground(Void... voidArr) {
            File saveBitmapAsFile = FileUtils.saveBitmapAsFile(EditImageActivity.this.mPhotoEditorView.getCurrentBitmap());
            try {
                MediaScannerConnection.scanFile(
                        EditImageActivity.this.getApplicationContext(), new String[]{saveBitmapAsFile.getAbsolutePath()}, (String[]) null

                        , new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                lambda$doInBackground$0(path, uri);
                            }
                        });
                return saveBitmapAsFile.getAbsolutePath();
            } catch (Exception unused) {
                return null;
            }
        }


        @SuppressLint("WrongConstant")
        public void onPostExecute(String str) {
            EditImageActivity.this.showLoading(false);
            if (str == null) {
                Toast.makeText(EditImageActivity.this.getApplicationContext(), "Oop! Something went wrong", 1).show();
                return;
            }
            Intent intent = new Intent(EditImageActivity.this, SaveAndShareActivity.class);
            intent.putExtra("path", str);
            EditImageActivity.this.startActivity(intent);
        }// wait im sending you file send  me your projrct i will check myself, thx should i dissconnect caan you send me xml file t
    }


    }
