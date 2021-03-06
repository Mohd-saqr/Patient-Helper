package com.patient.patienthelper.fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.ChangePasswordActivity;
import com.patient.patienthelper.activities.DeleteAccountActivity;
import com.patient.patienthelper.activities.EditProfileActivity;
import com.patient.patienthelper.activities.LoginActivity;
import com.patient.patienthelper.activities.MainActivity;
import com.patient.patienthelper.activities.MyDrugs;
import com.patient.patienthelper.activities.MyPosts;
import com.patient.patienthelper.activities.TestActivity;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.io.File;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint({"SdCardPath", "SetTextI18n"})
@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfileFragment extends Fragment {
    /*
    activitys
     */
    private ListView listView;
    private ImageView profileImage;
    private TextView userFullName;
    private static String imageToDownloadKey;
    private LottieAnimationView loading;
    private final String[] itemList = {" My Posts", " My Drugs", " Edit Profile", " Change password", " Sign out", " Delete account"};
    private final String[] subItemsList = {
            "        See all your posts",
            "        Manage all your drugs activities",
            "        Change name, age, male and email",
            "        Change current password",
            "        Sign out from this or all device",
            "        Delete your account"};

    File file;
    private static final String TAG = MainActivity.class.getSimpleName() + " Profile Fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SdCardPath")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findAllViewById(view);
        getCurrentUserImageKey();
        setListView();
        imageDownload();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentUserImageKey();
        imageDownload();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void findAllViewById(View view) {
        listView = view.findViewById(R.id.profile_list_views);
        userFullName = (TextView) view.findViewById(R.id.user_full_name);
        profileImage = view.findViewById(R.id.prfile_page_img);
        loading = view.findViewById(R.id.profile_page_progress_bar);
    }

    private void getCurrentUserImageKey() {
        UserLogIn userLogIn;
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        if (mySharedPreferences.contains("userLog")) {
            Gson gson = new Gson();
            userLogIn = gson.fromJson(mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);
            imageToDownloadKey = userLogIn.getEmail().replace(".", "").replace("@", "").replace("_", "");
            userFullName.setText(userLogIn.getFirstName() + " " + userLogIn.getLastName());
            System.out.println(userLogIn.getFirstName() + userLogIn.getLastName() + "99999***");
        }
        imageDownload();
    }

    private void imageDownload() {
        profileImage.setImageURI(null);

        file = new File(getContext().getFilesDir() + "/" + "userProfile" + ".jpg");
        Log.i(TAG, "imageDownload: is the file exist -> " + file.exists());
        Amplify.Storage.getUrl(imageToDownloadKey, res -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext())
                            .load(res.getUrl())
                            .circleCrop()
                            .centerInside()
                            .into(profileImage);
                }
            });
        }, err -> {

        });

//        if (!file.exists()) {
//            Amplify.Storage.downloadFile(
//                    imageToDownloadKey,
//                    file,
//                    result -> {
//                        Log.i(TAG, "The root path is: " + getContext().getFilesDir());
//                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println("testImage Donlad");
//                                showTheImageInThePage(file);
//                            }
//                        });
//
//                    },
//                    error -> Log.e(TAG, "Download Failure", error)
//            );
//        }else {
//            showTheImageInThePage(file);
//        }
    }

    private void showTheImageInThePage(File file) {

        if (file != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(bitmap);
            System.out.println(file.getPath() + "plapla");
        }

    }


    private void setListView() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, itemList) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView name = view.findViewById(android.R.id.text1);
                TextView subItem = view.findViewById(android.R.id.text2);

                /*
                 * How to set the text style from java side
                 * https://www.codegrepper.com/code-examples/whatever/make+text+bold+android+studio
                 */
                name.setTypeface(null, Typeface.BOLD);


                name.setText(itemList[position]);
                switch (position) {
                    case 0:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.post_vector_asset, 0, 0, 0);
                        break;
                    case 1:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.profile_page_pill_vector, 0, 0, 0);
                        break;
                    case 2:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.edit_profile_vector_asset, 0, 0, 0);
                        break;
                    case 3:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_vector_asset, 0, 0, 0);
                        break;
                    case 4:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.logout, 0, 0, 0);
                        break;
                    case 5:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_vector_asset, 0, 0, 0);
                        break;
                    default:
                }

                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            switch (i) {
                case 0:
                    navigateToMyPosts();
                    break;
                case 1:
                    navigateToMyDrugsPage();
                    break;
                case 2:
                    navigateToEditProfilePage();
                    break;
                case 3:
                    navigateToChangePasswordPage();
                    break;
                case 4:
                    signOut();
                    break;
                case 5:
                    navigateToDeleteAccountActivity();
                    break;
                default:
            }
        });
    }

    private void navigateToMyPosts() {
        Intent intent = new Intent(getContext(), MyPosts.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void navigateToMyDrugsPage() {
        Intent intent = new Intent(getContext(), MyDrugs.class);
        startActivity(intent);
    }

    private void navigateToEditProfilePage() {

        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(intent);


    }

    private void navigateToChangePasswordPage() {
        startActivity(new Intent(getContext(), ChangePasswordActivity.class));
    }

    private void signOut() {
        loading.setVisibility(View.VISIBLE);
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    navigateToLoginPage();
                },
                error -> {
                    Log.e(TAG, error.toString());
                    runOnUiThread(() -> {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        onResume();
                    });
                }
        );
    }

    private void navigateToLoginPage() {
        runOnUiThread(() -> {
            loading.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
    }

    private void navigateToDeleteAccountActivity() {

        startActivity(new Intent(getContext(), DeleteAccountActivity.class));
    }

    private void refresh() {

        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}