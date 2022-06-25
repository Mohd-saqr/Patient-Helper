package com.patient.patienthelper.fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.amplifyframework.core.Amplify;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activitys.ChangePasswordActivity;
import com.patient.patienthelper.activitys.DeleteAccountActivity;
import com.patient.patienthelper.activitys.EditProfileActivity;
import com.patient.patienthelper.activitys.LoginActivity;
import com.patient.patienthelper.activitys.MainActivity;

import java.io.File;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private ListView listView;
    private ImageView profileImage;
    private TextView userFullName;
    private String downloadedImagePath;
    private ProgressBar profilePageProgressBar;
    private final String[] itemList = {" Edit Profile", " Change password", " Sign out", " Delete account"};
    private final String[] subItemsList = {
            "        Change name, age, male and email",
            "        Change current password",
            "        Sign out from this or all device",
            "        Delete your account"};
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
        setListView();
        imageDownload();
        return view;
    }

    private void findAllViewById(View view) {
        listView = view.findViewById(R.id.profile_list_views);
        userFullName = (TextView) view.findViewById(R.id.user_full_name);
        profileImage = view.findViewById(R.id.img_profile);
        profilePageProgressBar = view.findViewById(R.id.profile_page_progress_bar);
    }

    @SuppressLint("SdCardPath")
    private void imageDownload() {
        downloadedImagePath = "/data/data/com.patient.patienthelper/files/";
        File file = new File(getContext().getFilesDir() + "/"+"userProfile"+".jpg");
        Log.i(TAG, "imageDownload: is the file exist -> " + file.exists());
        if (!file.exists()) {
            Amplify.Storage.downloadFile(
                    /*
                    TODO
                    Add the image code
                     */
                    "ghanem97outlookcom",
                    file,
                    result -> {
                        Log.i(TAG, "The root path is: " + requireContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());

                        downloadedImagePath = result.getFile().getPath();
                        showTheImageInThePage(file);
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }else {
            showTheImageInThePage(file);
        }
    }

    private void showTheImageInThePage(File file) {

        if (file != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(bitmap);
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
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.edit_profile_vector_asset, 0, 0, 0);
                        break;
                    case 1:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_vector_asset, 0, 0, 0);
                        break;
                    case 2:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.logout,0,0,0);
                        break;
                    case 3:
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
                    navigateToEditProfilePage();
                    break;
                case 1:
                    navigateToChangePasswordPage();
                    break;
                case 2:
                    signOut();
                    break;
                case 3:
                    navigateToDeleteAccountActivity();
                    break;
                default:
            }
        });
    }

    private void navigateToEditProfilePage(){

        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(intent);

    }

    private void navigateToChangePasswordPage(){
        startActivity(new Intent(getContext(), ChangePasswordActivity.class));
    }

    private void signOut() {
        profilePageProgressBar.setVisibility(View.VISIBLE);
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    navigateToLoginPage();
                },
                error -> {
                    Log.e(TAG, error.toString());
                    runOnUiThread(() -> {
                        profilePageProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        onResume();
                    });
                }
        );
    }

    private void navigateToLoginPage() {
        runOnUiThread(() -> {
            profilePageProgressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
    }
    private void navigateToDeleteAccountActivity(){

        startActivity(new Intent(getContext(), DeleteAccountActivity.class));
    }
}