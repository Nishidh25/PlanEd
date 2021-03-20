package com.proj.planed.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proj.planed.R;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        View root = inflater.inflate(R.layout.activity_profile, container, false);

        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;



        TextInputLayout name = root.findViewById(R.id.full_name_profile);
        TextInputLayout age = root.findViewById(R.id.age);
        TextInputLayout gender = root.findViewById(R.id.gender);



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
                    age.getEditText().setText(mAge.toString());
                    gender.getEditText().setText(mGender);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        return root;
    }


}
