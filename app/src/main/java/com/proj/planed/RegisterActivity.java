package com.proj.planed;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{

    String[] gender_list = { "Male", "Female"};
    String gender = "";
    EditText first_name, last_name, phone_number, age,height_et,weight_et;
    FirebaseAuth mAuth;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone_number = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        height_et = findViewById(R.id.height);
        weight_et = findViewById(R.id.weight);




        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;

        String uid = user.getUid();



        Spinner spinner_gender = findViewById(R.id.spinnergender);






        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender_list);

        spinner_gender.setAdapter(dataAdapter);

        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                String item = parent.getItemAtPosition(position).toString();
                gender = item;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });




        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setPrompt("Gender");
        // attaching data adapter to spinner
        spinner_gender.setAdapter(dataAdapter);

        Button register =  findViewById(R.id.button_register);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        register.setOnClickListener(v -> {

            if (CheckAllFields()) {
                Map<String, Object> users = new HashMap<>();
                users.put("First name", first_name.getText().toString());
                users.put("Last name", last_name.getText().toString());
                users.put("Email", user.getEmail());
                users.put("Phone number", phone_number.getText().toString());
                users.put("Gender", gender);
                users.put("Age", Integer.parseInt(age.getText().toString()));
                users.put("Height", height_et.getText().toString());
                users.put("Weight", weight_et.getText().toString());

                db.collection("users").document(uid).set(users);
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

    }

    private boolean CheckAllFields() {
        if (first_name.length() == 0) {
            first_name.setError("This field is required");
            return false;
        }

        if (last_name.length() == 0) {
            last_name.setError("This field is required");
            return false;
        }



        if (phone_number.length() == 0) {
            phone_number.setError("Phone Number is required");
            return false;
        }else if (phone_number.length() != 10) {
            phone_number.setError("Phone Number must be minimum 10 characters");
            return false;
        }

        if (age.length() == 0) {
            age.setError("Age is required");
            return false;
        }

       // after all validation return true.
        return true;
    }



}
