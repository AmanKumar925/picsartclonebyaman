package com.gabbar925.photoeditios.editor.featuresfoto.adjust;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabbar925.photoeditios.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {
    public String FILTER_CONFIG_TEMPLATE = "@adjust brightness {0} @adjust contrast {1} @adjust saturation {2} @vignette {3} 0.7 @adjust sharpen {4} 1 @adjust whitebalance {5} 1";

    public AdjustListener adjustListener;
    private Context context;

    public List<AdjustModel> lstAdjusts;

    public int selectedFilterIndex = 0;

    public AdjustAdapter(Context context2, AdjustListener adjustListener2) {
        this.context = context2;
        this.adjustListener = adjustListener2;
        init();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adjust_view, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.toolName.setText(this.lstAdjusts.get(i).name);
        viewHolder.icon.setImageDrawable(this.selectedFilterIndex != i ? this.lstAdjusts.get(i).icon : this.lstAdjusts.get(i).selectedIcon);
        if (this.selectedFilterIndex == i) {
            viewHolder.toolName.setTextColor(this.context.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.toolName.setTextColor(this.context.getResources().getColor(R.color.text_color_edit));
        }
    }

    public int getItemCount() {
        return this.lstAdjusts.size();
    }

    public String getFilterConfig() {
        String str = this.FILTER_CONFIG_TEMPLATE;
        return MessageFormat.format(str, new Object[]{this.lstAdjusts.get(0).originValue + "", this.lstAdjusts.get(1).originValue + "", this.lstAdjusts.get(2).originValue + "", this.lstAdjusts.get(3).originValue + "", this.lstAdjusts.get(4).originValue + "", Float.valueOf(this.lstAdjusts.get(5).originValue)});
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView toolName;

        ViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.toolName = (TextView) view.findViewById(R.id.tool_name);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int unused = AdjustAdapter.this.selectedFilterIndex = ViewHolder.this.getLayoutPosition();
                    AdjustAdapter.this.adjustListener.onAdjustSelected((AdjustModel) AdjustAdapter.this.lstAdjusts.get(AdjustAdapter.this.selectedFilterIndex));
                    AdjustAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public AdjustModel getCurrentAdjustModel() {
        return this.lstAdjusts.get(this.selectedFilterIndex);
    }

    private void init() {
        this.lstAdjusts = new ArrayList();
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.brightness), "brightness", this.context.getDrawable(R.drawable.brightness), this.context.getDrawable(R.drawable.brightness_selected), -1.0f, 0.0f, 1.0f));
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.contrast), "contrast", this.context.getDrawable(R.drawable.contrast), this.context.getDrawable(R.drawable.contrast_selected), 0.5f, 1.0f, 1.5f));
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.saturation), "saturation", this.context.getDrawable(R.drawable.saturation), this.context.getDrawable(R.drawable.saturation_selected), 0.0f, 1.0f, 2.0f));
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.vignette), "vignette", this.context.getDrawable(R.drawable.vignette), this.context.getDrawable(R.drawable.vignette_selected), 0.0f, 0.6f, 0.6f));
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.sharpen), "sharpen", this.context.getDrawable(R.drawable.sharpen), this.context.getDrawable(R.drawable.sharpen_selected), 0.0f, 0.0f, 10.0f));
        this.lstAdjusts.add(new AdjustModel(this.context.getString(R.string.temp), "whitebalance", this.context.getDrawable(R.drawable.temperature), this.context.getDrawable(R.drawable.temperature_selected), -1.0f, 0.0f, 1.0f));
    }

    public class AdjustModel {
        String code;
        Drawable icon;
        public float maxValue;
        public float minValue;
        String name;
        public float originValue;
        Drawable selectedIcon;

        AdjustModel(String str, String str2, Drawable drawable, Drawable drawable2, float f, float f2, float f3) {
            this.name = str;
            this.code = str2;
            this.icon = drawable;
            this.minValue = f;
            this.originValue = f2;
            this.maxValue = f3;
            this.selectedIcon = drawable2;
        }
    }
}
