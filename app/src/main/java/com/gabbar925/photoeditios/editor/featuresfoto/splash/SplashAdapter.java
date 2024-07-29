package com.gabbar925.photoeditios.editor.featuresfoto.splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.sticker.SplashSticker;
import com.gabbar925.photoeditios.util.AssetUtils;
import com.gabbar925.photoeditios.util.SystemUtil;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {
    private int borderWidth;
    private Context context;

    public int selectedSquareIndex;

    public SplashChangeListener splashChangeListener;

    public List<SplashItem> splashList = new ArrayList();

    interface SplashChangeListener {
        void onSelected(SplashSticker splashSticker);
    }

    SplashAdapter(Context context2, SplashChangeListener splashChangeListener2, boolean z) {
        this.context = context2;
        this.splashChangeListener = splashChangeListener2;
        this.borderWidth = SystemUtil.dpToPx(context2, 3);
        if (z) {
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask1.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame1.png")), R.drawable.splash01));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask2.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame2.png")), R.drawable.splash02));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask3.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame3.png")), R.drawable.splash03));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask4.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame4.png")), R.drawable.splash04));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask5.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame5.png")), R.drawable.splash05));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask6.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame6.png")), R.drawable.splash06));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask7.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame7.png")), R.drawable.splash07));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask8.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame8.png")), R.drawable.splash08));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask9.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame9.png")), R.drawable.splash09));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask11.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame11.png")), R.drawable.splash11));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask12.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame12.png")), R.drawable.splash12));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask14.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame14.png")), R.drawable.splash14));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask17.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame17.png")), R.drawable.splash17));
            this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask18.png"), AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame18.png")), R.drawable.splash18));
            return;
        }
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_1_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_1_shadow.png")), R.drawable.blur_1));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_2_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_2_shadow.png")), R.drawable.blur_2));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_3_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_3_shadow.png")), R.drawable.blur_3));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_4_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_4_shadow.png")), R.drawable.blur_4));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_5_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_5_shadow.png")), R.drawable.blur_5));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_7_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_7_shadow.png")), R.drawable.blur_7));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_8_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_8_shadow.png")), R.drawable.blur_8));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_9_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_9_shadow.png")), R.drawable.blur_9));
        this.splashList.add(new SplashItem(new SplashSticker(AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_10_mask.png"), AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_10_shadow.png")), R.drawable.blur_10));
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.splash_view, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.splash.setImageResource(this.splashList.get(i).drawableId);
        if (this.selectedSquareIndex == i) {
            viewHolder.splash.setBorderColor(this.context.getResources().getColor(R.color.colorAccent));
            viewHolder.splash.setBorderWidth(this.borderWidth);
            return;
        }
        viewHolder.splash.setBorderColor(0);
        viewHolder.splash.setBorderWidth(this.borderWidth);
    }

    public int getItemCount() {
        return this.splashList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoundedImageView splash;

        public ViewHolder(View view) {
            super(view);
            this.splash = (RoundedImageView) view.findViewById(R.id.splash);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            int unused = SplashAdapter.this.selectedSquareIndex = getAdapterPosition();
            if (SplashAdapter.this.selectedSquareIndex < 0) {
                int unused2 = SplashAdapter.this.selectedSquareIndex = 0;
            }
            if (SplashAdapter.this.selectedSquareIndex >= SplashAdapter.this.splashList.size()) {
                int unused3 = SplashAdapter.this.selectedSquareIndex = SplashAdapter.this.splashList.size() - 1;
            }
            SplashAdapter.this.splashChangeListener.onSelected(((SplashItem) SplashAdapter.this.splashList.get(SplashAdapter.this.selectedSquareIndex)).splashSticker);
            SplashAdapter.this.notifyDataSetChanged();
        }
    }

    class SplashItem {
        int drawableId;
        SplashSticker splashSticker;

        SplashItem(SplashSticker splashSticker2, int i) {
            this.splashSticker = splashSticker2;
            this.drawableId = i;
        }
    }
}
