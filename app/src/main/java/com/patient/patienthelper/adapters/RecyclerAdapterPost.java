package com.patient.patienthelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Post;
import com.patient.patienthelper.R;

import java.util.List;

public class RecyclerAdapterPost extends RecyclerView.Adapter<RecyclerAdapterPost.MyViewHolder> {


    List<Post> data;
    itemClick itemClick;
    commentBtnClick commentBtnClick;



    public RecyclerAdapterPost(List<Post> data, itemClick rowClick) {
        this.data = data;
        this.itemClick = rowClick;
    }
    public RecyclerAdapterPost(List<Post> data, itemClick rowClick, commentBtnClick commentBtnClick) {
        this.data = data;
        this.itemClick = rowClick;
        this.commentBtnClick=commentBtnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.posts_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(data.get(position).getCreateBy());
        holder.create_at.setText(data.get(position).getCreatedAt().format());
        holder.posts_body.setText(data.get(position).getBody());
        holder.username.setOnClickListener(v -> {
            this.itemClick.OnButtonClick(data.get(position));
        });
        holder.comment.setOnClickListener(view -> {
            commentBtnClick.OnButtonClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView create_at;
        TextView username;
        TextView posts_body;
        Button comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            create_at = itemView.findViewById(R.id.text_view_post_date);
            posts_body = itemView.findViewById(R.id.text_view_post_body);
            comment=itemView.findViewById(R.id.btn_comment);

        }
    }

    public interface itemClick {
        void OnButtonClick(Post Post);
    }

    public interface commentBtnClick {
        void OnButtonClick(Post Post);
    }
}
