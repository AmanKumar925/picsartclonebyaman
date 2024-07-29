package com.gabbar925.photoeditios.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;

import java.util.ArrayList;
import java.util.List;

public class AddTextAdapter extends RecyclerView.Adapter<AddTextAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<TextFunction> textFunctions;

    AddTextAdapter(@NonNull Context context, @NonNull List<TextFunction> list) {
        this.inflater = LayoutInflater.from(context);
        this.textFunctions = list;
    }

    public AddTextAdapter(@NonNull Context context) {
        this(context, getListTextFunctions(context));
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.bottom_toolbar_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.item.setBackground(this.textFunctions.get(i).drawable);
    }

    public int getItemCount() {
        return this.textFunctions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View item;

        public ViewHolder(View view) {
            super(view);
            this.item = view.findViewById(R.id.bottomItemIcon);
        }
    }

    public static List<TextFunction> getListTextFunctions(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TextFunction(context.getResources().getDrawable(R.drawable.img_keyboard)));
        arrayList.add(new TextFunction(context.getResources().getDrawable(R.drawable.img_text)));
        arrayList.add(new TextFunction(context.getResources().getDrawable(R.drawable.img_color)));
        arrayList.add(new TextFunction(context.getResources().getDrawable(R.drawable.img_edit)));
        arrayList.add(new TextFunction(context.getResources().getDrawable(R.drawable.img_confirm)));
        return arrayList;
    }

    static class TextFunction {
        Drawable drawable;

        public TextFunction(Drawable drawable2) {
            this.drawable = drawable2;
        }
    }
}
