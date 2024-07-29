package com.gabbar925.photoeditios.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.sticker.StickerView;

import org.wysaid.view.ImageGLSurfaceView;

public class PhotoEditorView extends StickerView {
    private static final String TAG = "PhotoEditorView";
    private static final int brushSrcId = 2;
    private static final int glFilterId = 3;
    private static final int imgSrcId = 1;
    private Bitmap currentBitmap;
    private BrushDrawingView mBrushDrawingView;

    public ImageGLSurfaceView mGLSurfaceView;
    private FilterImageView mImgSource;

    public PhotoEditorView(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public PhotoEditorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public PhotoEditorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    @RequiresApi(api = 21)
    public PhotoEditorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet);
    }

    @SuppressLint({"Recycle"})
    private void init(@Nullable AttributeSet attributeSet) {
        this.mImgSource = new FilterImageView(getContext());
        this.mImgSource.setId(1);
        this.mImgSource.setAdjustViewBounds(true);
        this.mImgSource.setBackgroundColor(getResources().getColor(R.color.collage_bg));
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13, -1);
        this.mBrushDrawingView = new BrushDrawingView(getContext());
        this.mBrushDrawingView.setVisibility(View.GONE);
        this.mBrushDrawingView.setId(2);
        LayoutParams layoutParams2 = new LayoutParams(-1, -2);
        layoutParams2.addRule(13, -1);
        layoutParams2.addRule(6, 1);
        layoutParams2.addRule(8, 1);
        this.mGLSurfaceView = new ImageGLSurfaceView(getContext(), attributeSet);
        this.mGLSurfaceView.setId(3);
        this.mGLSurfaceView.setVisibility(View.VISIBLE);
        this.mGLSurfaceView.setAlpha(1.0f);
        this.mGLSurfaceView.setDisplayMode(ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT);
        LayoutParams layoutParams3 = new LayoutParams(-1, -2);
        layoutParams3.addRule(13, -1);
        layoutParams3.addRule(6, 1);
        layoutParams3.addRule(8, 1);
        addView(this.mImgSource, layoutParams);
        addView(this.mGLSurfaceView, layoutParams3);
        addView(this.mBrushDrawingView, layoutParams2);
        setDrawingCacheEnabled(true);
    }

    public ImageView getImageSource() {
        return this.mImgSource;
    }

    public void setImageSource(final Bitmap bitmap) {
        this.mImgSource.setImageBitmap(bitmap);
        if (this.mGLSurfaceView.getImageHandler() != null) {
            this.mGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.mGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() {
                public void surfaceCreated() {
                    PhotoEditorView.this.mGLSurfaceView.setImageBitmap(bitmap);
                }
            });
        }
        this.currentBitmap = bitmap;
    }

    public void setImageSource(Bitmap bitmap, ImageGLSurfaceView.OnSurfaceCreatedCallback onSurfaceCreatedCallback) {
        this.mImgSource.setImageBitmap(bitmap);
        if (this.mGLSurfaceView.getImageHandler() != null) {
            this.mGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.mGLSurfaceView.setSurfaceCreatedCallback(onSurfaceCreatedCallback);
        }
        this.currentBitmap = bitmap;
    }

    public Bitmap getCurrentBitmap() {
        return this.currentBitmap;
    }

    public void setCurrentBitmap(Bitmap bitmap) {
        this.currentBitmap = bitmap;
    }


    public BrushDrawingView getBrushDrawingView() {
        return this.mBrushDrawingView;
    }

    public ImageGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public void saveGLSurfaceViewAsBitmap(@NonNull final OnSaveBitmap onSaveBitmap) {
        if (this.mGLSurfaceView.getVisibility() == VISIBLE) {
            this.mGLSurfaceView.getResultBitmap(new ImageGLSurfaceView.QueryResultBitmapCallback() {
                public void get(Bitmap bitmap) {
                    onSaveBitmap.onBitmapReady(bitmap);
                }
            });
        }
    }

    public void setFilterEffect(String str) {
        this.mGLSurfaceView.setFilterWithConfig(str);
    }

    public void setFilterIntensity(float f) {
        this.mGLSurfaceView.setFilterIntensity(f);
    }
}
