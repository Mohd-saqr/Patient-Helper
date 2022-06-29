package com.patient.patienthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerAdapterPost;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;
import java.util.List;

public class MyPosts extends AppCompatActivity {
    
    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;
    RecyclerAdapterPost recyclerAdapterPost;
    RecyclerView recyclerView;
    LottieAnimationView loading;
    List<Post> posts = new ArrayList<>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        findViews();
        sharedPreferences=new MySharedPreferences(this);
        getMySharedPreferences();
        loading.setVisibility(View.VISIBLE);
        setAdapter();
        getPosts();
    }

    private void setAdapter() {
        recyclerAdapterPost = new RecyclerAdapterPost(posts, Post -> {

        }, Post -> {
            Gson gson = new Gson();
            String post =gson.toJson(Post);
            Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);

            intent.putExtra("Post",post);
            intent.putExtra("PostCreatedAt",Post.getCreatedAt().format());
            startActivity(intent);
        });
        recyclerView.setAdapter(recyclerAdapterPost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getMySharedPreferences() {
        Gson gson = new Gson();
        userLogIn=gson.fromJson(sharedPreferences.getString("userLog",null),UserLogIn.class);
        System.out.println(userLogIn.getId()+"id");
    }

    private void getPosts() {
        posts.clear();
        Amplify.API.query(ModelQuery.list(Post.class,Post.USER_ID.eq(userLogIn.getId())),res->{
            if (res.hasData()){
                for (Post p :res.getData()){
                    posts.add(p);
                }
                runOnUiThread(()->{
                    loading.setVisibility(View.INVISIBLE);
                    recyclerAdapterPost.notifyDataSetChanged();
                });
            }
        },err->{

        });

    }

    private void findViews() {
        recyclerView=findViewById(R.id.my_posts_recycler);
        loading=findViewById(R.id.loading_my_drugs);
    }
}