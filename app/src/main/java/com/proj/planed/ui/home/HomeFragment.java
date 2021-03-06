package com.proj.planed.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proj.planed.NavigationActivity;
import com.proj.planed.OnboardingActivity;
import com.proj.planed.R;
import com.proj.planed.ui.faq.FaqActivity;

public class HomeFragment extends Fragment {
    SearchView searchView1;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View root = inflater.inflate(R.layout.fragment_home, container, false);


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


        CardView card_pill_reminder = root.findViewById(R.id.card_pill_reminder);
        CardView card_planner = root.findViewById(R.id.card_planner);
        CardView card_faq = root.findViewById(R.id.card_faq);
        CardView card_more = root.findViewById(R.id.card_more);


        card_pill_reminder.setOnClickListener( v-> {

            BottomNavigationView navView = getActivity().findViewById(R.id.bottom_navigation_view);
            navView.setSelectedItemId(R.id.navigation_alarm);
            //    navView.getSelectedItemId();
        });

        card_planner.setOnClickListener( v-> {

            BottomNavigationView navView = getActivity().findViewById(R.id.bottom_navigation_view);
            navView.setSelectedItemId(R.id.navigation_notifications);

        });

        card_faq.setOnClickListener( v-> {

            Intent intent = new Intent(getContext(), FaqActivity.class);
            startActivity(intent);

        });

        card_more.setOnClickListener( v-> {

            DrawerLayout layout = getActivity().findViewById(R.id.drawer_layout);
            layout.openDrawer(GravityCompat.START);

        });





        return root;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");

        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();

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