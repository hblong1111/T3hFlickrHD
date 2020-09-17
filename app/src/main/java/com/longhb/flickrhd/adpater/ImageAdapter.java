package com.longhb.flickrhd.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.ImageAdapterEvent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    private List<Image> list;
    private ImageAdapterEvent callBack;

    public ImageAdapter(Context context, List<Image> list, ImageAdapterEvent callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvView.setText(list.get(position).getViews());
        holder.imageView.setMinimumHeight(list.get(position).getHeight());
        holder.imageView.setMinimumWidth(list.get(position).getWith());
        Picasso.get().load(list.get(position).getUrl())
                .resize(list.get(position).getWith(), list.get(position).getHeight())
                .placeholder(R.drawable.place)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onItemImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageView imgView;
        private TextView tvView;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imgView = (ImageView) itemView.findViewById(R.id.img_view);
            tvView = (TextView) itemView.findViewById(R.id.tv_view);
        }
    }
}
