package com.longhb.flickrhd.adpater;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.BoringLayout;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.util.CategoryAdapterEvent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> list;
    private CategoryAdapterEvent callback;

    public CategoryAdapter(Context context, List<Category> list, CategoryAdapterEvent callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = list.get(position);
        getTextHtml(category);
        holder.tvTitle.setText(Html.fromHtml(getTextHtml(category)));
        if (category.getH() < 500) {
            category.setH(500);
        }
        holder.imgAvt.setMinimumHeight(category.getH());
        Picasso.get().load(category.getUrl())
                .resize(category.getW(), category.getH())
                .placeholder(R.drawable.place_category)
                .into(holder.imgAvt);

        setAnimationItemView(holder.itemView);
    }

    private void setAnimationItemView(View itemView) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_zoom);
        animation.setInterpolator(new LinearInterpolator());
        itemView.startAnimation(animation);
    }

    private String getTextHtml(Category category) {
        String[] strings = category.getTitle().trim().split(" ");
        String textHtml = "";
        for (int i = 0; i < strings.length; i++) {
            if (i <= (strings.length-1) / 2) {
                textHtml += "<span style=\"color: #FFD500\">" + strings[i] + " </span>";
            } else {
                textHtml += "<span style=\"color: #FFFFFF\">" + strings[i] + "</span>";
            }
        }
        return textHtml;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvt;
        private TextView tvTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = (ImageView) itemView.findViewById(R.id.img_avt);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

        }

    }
}
