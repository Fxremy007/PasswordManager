package com.example.mobapp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ModifyWebsite extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String TAG = "CreateWebsite";

    private String name, url, login, password, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_website);

        EditText txt_name = (EditText)findViewById((R.id.nameWebsite));
        name = txt_name.getText().toString().trim();
        EditText txt_url = (EditText)findViewById((R.id.url));
        url = txt_url.getText().toString().trim();
        EditText txt_login = (EditText)findViewById((R.id.loginWebsite));
        login = txt_login.getText().toString().trim();
        EditText txt_password = (EditText)findViewById((R.id.passwordWebsite));
        password = txt_password.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        id = user.getUid();


    }

    private void updateWebsite() {
    }

    private void deleteWebsite() {
        /*db.collection("Users").document("Website").collection(id) //manque un .document() avec l'ID du site sélectionné
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete :
                deleteWebsite();
                break;
            case R.id.btn_update :
                updateWebsite();
                break;
            case R.id.txt_back:
                Intent i_back = new Intent(this, MainPage.class);
                startActivity(i_back);
                break;
        }
    }
}