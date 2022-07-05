package com.patient.patienthelper.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.CommentsActivity;
import com.patient.patienthelper.adapters.RecyclerAdapterPost;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    RecyclerView recyclerview;
    RecyclerAdapterPost recyclerAdapter;

    List<Post> apiData = new ArrayList<>();
    Button buttonPost;
    Button writeSome;
    EditText postBody;
    UserLogIn userLogIn;
    TextView Username_posts;
    PowerMenu powerMenu;
    View.OnClickListener editPostListener;
    View.OnClickListener postListener;
    Button btn_back;


    LottieAnimationView loading;
    SlidingUpPanelLayout slidingPaneLayout;
    boolean flage = false;
    String postId="";
    TextView text_view_no_posts;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_community, container, false);
        recyclerview = view.findViewById(R.id.rc_posts);
        String date1 = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

        setAdapter();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getUserLogIn();
        fetchData();

        findViewById(view);
        postHandel();
        btn_back.setOnClickListener(v->{
            hideKeypord();
            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        });

        writeSome.setShowSoftInputOnFocus(false);
        writeSome.setOnClickListener(view1 -> {

            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            slidingPaneLayout.setTouchEnabled(false);
            Username_posts.setText(userLogIn.getFullName());
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void postHandel() {


        buttonPost.setOnClickListener(v -> {
            if (TextUtils.isEmpty(postBody.getText())) {
postBody.setError("Your post shouldn't be empty");
            } else {


                if (flage) {
                    hideKeypord();

                    String body = postBody.getText().toString();
                    Post post = Post.builder().body(body)
                            .createBy(userLogIn.getFullName())
                            .userId(userLogIn.getId())
                            .id(postId)
                            .build();
                    loading.setVisibility(View.VISIBLE);
                    Amplify.API.mutate(ModelMutation.update(post), res -> {


                        mHandler.post(() -> {
                            postBody.setText("");
                            loading.setVisibility(View.INVISIBLE);
                            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            buttonPost.setText("Post");
                            flage=false;

                            onStart();
                        });


                    }, err -> {

                    });
                } else {
                    hideKeypord();
                    String body = postBody.getText().toString();
                    Post post = Post.builder().body(body)
                            .createBy(userLogIn.getFullName())
                            .userId(userLogIn.getId())
                            .build();
                    loading.setVisibility(View.VISIBLE);
                    Amplify.API.mutate(ModelMutation.create(post), res -> {


                        mHandler.post(() -> {
                            postBody.setText("");
                            loading.setVisibility(View.INVISIBLE);
                            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            onStart();
                        });


                    }, err -> {

                    });
                }


            }


        });


    }

    private void findViewById(View view) {
        buttonPost = view.findViewById(R.id.post_btn);
        writeSome = view.findViewById(R.id.btn_write);
        slidingPaneLayout = view.findViewById(R.id.sliding_layout_post);
        postBody = view.findViewById(R.id.post_body);
        Username_posts = view.findViewById(R.id.username_post);
        loading = view.findViewById(R.id.loading_com);
        text_view_no_posts=view.findViewById(R.id.text_view_no_posts);
        btn_back=view.findViewById(R.id.btn_back);
    }

    private void fetchData() {

    }

    private void getUserLogIn() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        if (mySharedPreferences.contains("userLog")) {
            Gson gson = new Gson();
            userLogIn = gson.fromJson(mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);

        }
    }


    private void setAdapter() {
        recyclerAdapter = new RecyclerAdapterPost(apiData, post -> {
            Toast.makeText(getContext(), post.getCreateBy(), Toast.LENGTH_SHORT).show();
        }, Post -> {
            Intent intent = new Intent(getContext(), CommentsActivity.class);
            Gson gson = new Gson();
            String post = gson.toJson(Post);

            intent.putExtra("Post", post);
            intent.putExtra("PostCreatedAt", Post.getCreatedAt().format());
            startActivity(intent);
        }, getActivity(), new RecyclerAdapterPost.InflateMenu() {
            @Override
            public void inflate(Post post, boolean isUser, View view) {

                if (isUser) {

                    powerMenu = new PowerMenu.Builder(getContext())
                            .addItem(new PowerMenuItem("Delete", false)) // add an item.
                            .addItem(new PowerMenuItem("Edit", false)) // aad an item list.
                            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                            .setMenuRadius(10f) // sets the corner radius.
                            .setMenuShadow(10f) // sets the shadow.
                            .setTextColor(ContextCompat.getColor(getContext(), R.color.black))
                            .setTextGravity(Gravity.CENTER)

                            .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                            .setSelectedTextColor(Color.RED)
                            .setMenuColor(Color.parseColor("#E3E5E8"))
                            .setSelectedMenuColor(ContextCompat.getColor(getContext(), R.color.red))
                            .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {

                                @Override
                                public void onItemClick(int position, PowerMenuItem item) {
                                    if (position == 0) {
                                        Amplify.API.mutate(ModelMutation.delete(post), res -> {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (powerMenu.isShowing()) {
                                                        powerMenu.dismiss();
                                                    }
                                                    onStart();
                                                }
                                            });

                                        }, err -> {

                                        });
                                    } else {
                                        flage=true;
                                        buttonPost.setText("Save");
                                        postId=post.getId();
                                        powerMenu.dismiss();
                                        slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                                        postBody.setText(post.getBody());
                                        slidingPaneLayout.setTouchEnabled(false);



                                    }
                                }
                            })
                            .build();
                    powerMenu.showAsDropDown(view, -370, 0);
                }
            }
        });
        System.out.println(apiData);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyclerAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        loading.setVisibility(View.VISIBLE);
        apiData.clear();
        Amplify.API.query(ModelQuery.list(Post.class), res -> {
            for (Post post : res.getData()) {
                apiData.add(post);


            }


            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.INVISIBLE);
                    recyclerAdapter.notifyDataSetChanged();
                    if (apiData.size()==0){
                        text_view_no_posts.setVisibility(View.VISIBLE);
                    }else {
                        text_view_no_posts.setVisibility(View.INVISIBLE);

                    }
                }
            });

        }, err -> {

        });
    }

    private void hideKeypord( ) {

        View view2 = getActivity().getCurrentFocus();
if (view2!=null){
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
}


    }
}