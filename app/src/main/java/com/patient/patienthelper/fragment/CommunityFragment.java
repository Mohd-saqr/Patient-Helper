package com.patient.patienthelper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.QuerySortBy;
import com.amplifyframework.core.model.query.QuerySortOrder;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Post;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activitys.CommentsActivity;
import com.patient.patienthelper.adapters.RecyclerAdapterPost;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    RecyclerView recyclerview;
    RecyclerAdapterPost recyclerAdapter;

    List<Post> apiData= new ArrayList<>();
    Button post;
    EditText postBody;
    ProgressBar progressBar;
    UserLogIn userLogIn;

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

        View view =inflater.inflate(R.layout.activity_community, container, false);
        recyclerview = view.findViewById(R.id.rc_posts);
        String date1 = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

        setAdapter();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getUserLogIn();
        fetchData();

        findViewById(view);
        postHandel();


        // Inflate the layout for this fragment
        return view;
    }

    private void postHandel() {

        post.setOnClickListener(v->{
            String body =postBody.getText().toString();
            Post post =Post.builder().body(body)
                    .createBy(userLogIn.getFullName())
                    .userId(userLogIn.getId())
                    .build();
            progressBar.setVisibility(View.VISIBLE);
            Amplify.API.mutate(ModelMutation.create(post),res->{
                progressBar.setVisibility(View.INVISIBLE);


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        postBody.setText("");
                        onResume();
                    }
                });


            },err->{

            });
            System.out.println(body+"dasdass");
        });



    }

    private void findViewById(View view) {
        post =view.findViewById(R.id.btn_post);
        postBody =view.findViewById(R.id.post_body);
        progressBar=view.findViewById(R.id.progress_com);
    }

    private void fetchData() {

    }

    private void getUserLogIn() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        if (mySharedPreferences.contains("userLog")){
            Gson gson = new Gson();
            userLogIn= gson.fromJson( mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);

        }
    }


    private void  setAdapter(){
        recyclerAdapter = new RecyclerAdapterPost(apiData, post -> {
            Toast.makeText(getContext(), post.getCreateBy(), Toast.LENGTH_SHORT).show();
        }, Post -> {
            Intent intent = new Intent(getContext(), CommentsActivity.class);
            Gson gson = new Gson();
            String post =gson.toJson(Post);

            intent.putExtra("Post",post);
            intent.putExtra("PostCreatedAt",Post.getCreatedAt().format());
            startActivity(intent);
        });
        System.out.println(apiData);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyclerAdapter);
    }

    @Override
    public void onResume() {
        progressBar.setVisibility(View.VISIBLE);
        apiData.clear();
        Amplify.API.query(ModelQuery.list(Post.class), res->{
            for (Post post:res.getData()){
                apiData.add(post);



            }



            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerAdapter.notifyDataSetChanged();
                }
            });

        },err->{

        });



        super.onResume();
    }


}