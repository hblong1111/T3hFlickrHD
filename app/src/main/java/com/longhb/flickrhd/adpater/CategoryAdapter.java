package com.longhb.flickrhd.adpater;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    private boolean isChoose = false;

    public CategoryAdapter(Context context, List<Category> list, CategoryAdapterEvent callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isChoose()) {
            return 1;
        } else return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_item_category, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_item_category_choose, parent, false);
        }
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

        holder.itemView.setOnClickListener(view -> {
            if (!isChoose) {
                callback.onClickItem(position);
            } else {
                //TODO add event choose item
                callback.onClickItemChoose(position);
            }
        });

        //Animation for item
        setAnimationItemView(holder.itemView, isChoose);

        if (getItemViewType(position) == 1) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_longclick);
            animation.setInterpolator(new LinearInterpolator());
            holder.cardSelect.startAnimation(animation);
            Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.item_longclick_select);
            animation.setInterpolator(new LinearInterpolator());
            holder.cardSelect1.startAnimation(animation1);
        }
        holder.itemView.setOnLongClickListener(view -> {
            callback.itemLongClick(position);


            return true;
        });

    }

    private void setAnimationItemView(View itemView, boolean isChoose) {
        if (isChoose == false) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_zoom);
            animation.setInterpolator(new LinearInterpolator());
            itemView.startAnimation(animation);
        }
    }

    private String getTextHtml(Category category) {
        String[] strings = category.getTitle().trim().split(" ");
        String textHtml = "";
        for (int i = 0; i < strings.length; i++) {
            if (i <= (strings.length - 1) / 2) {
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

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvt;
        private TextView tvTitle;
        private CardView cardSelect;
        private CardView cardSelect1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = (ImageView) itemView.findViewById(R.id.img_avt);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            cardSelect = itemView.findViewById(R.id.cardView);
            cardSelect1 = itemView.findViewById(R.id.card_select);

        }

    }
}
