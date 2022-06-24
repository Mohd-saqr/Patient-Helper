package com.patient.patienthelper.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activitys.MainActivity;

import java.io.File;
import java.util.Objects;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private ListView listView;
    private TextView textView;
    private ImageView profileImage;
    private String downloadedImagePath;
    private final String[] itemList = {" Edit Profile", " Change password", " Delete account", ""};
    private final String[] subItemsList = {
            "        Change name, age, male and email",
            "        Change current password",
            "        Delete your account"};
    private static final String TAG = MainActivity.class.getSimpleName() + " Profile Fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findAllViewById(view);
        setListView();
        return view;
    }

    private void findAllViewById(View view) {
        listView = (ListView) view.findViewById(R.id.profile_list_views);
        //textView = (TextView) view.findViewById(R.id.user_welcoming_profile);
        profileImage = view.findViewById(R.id.img_profile);
    }

    @SuppressLint("SdCardPath")
    private void imageDownload() {
        downloadedImagePath = "/data/data/com.patient.patienthelper/files/";
        File file = new File(downloadedImagePath);
        Log.i(TAG, "imageDownload: is the file exist -> " + file.exists());
        if (!file.exists()) {
            Amplify.Storage.downloadFile(
                    /*
                    TODO
                    Add the image code
                     */
                    "currentTask.getTaskImageCode()",
                    file,
                    result -> {
                        Log.i(TAG, "The root path is: " + requireContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());

                        downloadedImagePath = result.getFile().getPath();
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }

    private void showTheImageInThePage() {

        /*
        TODO
        Add the image code
         */
        Bitmap bMap = BitmapFactory.decodeFile(downloadedImagePath + "currentTask.getTaskImageCode()" + ".jpg");
        profileImage.setImageBitmap(bMap);
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
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.profile_vector_assest, 0, 0, 0);
                        break;
                    case 1:
                        subItem.setText(subItemsList[position]);
                        name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_vector_asset, 0, 0, 0);
                        break;
                    case 2:
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
//                    navigateToEditPage();
                    break;
                case 1:
//                    navigateToResetPasswordPage();
                    break;
                case 2:
                    deleteAccountAlertDialog();
                    break;
                default:
            }
        });
    }

    private void deleteAccountAlertDialog() {
        /*
        https://stackoverflow.com/questions/33437398/how-to-change-textcolor-in-alertdialog
        how to change the text color in alert dialog
        */
        String random = randomString() + "";
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setMessage("Are you sure to delete your account?\n\nEnter " + random + " To confirm");
        alert.setTitle(Html.fromHtml("<font color='#FF0000'>Warning!</font>"));

        alert.setView(edittext);

        alert.setPositiveButton(Html.fromHtml("<font color='#FF0000'>ok</font>"), (dialog, whichButton) -> {
            if (edittext.getText().toString().equals(random + "")) {
                //deleteAccount();
//                navigateToLoginPage();
//                finish();
            } else {
                deleteAccountAlertDialog();
            }
        });

        alert.setNegativeButton("cancel", (dialog, whichButton) -> onResume());

        alert.show();
    }

    private int randomString() {
        return (int) (Math.random() * (5000 - 2000) + 1) + 2000;
    }
}