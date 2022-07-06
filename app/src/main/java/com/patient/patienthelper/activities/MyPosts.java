package com.patient.patienthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerAdapterPost;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class MyPosts extends AppCompatActivity {

    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;
    RecyclerAdapterPost recyclerAdapterPost;
    RecyclerView recyclerView;
    LottieAnimationView loading;
    List<Post> posts = new ArrayList<>();
    SlidingUpPanelLayout slidingUpPanelLayout ;
    Button save;
    Button btn_back_my_post;
    EditText commentBody;
    Handler mHandler = new Handler();
    boolean flage =false;
    String postId="";
    PowerMenu powerMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        findViews();
        sharedPreferences = new MySharedPreferences(this);
        getMySharedPreferences();
        loading.setVisibility(View.VISIBLE);
        setAdapter();
        getPosts();
        btn_back_my_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeypord();
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void setAdapter() {
        recyclerAdapterPost = new RecyclerAdapterPost(posts, Post -> {

        }, Post -> {
            Gson gson = new Gson();
            String post = gson.toJson(Post);
            Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);

            intent.putExtra("Post", post);
            intent.putExtra("PostCreatedAt", Post.getCreatedAt().format());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();

        }, this, new RecyclerAdapterPost.InflateMenu() {
            @Override
            public void inflate(Post post, boolean isUser, View view) {
editDeletePost(post,isUser,view);
            }
        });
                recyclerView.setAdapter(recyclerAdapterPost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void editDeletePost(Post post, boolean isUser, View view) {




            powerMenu = new PowerMenu.Builder(this)
                    .addItem(new PowerMenuItem("Delete", false)) // add an item.
                    .addItem(new PowerMenuItem("Edit", false)) // aad an item list.
                    .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                    .setMenuRadius(10f) // sets the corner radius.
                    .setMenuShadow(10f) // sets the shadow.
                    .setTextColor(ContextCompat.getColor(this, R.color.black))
                    .setTextGravity(Gravity.CENTER)

                    .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                    .setSelectedTextColor(Color.RED)
                    .setMenuColor(Color.parseColor("#E3E5E8"))
                    .setSelectedMenuColor(ContextCompat.getColor(this, R.color.red))
                    .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                        @Override
                        public void onItemClick(int position, PowerMenuItem item) {
                            if (position==0){
                                Amplify.API.mutate(ModelMutation.delete(post), res -> {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (powerMenu.isShowing()) {
                                                powerMenu.dismiss();
                                            }
                                            getPosts();
                                        }
                                    });

                                }, err -> {

                                });
                            }else {


                                postId = post.getId();
                                powerMenu.dismiss();
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                                commentBody.setText(post.getBody());
                                slidingUpPanelLayout.setTouchEnabled(false);
                            }

                        }
                    }).build();
            powerMenu.showAsDropDown(view, -370, 0);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(commentBody.getText())) {
                        commentBody.setError("Your post shouldn't be empty");
                    } else {

                    hideKeypord();

                    String body = commentBody.getText().toString();
                    Post post = Post.builder().body(body)
                            .createBy(userLogIn.getFullName())
                            .userId(userLogIn.getId())
                            .userEmail(userLogIn.getImageId())
                            .id(postId)
                            .build();
                    loading.setVisibility(View.VISIBLE);
                    Amplify.API.mutate(ModelMutation.update(post), res -> {


                        mHandler.post(() -> {
                            commentBody.setText("");
                            loading.setVisibility(View.INVISIBLE);
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


                            getPosts();
                        });


                    }, err -> {

                    });
                }
            }
            });


        }



    private void getMySharedPreferences() {
        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
        System.out.println(userLogIn.getId() + "id");
    }

    private void getPosts() {
        posts.clear();
        Amplify.API.query(ModelQuery.list(Post.class, Post.USER_ID.eq(userLogIn.getId())), res -> {
            if (res.hasData()) {
                for (Post p : res.getData()) {
                    posts.add(p);
                }
                runOnUiThread(() -> {
                    loading.setVisibility(View.INVISIBLE);
                    recyclerAdapterPost.notifyDataSetChanged();
                });
            }
        }, err -> {

        });

    }

    private void findViews() {
        recyclerView = findViewById(R.id.my_posts_recycler);
        loading = findViewById(R.id.loading_my_drugs);
        commentBody=findViewById(R.id.mY_posts_commentpody);
        save=findViewById(R.id.Save);
        slidingUpPanelLayout=findViewById(R.id.my_post_slide);
        btn_back_my_post =findViewById(R.id.btn_back_my_post);
    }
    private void hideKeypord() {

        View view2 = this.getCurrentFocus();
        if (view2 != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }


    }
}