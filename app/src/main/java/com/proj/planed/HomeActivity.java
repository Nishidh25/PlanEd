package com.proj.planed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textName, textEmail;
    FirebaseAuth mAuth;

    private Object GoogleCredentials;
    // Use the application default credentials



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String TAG = "Sample TAG";
        mAuth = FirebaseAuth.getInstance();
        String uid = "ABCD";
        //imageView = findViewById(R.id.imageView);
        //textName = findViewById(R.id.textViewName);
        //textEmail = findViewById(R.id.textViewEmail);


        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        assert user != null;
        //textName.setText(user.getDisplayName());
        //textEmail.setText(user.getEmail());
        //Button logout = findViewById(R.id.button);

        //logout.setOnClickListener(v -> {
        //    FirebaseAuth.getInstance().signOut();
        //    startActivity(new Intent(getApplicationContext(), OnboardingActivity.class));
       //     HomeActivity.this.finish();
       // });


        //Button firestore = findViewById(R.id.Firestorebutton);
        //firestore.setOnClickListener(v -> {

     /*        Map<String, Object> users = new HashMap<>();
            users.put("Name", user.getDisplayName());
            users.put("Email", user.getEmail());
            users.put("born", 2000);
       /*   docData.put("stringExample", "Hello, World");
            docData.put("booleanExample", false);
            docData.put("numberExample", 3.14159265);
            docData.put("nullExample", null);
            users.put("uid", request.auth.uid );*/



            //db.collection("users").document(uid).set(users);



            //https://firebase.google.com/docs/firestore/security/rules-conditions#data_validation
     /*       db.collection("users")
                    .add(users)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }); */


    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, OnboardingActivity.class));
            HomeActivity.this.finish();
        }
    }
}
