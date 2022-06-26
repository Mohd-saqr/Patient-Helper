package com.patient.patienthelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Comment;
import com.patient.patienthelper.R;

import java.util.List;

public class RecyclerAdapterComment extends RecyclerView.Adapter<RecyclerAdapterComment.MyViewHolder> {


    List<Comment> data;
    itemClick itemClick;


    public RecyclerAdapterComment(List<Comment> data, itemClick rowClick) {
        this.data = data;
        this.itemClick = rowClick;
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
        holder.create_at.setText(data.get(position).getCreatedAt().format());
        holder.posts_body.setText(data.get(position).getBody());
        holder.username.setOnClickListener(v -> {
            this.itemClick.OnButtonClick(data.get(position).getPost().getId());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_com);
            create_at = itemView.findViewById(R.id.text_view_com_date);
            posts_body = itemView.findViewById(R.id.text_view_com_body);

        }
    }

    public interface itemClick {
        void OnButtonClick(String Post);
    }
}
