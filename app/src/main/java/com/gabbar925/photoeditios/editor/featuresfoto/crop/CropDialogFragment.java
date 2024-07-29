package com.gabbar925.photoeditios.editor.featuresfoto.crop;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.crop.adapter.AspectRatioPreviewAdapter;
import com.isseiaoki.simplecropview.CropImageView;
import com.steelkiwi.cropiwa.AspectRatio;

public class CropDialogFragment extends DialogFragment implements AspectRatioPreviewAdapter.OnNewSelectedListener {
    private static final String TAG = "CropDialogFragment";
    private Bitmap bitmap;
    private RelativeLayout loadingView;

    public CropImageView mCropView;

    public OnCropPhoto onCropPhoto;

    public interface OnCropPhoto {
        void finishCrop(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static CropDialogFragment show(@NonNull AppCompatActivity appCompatActivity, OnCropPhoto onCropPhoto2, Bitmap bitmap2) {
        CropDialogFragment cropDialogFragment = new CropDialogFragment();
        cropDialogFragment.setBitmap(bitmap2);
        cropDialogFragment.setOnCropPhoto(onCropPhoto2);
        cropDialogFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return cropDialogFragment;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

    }

    public void setOnCropPhoto(OnCropPhoto onCropPhoto2) {
        this.onCropPhoto = onCropPhoto2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ViewCompat.MEASURED_STATE_MASK));
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.crop_layout, viewGroup, false);
        AspectRatioPreviewAdapter aspectRatioPreviewAdapter = new AspectRatioPreviewAdapter();
        aspectRatioPreviewAdapter.setListener(this);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.fixed_ratio_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(aspectRatioPreviewAdapter);
        this.mCropView = (CropImageView) inflate.findViewById(R.id.crop_view);
        this.mCropView.setCropMode(CropImageView.CropMode.FREE);
        inflate.findViewById(R.id.rotate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CropDialogFragment.this.mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
        inflate.findViewById(R.id.h_flip).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                CropDialogFragment.this.mCropView.flipHorizontal();
            }
        });
        inflate.findViewById(R.id.v_flip).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                CropDialogFragment.this.mCropView.flipVertical();
            }
        });
        inflate.findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new OnSaveCrop().execute(new Void[0]);
            }
        });
        this.loadingView = (RelativeLayout) inflate.findViewById(R.id.loadingView);
        this.loadingView.setVisibility(View.GONE);
        inflate.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CropDialogFragment.this.dismiss();
            }
        });
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mCropView = (CropImageView) view.findViewById(R.id.crop_view);
        this.mCropView.setImageBitmap(this.bitmap);
    }

    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        if (aspectRatio.getWidth() == 10 && aspectRatio.getHeight() == 10) {
            this.mCropView.setCropMode(CropImageView.CropMode.FREE);
        } else {
            this.mCropView.setCustomRatio(aspectRatio.getWidth(), aspectRatio.getHeight());
        }
    }

    class OnSaveCrop extends AsyncTask<Void, Bitmap, Bitmap> {
        OnSaveCrop() {
        }


        public void onPreExecute() {
            CropDialogFragment.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            return CropDialogFragment.this.mCropView.getCroppedBitmap();
        }


        public void onPostExecute(Bitmap bitmap) {
            CropDialogFragment.this.showLoading(false);
            CropDialogFragment.this.onCropPhoto.finishCrop(bitmap);
            CropDialogFragment.this.dismiss();
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.loadingView.setVisibility(View.VISIBLE);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.loadingView.setVisibility(View.GONE);
    }
}
