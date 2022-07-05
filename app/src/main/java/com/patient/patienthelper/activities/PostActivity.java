package com.patient.patienthelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.patient.patienthelper.R;

public class PostActivity extends AppCompatActivity {

    private final static String TAG = PostActivity.class.getSimpleName();
    private ImageView userImage;
    private TextView username,date;
    private MaterialTextView postBody;
    private MaterialButton commentBtn;
    private static String postId;
    private static Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        findAllViewById();
        getPost(postId);
        setAllTextView();
        setOnClickListener();
    }

    private void findAllViewById(){
        userImage = findViewById(R.id.user_image_post);
        username = findViewById(R.id.username_post);
        date = findViewById(R.id.text_view_post_date_post);
        postBody = findViewById(R.id.text_view_post_body_post);
        commentBtn = findViewById(R.id.btn_comment_post_post);
    }

    private void getPost(String id) {
        Amplify.API.query(
                ModelQuery.get(Post.class, id),
                response -> {
                    Log.i(TAG, ((Post) response.getData()).getBody());
                    post = response.getData();
                },
                error -> Log.e(TAG, error.toString(), error)
        );
    }

    private void setAllTextView(){
        username.setText(post.getCreateBy());
        date.setText(post.getCreatedAt().toString());
        postBody.setText(post.getBody());
    }

    private void setOnClickListener(){
        commentBtn.setOnClickListener(view -> {
            navigateToCommentActivity();
        });
    }

    private void navigateToCommentActivity(){
        Intent intent = new Intent(this, CommentsActivity.class);
        Gson gson = new Gson();
        String postString =gson.toJson(post);
        intent.putExtra("Post",postString);
        intent.putExtra("PostCreatedAt",post.getCreatedAt().format());
        startActivity(intent);
    }
}