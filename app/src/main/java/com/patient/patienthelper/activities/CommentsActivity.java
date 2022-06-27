package com.patient.patienthelper.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerAdapterComment;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    RecyclerAdapterComment recyclerAdapter;

    List<Comment> apiData= new ArrayList<>();
    Button postComment;
    EditText CommentBody;
    TextView postBody;
    TextView username;
    TextView text_view_no_comment;
    ProgressBar progressBar;
    UserLogIn userLogIn;
    Post post;
    String PostCreatedAt="";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getPost();
        findViewById();
        setAdapter();
        postComment();
//        setData();
    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.VISIBLE);
        fetchData();
        super.onResume();
    }

    private void findViewById( ) {
        postComment =findViewById(R.id.btn_post_comment);
        CommentBody =findViewById(R.id.edtext_comment_body);
        progressBar=findViewById(R.id.progress_bar_comment_ac);
        text_view_no_comment =findViewById(R.id.text_view_no_comment);
//        postBody =findViewById(R.id.text_view_post_body_com);
//        username=findViewById(R.id.username_post_com);
//        postCreateAt=findViewById(R.id.text_view_post_date_com);
        recyclerview=findViewById(R.id.comment_rc);
    }

    private void postComment(){

        postComment.setOnClickListener(v->{
            hideKeyboard(this);
            progressBar.setVisibility(View.VISIBLE);
            String commentBodyStr=CommentBody.getText().toString();
            Comment comment = Comment.builder()
                    .body(commentBodyStr)

                    .createBy(userLogIn.getFullName())
                    .commentPostId(post.getId())
                    .build();

            Amplify.API.mutate(ModelMutation.create(comment),res->{
                fetchData();
               runOnUiThread(() -> {
                   progressBar.setVisibility(View.INVISIBLE);
                   CommentBody.setText("");

               });
            },err->{
                Toast.makeText(this, err.toString(), Toast.LENGTH_SHORT).show();
            });
        });
    }
    private void fetchData() {

        apiData.clear();
        Amplify.API.query(ModelQuery.list(Comment.class,Comment.COMMENT_POST_ID.eq(post.getId())),res->{
            if (res.hasData()){
                for (Comment c: res.getData()){
                    apiData.add(c);
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerAdapter.notifyDataSetChanged();
                    if (apiData.size()==0){
                        text_view_no_comment.setVisibility(View.VISIBLE);
                    }else {
                        text_view_no_comment.setVisibility(View.INVISIBLE);

                    }
                }
            });
        },err->{

        });

    }


    private void getPost() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        if (mySharedPreferences.contains("userLog")){
            Gson gson = new Gson();
            userLogIn= gson.fromJson( mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);

        }

        Intent intent = getIntent();
        String postJson= intent.getStringExtra("Post");
        PostCreatedAt=intent.getStringExtra("PostCreatedAt");
        Gson gson = new Gson();
        post=gson.fromJson(postJson,Post.class);

    }

    private void  setAdapter(){
        recyclerAdapter = new RecyclerAdapterComment(apiData,comment->{
            Toast.makeText(this, comment, Toast.LENGTH_SHORT).show();
        });



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyclerAdapter);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}