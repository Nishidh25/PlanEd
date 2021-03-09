package com.proj.planed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proj.planed.HomeActivity;
import com.proj.planed.OnboardingActivity;
import com.proj.planed.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton logout =  root.findViewById(R.id.floatingActionButton);;

        TextView textName = root.findViewById(R.id.textViewName);

        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        assert user != null;
        textName.setText(user.getDisplayName());
        //textEmail.setText(user.getEmail());
        String uid = user.getUid();

        DocumentReference docRef = db.collection("users").document(uid);
        //String Name = docRef.get().getResult().getString("First name");
        String  TAG = "";
    /*      docRef.get().addOnSuccessListener( task ->{
            DocumentSnapshot document =  docRef.get().getResult();
            if(task.exists()){
              Log.d(TAG, "DocumentSnapshot data: " + document.getData());
              Log.d(TAG, "db firstName getString() is: " + document.getString("firstName"));
              Log.d(TAG, "db lastName getString() is: " + document.getString("lastName"));

              String mFirstName = (String) document.getString("First name");
              String mLastName = (String) document.getString("Last name");
              Log.d(TAG, "String mFirstName is: " + mFirstName);
              Log.d(TAG, "String mLastName is: " + mLastName);
              textName.setText(mFirstName + " " +mLastName);
              }
          else{
              Log.d(TAG, "No such document");
          }

        }); */

        docRef.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    Log.d(TAG, "db firstName getString() is: " + document.getString("firstName"));
                    Log.d(TAG, "db lastName getString() is: " + document.getString("lastName"));

                    String mFirstName = (String) document.getString("First name");
                    //String mLastName = (String) document.getString("Last name");
                    Log.d(TAG, "String mFirstName is: " + mFirstName);
                    //Log.d(TAG, "String mLastName is: " + mLastName);
                    textName.setText("Welcome "+mFirstName);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

   /*     docRef.get().addOnSuccessListener(task -> {

                        DocumentSnapshot document = task.getDocumentReference();
                        if (document.exists()) {

                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Log.d(TAG, "db firstName getString() is: " + document.getString("firstName"));
                            Log.d(TAG, "db lastName getString() is: " + document.getString("lastName"));

                            String mFirstName = (String) document.getString("First name");
                            String mLastName = (String) document.getString("Last name");
                            Log.d(TAG, "String mFirstName is: " + mFirstName);
                            Log.d(TAG, "String mLastName is: " + mLastName);
                            textName.setText(mFirstName + " " +mLastName);
                        } else {
                            Log.d(TAG, "No such document");

                    }
                } ); */


        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(root.getContext(), OnboardingActivity.class));
           // FragmentActivity.finish();
        });
        return root;
    }


   // @Override
    protected void onStartView() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            //getActivity().finish();
            startActivity(new Intent(getContext(), OnboardingActivity.class));
            getActivity().finish();
        }
    }
}