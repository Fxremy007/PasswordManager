package com.example.mobapp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateWebsite extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String TAG = "CreateWebsite";

    private String name, url, login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_website);
        mAuth = FirebaseAuth.getInstance();
    }

    private void createWebsite () {
        EditText txt_name = (EditText)findViewById((R.id.nameWebsite));
        name = txt_name.getText().toString().trim();
        EditText txt_url = (EditText)findViewById((R.id.url));
        url = txt_url.getText().toString().trim();
        EditText txt_login = (EditText)findViewById((R.id.loginWebsite));
        login = txt_login.getText().toString().trim();
        EditText txt_password = (EditText)findViewById((R.id.passwordWebsite));
        password = txt_password.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();

        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        data.put("URL", url);
        data.put("Login", login);
        data.put("Password", password);

        db.collection("Users").document("Website").collection(id)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Website created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_createWebsite :
                createWebsite();
                Intent i = new Intent(this, MainPage.class);
                startActivity(i);
                break;
            case R.id.txt_back:
                Intent i_back = new Intent(this, MainPage.class);
                startActivity(i_back);
                break;
        }
    }
}