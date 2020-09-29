package com.longhb.flickrhd.adpater;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Comment;
import com.longhb.flickrhd.model.Image;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        if (comment.getAuthorName()!=null&&!comment.getAuthorName().equals("")){
            holder.tvName.setText(comment.getAuthorName());
        }else {
            holder.tvName.setText("Long DZ");
        }
        holder.tvTime.setText(comment.convertTime());
        holder.tvContent.setText(Html.fromHtml(comment.getContent()));
        Glide
                .with(context)
                .load(comment.getUrlAvatar())
                .placeholder(R.color.colorAccent)
                .error(R.color.colorPrimary)
                .into(holder.imgAvt);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvt;
        private TextView tvName;
        private TextView tvContent;
        private TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvt = itemView.findViewById(R.id.img_avt);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
