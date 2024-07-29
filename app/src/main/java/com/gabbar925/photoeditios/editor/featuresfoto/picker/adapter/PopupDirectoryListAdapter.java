package com.gabbar925.photoeditios.editor.featuresfoto.picker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabbar925.photoeditios.R;
import com.gabbar925.photoeditios.editor.featuresfoto.picker.entity.PhotoDirectory;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class PopupDirectoryListAdapter extends BaseAdapter {
    private List<PhotoDirectory> directories = new ArrayList();

    public RequestManager glide;

    public PopupDirectoryListAdapter(RequestManager requestManager, List<PhotoDirectory> list) {
        this.directories = list;
        this.glide = requestManager;
    }

    public int getCount() {
        return this.directories.size();
    }

    public PhotoDirectory getItem(int i) {
        return this.directories.get(i);
    }

    public long getItemId(int i) {
        return (long) this.directories.get(i).hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.__picker_item_directory, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bindData(this.directories.get(i));
        return view;
    }

    private class ViewHolder {
        public ImageView ivCover;
        public TextView tvCount;
        public TextView tvName;

        public ViewHolder(View view) {
            this.ivCover = (ImageView) view.findViewById(R.id.iv_dir_cover);
            this.tvName = (TextView) view.findViewById(R.id.tv_dir_name);
            this.tvCount = (TextView) view.findViewById(R.id.tv_dir_count);
        }

        public void bindData(PhotoDirectory photoDirectory) {
            RequestOptions requestOptions = new RequestOptions();
            ((RequestOptions) ((RequestOptions) requestOptions.dontAnimate()).dontTransform()).override(800, 800);
            PopupDirectoryListAdapter.this.glide.setDefaultRequestOptions(requestOptions).load(photoDirectory.getCoverPath()).thumbnail(0.1f).into(this.ivCover);
            this.tvName.setText(photoDirectory.getName());
            TextView textView = this.tvCount;
            textView.setText(photoDirectory.getPhotos().size() + "");
        }
    }
}
