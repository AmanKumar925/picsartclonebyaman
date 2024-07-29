package com.gabbar925.photoeditios.editor.featuresfoto.sticker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.util.AssetUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    public Context context;

    public int screenWidth;

    public OnClickStickerListener stickerListener;

    public List<String> stickers;

    public interface OnClickStickerListener {
        void addSticker(Bitmap bitmap);
    }

    public StickerAdapter(Context context2, List<String> list, int i, OnClickStickerListener onClickStickerListener) {
        this.context = context2;
        this.stickers = list;
        this.screenWidth = i;
        this.stickerListener = onClickStickerListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.sticker_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            Bitmap bitmap = AssetUtils.loadBitmapFromAssets(this.context, this.stickers.get(i));
            Glide.with(this.context).load(bitmap).into(viewHolder.sticker);

        } catch (Exception unused) {
        }
    }

    public int getItemCount() {
        return this.stickers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView sticker;
        RelativeLayout stickerWrapper;

        public ViewHolder(View view) {
            super(view);
            this.sticker = (ImageView) view.findViewById(R.id.txt_vp_item_list);
            this.stickerWrapper = (RelativeLayout) view.findViewById(R.id.sticker_wrapper);
//            ViewGroup.LayoutParams layoutParams = this.stickerWrapper.getLayoutParams();
//            layoutParams.width = StickerAdapter.this.screenWidth / 4;
//            layoutParams.height = StickerAdapter.this.screenWidth / 4;
//            this.stickerWrapper.setLayoutParams(layoutParams);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            StickerAdapter.this.stickerListener.addSticker(AssetUtils.loadBitmapFromAssets(StickerAdapter.this.context, (String) StickerAdapter.this.stickers.get(getAdapterPosition())));
        }
    }
}
