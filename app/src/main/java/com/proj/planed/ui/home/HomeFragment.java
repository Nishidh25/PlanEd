package com.proj.planed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proj.planed.NavigationActivity;
import com.proj.planed.OnboardingActivity;
import com.proj.planed.R;

public class HomeFragment extends Fragment {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab =  root.findViewById(R.id.floatingActionButton);

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



       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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



        });

        card_more.setOnClickListener( v-> {

            DrawerLayout layout = getActivity().findViewById(R.id.drawer_layout);
            layout.openDrawer(GravityCompat.START);

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