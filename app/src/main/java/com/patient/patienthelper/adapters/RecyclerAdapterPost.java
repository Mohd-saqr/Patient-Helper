package com.patient.patienthelper.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.bumptech.glide.Glide;
import com.patient.patienthelper.R;

import java.util.Date;
import java.util.List;

public class RecyclerAdapterPost extends RecyclerView.Adapter<RecyclerAdapterPost.MyViewHolder> {


    List<Post> data;
    itemClick itemClick;
    commentBtnClick commentBtnClick;
    Activity context;


    public RecyclerAdapterPost(List<Post> data, itemClick rowClick) {
        this.data = data;
        this.itemClick = rowClick;
    }

    public RecyclerAdapterPost(List<Post> data, itemClick rowClick, commentBtnClick commentBtnClick, Activity context) {
        this.data = data;
        this.itemClick = rowClick;
        this.commentBtnClick = commentBtnClick;
        this.context = context;
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
        Date date1 = data.get(position).getCreatedAt().toDate();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        holder.create_at.setText(df.format("hh:mm:ss a dd-MM-yyyy ", date1));

        holder.posts_body.setText(data.get(position).getBody());
        holder.username.setOnClickListener(v -> {
            this.itemClick.OnButtonClick(data.get(position));
        });
        holder.comment.setOnClickListener(view -> {
            commentBtnClick.OnButtonClick(data.get(position));
        });

//        Amplify.Storage.getUrl(
//                data.get(position).getUserId(),
//                result ->
//                {
//
//
//                           context.runOnUiThread(new Runnable() {
//                               @Override
//                               public void run() {
//                                   Glide
//                                           .with(context)
//                                           .load(result.getUrl())
//                                           .circleCrop()
//                                           .into(holder.imageView);
//                               }
//                           });
//
//
//                },
//                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
//        );

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
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            create_at = itemView.findViewById(R.id.text_view_post_body);
            posts_body = itemView.findViewById(R.id.text_view_post_body);
            comment = itemView.findViewById(R.id.btn_comment);
            imageView = itemView.findViewById(R.id.img_prof);

        }
    }

    public interface itemClick {
        void OnButtonClick(Post Post);
    }

    public interface commentBtnClick {
        void OnButtonClick(Post Post);
    }

    private void getUrl(String email) {

    }
}
