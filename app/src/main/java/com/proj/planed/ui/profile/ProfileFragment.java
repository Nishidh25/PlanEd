package com.proj.planed.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proj.planed.OnboardingActivity;
import com.proj.planed.R;
import com.proj.planed.ui.faq.FaqActivity;

public class ProfileFragment extends Fragment {

    boolean set = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        View root = inflater.inflate(R.layout.activity_profile, container, false);
        Button update = root.findViewById(R.id.update);
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;


        TextView name1 = root.findViewById(R.id.fullname_field);
        TextView uname = root.findViewById(R.id.username_field);
        TextInputLayout name = root.findViewById(R.id.full_name_profile);
        TextInputLayout age = root.findViewById(R.id.age);
        TextInputLayout gender = root.findViewById(R.id.gender);
        TextInputLayout weight_tl = root.findViewById(R.id.weight);
        TextInputLayout height_tl = root.findViewById(R.id.height);



        name.setPlaceholderText(user.getDisplayName());
        name.getEditText().setText(user.getDisplayName());
        //textEmail.setText(user.getEmail());
        String uid = user.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        String TAG = "";
        docRef.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    Log.d(TAG, "db firstName getString() is: " + document.getString("firstName"));
                    Log.d(TAG, "db lastName getString() is: " + document.getString("lastName"));

                    String mFirstName = (String) document.getString("First name");
                    String mLastName = (String) document.getString("Last name");
                    Long mAge = (Long) document.get("Age");
                    String mGender = (String) document.getString("Gender");


                    Log.d(TAG, "String mFirstName is: " + mFirstName);
                    //Log.d(TAG, "String mLastName is: " + mLastName);
                    name.getEditText().setText(mFirstName + " " + mLastName);
                    name1.setText(mFirstName);
                    uname.setText(mLastName);
                    age.getEditText().setText(mAge.toString());
                    gender.getEditText().setText(mGender);
                    height_tl.getEditText().setText((String)document.getString("Height"));
                    weight_tl.getEditText().setText((String)document.getString("Weight"));
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });


        FloatingActionButton logout =  root.findViewById(R.id.floatingActionButton);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
            startActivity(new Intent(root.getContext(), OnboardingActivity.class));
            // FragmentActivity.finish();
        });


        FloatingActionButton bmi_btn =  root.findViewById(R.id.floatingActionButton2);
        bmi_btn.setOnClickListener(v -> {
            double BMI;
            String quality;
            String help;
            String link;

            double weight1 = Double.parseDouble(weight_tl.getEditText().getText().toString());
            double height1 = Double.parseDouble(height_tl.getEditText().getText().toString()) ;

            BMI = weight1 / (height1 *0.01 * height1*0.01);

            if (BMI < 18.5 ){
                quality = "Underweight";
                help = "Maintaining a healthy weight may reduce the risk of chronic diseases associated with overweight and obesity. For more on how to maintain a healthy diet with physical activity – Click More info";
                link = "https://www.cdc.gov/healthyweight/prevention/index.html";

            }else if (BMI >= 18.5 && BMI <=24.9 ){
                quality = "Normal weight";
                help = "It is important for you to gain weight. Go to your healthcare provider to determine possible causes of underweight. Click on more info to see some ways to gain weight.";
                link = "https://www.healthline.com/nutrition/how-to-gain-weight#TOC_TITLE_HDR_3";

            }else if (BMI >= 25 && BMI <=29.9 ) {
                quality = "Overweight";
                help = "People who are overweight or obese are at higher risk for chronic conditions such as high blood pressure, diabetes, and high cholesterol. For information about the importance of a healthy diet and physical activity in reaching a healthy weight, click on more info";
                link = "https://www.cdc.gov/healthyweight/losing_weight/index.html";


            }else if (BMI >= 30) {
                quality = "Obese";
                help = "People who are overweight or obese are at higher risk for chronic conditions such as high blood pressure, diabetes, and high cholesterol. It is important for you to lose weight, even a small weight loss (just 10% of your current weight) may help lower the risk of disease. Click on more info to see how";
                link = "https://www.cdc.gov/healthyweight/index.html";

             }else {
                quality = "Unknown: Input Error";
                help = "";
                link = "";
            }



            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("BMI Checker");
            builder.setMessage("BMI: "+ BMI + "\nCategory: "+ quality );

            // add a button
            builder.setPositiveButton("OK", (dialog1, which) -> {
                // Close
            });

            // To faq page
            builder.setNeutralButton("More Help", (dialog1, which) -> {

                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(getContext());
                alertDialog1.setTitle("Category - " + quality);
                alertDialog1.setMessage(help);

                alertDialog1.setPositiveButton("Got It!", (dialog11, which1) -> {
                    // Close
                });

                alertDialog1.setNeutralButton("More Info", (dialog11, which1) -> {
                    Uri uri = Uri.parse(link);
                    Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(gSearchIntent);
                });



                AlertDialog dialog13 = alertDialog1.create();
                dialog13.show();




            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        update.setOnClickListener(v -> {
            if(!set){
                update.setText("Save");
                set = true;
            }else {
                update.setText("Edit");
                set = false;
            }
            name.setEnabled(set);
            gender.setEnabled(set);
            age.setEnabled(set);
            height_tl.setEnabled(set);
            weight_tl.setEnabled(set);
        });

        return root;
    }

}
