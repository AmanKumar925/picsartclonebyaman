package com.gabbar925.photoeditios.editor.featuresfoto.insta;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.crop.adapter.AspectRatioPreviewAdapter;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.BrushColorListener;
import com.gabbar925.photoeditios.editor.featuresfoto.draw.ColorAdapter;
import com.gabbar925.photoeditios.editor.filterscolor.FilterUtils;
import com.gabbar925.photoeditios.util.SystemUtil;
import com.steelkiwi.cropiwa.AspectRatio;

public class InstaDialog extends DialogFragment implements AspectRatioPreviewAdapter.OnNewSelectedListener, InstaAdapter.BackgroundInstaListener, BrushColorListener {
    private static final String TAG = "InstaDialog";

    public TextView background;
    private Bitmap bitmap;
    private Bitmap blurBitmap;
    private ImageView blurView;

    public TextView border;
    private AspectRatio currentAspect;

    public RecyclerView fixedRatioList;

    public InstaSaveListener instaSaveListener;

    public LinearLayout instagramPadding;

    public ImageView instagramPhoto;
    private RelativeLayout loadingView;

    public TextView ratio;
    private ConstraintLayout ratioLayout;

    public RecyclerView rvBackground;

    public FrameLayout wrapInstagram;

    public interface InstaSaveListener {
        void instaSavedBitmap(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static InstaDialog show(@NonNull AppCompatActivity appCompatActivity, InstaSaveListener instaSaveListener2, Bitmap bitmap2, Bitmap bitmap3) {
        InstaDialog instaDialog = new InstaDialog();
        instaDialog.setBitmap(bitmap2);
        instaDialog.setBlurBitmap(bitmap3);
        instaDialog.setInstaSaveListener(instaSaveListener2);
        instaDialog.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return instaDialog;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void setInstaSaveListener(InstaSaveListener instaSaveListener2) {
        this.instaSaveListener = instaSaveListener2;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.insta_layout, viewGroup, false);
        AspectRatioPreviewAdapter aspectRatioPreviewAdapter = new AspectRatioPreviewAdapter(true);
        aspectRatioPreviewAdapter.setListener(this);
        this.loadingView = (RelativeLayout) inflate.findViewById(R.id.loadingView);
        this.loadingView.setVisibility(View.GONE);
        this.fixedRatioList = (RecyclerView) inflate.findViewById(R.id.fixed_ratio_list);
        this.fixedRatioList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.fixedRatioList.setAdapter(aspectRatioPreviewAdapter);
        this.currentAspect = new AspectRatio(1, 1);
        this.rvBackground = (RecyclerView) inflate.findViewById(R.id.rv_background);
        this.rvBackground.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.rvBackground.setAdapter(new InstaAdapter(getContext(), this));
        this.rvBackground.setVisibility(View.GONE);
        this.ratio = (TextView) inflate.findViewById(R.id.ratio);
        this.ratio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InstaDialog.this.fixedRatioList.setVisibility(View.VISIBLE);
                InstaDialog.this.rvBackground.setVisibility(View.GONE);
                InstaDialog.this.instagramPadding.setVisibility(View.GONE);
                InstaDialog.this.ratio.setTextColor(InstaDialog.this.getResources().getColor(R.color.colorAccent));
                InstaDialog.this.ratio.setBackground(InstaDialog.this.getResources().getDrawable(R.drawable.border_bottom));
                InstaDialog.this.background.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.border.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.background.setBackgroundResource(0);
                InstaDialog.this.border.setBackgroundResource(0);
            }
        });
        this.background = (TextView) inflate.findViewById(R.id.background);
        this.background.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InstaDialog.this.fixedRatioList.setVisibility(View.GONE);
                InstaDialog.this.rvBackground.setVisibility(View.VISIBLE);
                InstaDialog.this.instagramPadding.setVisibility(View.GONE);
                InstaDialog.this.background.setTextColor(InstaDialog.this.getResources().getColor(R.color.colorAccent));
                InstaDialog.this.background.setBackground(InstaDialog.this.getResources().getDrawable(R.drawable.border_bottom));
                InstaDialog.this.ratio.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.border.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.ratio.setBackgroundResource(0);
                InstaDialog.this.border.setBackgroundResource(0);
            }
        });
        this.instagramPadding = (LinearLayout) inflate.findViewById(R.id.instagramPadding);
        this.instagramPadding.setVisibility(View.GONE);
        this.border = (TextView) inflate.findViewById(R.id.border);
        this.border.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InstaDialog.this.instagramPadding.setVisibility(View.VISIBLE);
                InstaDialog.this.fixedRatioList.setVisibility(View.GONE);
                InstaDialog.this.rvBackground.setVisibility(View.GONE);
                InstaDialog.this.border.setTextColor(InstaDialog.this.getResources().getColor(R.color.colorAccent));
                InstaDialog.this.border.setBackground(InstaDialog.this.getResources().getDrawable(R.drawable.border_bottom));
                InstaDialog.this.background.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.ratio.setTextColor(InstaDialog.this.getResources().getColor(R.color.text_color_edit));
                InstaDialog.this.background.setBackgroundResource(0);
                InstaDialog.this.ratio.setBackgroundResource(0);
            }
        });
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_colors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ColorAdapter(getContext(), this, true));
        ((SeekBar) inflate.findViewById(R.id.paddingInsta)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int dpToPx = SystemUtil.dpToPx(InstaDialog.this.getContext(), i);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) InstaDialog.this.instagramPhoto.getLayoutParams();
                layoutParams.setMargins(dpToPx, dpToPx, dpToPx, dpToPx);
                InstaDialog.this.instagramPhoto.setLayoutParams(layoutParams);
            }
        });
        this.instagramPhoto = (ImageView) inflate.findViewById(R.id.instagramPhoto);
        this.instagramPhoto.setImageBitmap(this.bitmap);
        this.instagramPhoto.setAdjustViewBounds(true);
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.ratioLayout = (ConstraintLayout) inflate.findViewById(R.id.ratioLayout);
        this.blurView = (ImageView) inflate.findViewById(R.id.blurView);
        this.blurView.setImageBitmap(this.blurBitmap);
        this.wrapInstagram = (FrameLayout) inflate.findViewById(R.id.wrapInstagram);
        this.wrapInstagram.setLayoutParams(new ConstraintLayout.LayoutParams(point.x, point.x));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.ratioLayout);
        ConstraintSet constraintSet2 = constraintSet;
        constraintSet2.connect(this.wrapInstagram.getId(), 3, this.ratioLayout.getId(), 3, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 1, this.ratioLayout.getId(), 1, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 4, this.ratioLayout.getId(), 4, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 2, this.ratioLayout.getId(), 2, 0);
        constraintSet.applyTo(this.ratioLayout);
        inflate.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InstaDialog.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InstaDialog.this.wrapInstagram.setDrawingCacheEnabled(false);
                InstaDialog.this.wrapInstagram.setDrawingCacheEnabled(true);
                new SaveInstaView().execute(new Bitmap[]{InstaDialog.this.wrapInstagram.getDrawingCache(false)});
            }
        });
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ViewCompat.MEASURED_STATE_MASK));
        }
    }

    public void onStop() {
        super.onStop();
    }

    private int[] calculateWidthAndHeight(AspectRatio aspectRatio, Point point) {
        int height = this.ratioLayout.getHeight();
        if (aspectRatio.getHeight() > aspectRatio.getWidth()) {
            int ratio2 = (int) (aspectRatio.getRatio() * ((float) height));
            if (ratio2 < point.x) {
                return new int[]{ratio2, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio.getRatio())};
        }
        int ratio3 = (int) (((float) point.x) / aspectRatio.getRatio());
        if (ratio3 > height) {
            return new int[]{(int) (((float) height) * aspectRatio.getRatio()), height};
        }
        return new int[]{point.x, ratio3};
    }

    private int[] calculateWidthAndHeightReal(AspectRatio aspectRatio, Point point) {
        int height = this.bitmap.getHeight();
        if (aspectRatio.getHeight() > aspectRatio.getWidth()) {
            int ratio2 = (int) (aspectRatio.getRatio() * ((float) height));
            if (ratio2 < point.x) {
                return new int[]{ratio2, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio.getRatio())};
        }
        int ratio3 = (int) (((float) point.x) / aspectRatio.getRatio());
        if (ratio3 > height) {
            return new int[]{(int) (((float) height) * aspectRatio.getRatio()), height};
        }
        return new int[]{point.x, ratio3};
    }

    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.currentAspect = aspectRatio;
        int[] calculateWidthAndHeight = calculateWidthAndHeight(aspectRatio, point);
        this.wrapInstagram.setLayoutParams(new ConstraintLayout.LayoutParams(calculateWidthAndHeight[0], calculateWidthAndHeight[1]));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.ratioLayout);
        ConstraintSet constraintSet2 = constraintSet;
        constraintSet2.connect(this.wrapInstagram.getId(), 3, this.ratioLayout.getId(), 3, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 1, this.ratioLayout.getId(), 1, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 4, this.ratioLayout.getId(), 4, 0);
        constraintSet2.connect(this.wrapInstagram.getId(), 2, this.ratioLayout.getId(), 2, 0);
        constraintSet.applyTo(this.ratioLayout);
    }

    public void showLoading(boolean z) {
        if (z) {
            try {
                getActivity().getWindow().setFlags(16, 16);
                this.loadingView.setVisibility(View.VISIBLE);
            } catch (Exception unused) {
            }
        } else {
            getActivity().getWindow().clearFlags(16);
            this.loadingView.setVisibility(View.GONE);
        }
    }

    class SaveInstaView extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        SaveInstaView() {
        }


        public void onPreExecute() {
            InstaDialog.this.showLoading(true);
        }


        public Bitmap doInBackground(Bitmap... bitmapArr) {
            Bitmap cloneBitmap = FilterUtils.cloneBitmap(bitmapArr[0]);
            bitmapArr[0].recycle();
            bitmapArr[0] = null;
            return cloneBitmap;
        }


        public void onPostExecute(Bitmap bitmap) {
            InstaDialog.this.showLoading(false);
            InstaDialog.this.instaSaveListener.instaSavedBitmap(bitmap);
            InstaDialog.this.wrapInstagram.destroyDrawingCache();
            InstaDialog.this.dismiss();
        }
    }

    public void onBackgroundSelected(InstaAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.wrapInstagram.setBackgroundColor(squareView.drawableId);
        } else if (squareView.text.equals("Blur")) {
            this.blurView.setVisibility(View.VISIBLE);
        } else {
            this.wrapInstagram.setBackgroundResource(squareView.drawableId);
            this.blurView.setVisibility(View.GONE);
        }
        this.wrapInstagram.invalidate();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.blurBitmap.recycle();
        this.blurBitmap = null;
        this.bitmap = null;
    }

    public void onColorChanged(String str) {
        this.instagramPhoto.setBackgroundColor(Color.parseColor(str));
        if (!str.equals("#00000000")) {
            int dpToPx = SystemUtil.dpToPx(getContext(), 3);
            this.instagramPhoto.setPadding(dpToPx, dpToPx, dpToPx, dpToPx);
            return;
        }
        this.instagramPhoto.setPadding(0, 0, 0, 0);
    }
}
