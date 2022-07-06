package com.patient.patienthelper.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.bumptech.glide.Glide;
import com.patient.patienthelper.R;

import java.util.Date;
import java.util.List;

public class RecyclerAdapterComment extends RecyclerView.Adapter<RecyclerAdapterComment.MyViewHolder> {


    List<Comment> data;
    itemClick itemClick;
    Activity context;


    public RecyclerAdapterComment(List<Comment> data, itemClick rowClick,Activity context) {
        this.data = data;
        this.itemClick = rowClick;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_row, parent, false);
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
            this.itemClick.OnButtonClick(data.get(position).getPost().getId());
        });

        Amplify.Storage.getUrl(
                data.get(position).getUserImageId(),
                result ->
                {


                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide
                                    .with(context)
                                    .load(result.getUrl())
                                    .circleCrop()
                                    .into(holder.userImage);
                        }
                    });


                },
                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView create_at;
        TextView username;
        TextView posts_body;
        ImageView userImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_com);
            create_at = itemView.findViewById(R.id.text_view_com_date);
            posts_body = itemView.findViewById(R.id.text_view_com_body);
userImage=itemView.findViewById(R.id.user_image_com);
        }
    }

    public interface itemClick {
        void OnButtonClick(String Post);
    }
}
