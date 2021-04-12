package com.proj.planed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseActivity extends AppCompatActivity {

    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    //Tag for the logs optional
    private static final String TAG = "TAG ";
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private String login = "Google Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (getIntent().getStringExtra("Type").equals("Register")){


            setTitle("Registration");
            setContentView(R.layout.activity_signup);
            //getActionBar().setTitle();
            Button regBtn = findViewById(R.id.button_ereg);




            regBtn.setOnClickListener(v -> registerNewUser("Register"));
            login = "Google Sign up";
        }else {

            setTitle("Login");
            setContentView(R.layout.activity_login);
            //getActionBar().setTitle("Login");
            Button lgnBtn = findViewById(R.id.button_elogin);
            lgnBtn.setOnClickListener(v -> registerNewUser(""));

        }

        TextView title = findViewById(R.id.textView);
        if(useDarkTheme) {
            title.setTextColor(getResources().getColor(R.color.black));
        }



        //first we initialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(view -> gsignIn());
        setGooglePlusButtonText(findViewById(R.id.sign_in_button),login);


    }


    // Firebase Email auth
    private void registerNewUser(String type) {
        TextInputLayout emailTV = findViewById(R.id.Email);
        TextInputLayout passwordTV = findViewById(R.id.Password);
        TextInputLayout passwordTV2 = findViewById(R.id.ConfirmPassword);


        String email, password;
        email = emailTV.getEditText().getText().toString();
        password = passwordTV.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailTV.getEditText().setError("Please enter email...");
            return;
          //  Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();

        }
        if (TextUtils.isEmpty(password)) {
            passwordTV.getEditText().setError("Please enter password...");
          //  Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }



        if (type.equals("Register")){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();


                        //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        FirebaseActivity.this.finish();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registration failed! Please try again later" + task.getException(), Toast.LENGTH_LONG).show();

                    }
                });

        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                            startActivity(intent);
                            FirebaseActivity.this.finish();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    });
        }
    }


    // on start this activity
    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, NavigationActivity.class));
        }
    }


    // Google auth
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(FirebaseActivity.this, "Error message: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // Auth with google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                        Toast.makeText(FirebaseActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(FirebaseActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //Google signin
    private void gsignIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

}



