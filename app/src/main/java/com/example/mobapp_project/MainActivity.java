package com.example.mobapp_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ResearchUser";

    private String email, password;

    private FirebaseAuth mAuth;
    //@Nullable
    //protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean isNotEmptyEmail() {
        EditText txt_email = (EditText)findViewById(R.id.email_main);
        email = txt_email.getText().toString().trim();

        if (email.isEmpty()) {
            txt_email.setError("Please, enter your email");
            return false;
        } else {
            return true;
        }
    }

    private boolean isNotEmptyPassword() {
        EditText txt_password = (EditText)findViewById(R.id.password);
        password = txt_password.getText().toString().trim();

        if (password.isEmpty()) {
            txt_password.setError("Please, enter your password");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login :
                if (isNotEmptyEmail() == false || isNotEmptyPassword() == false) { return; }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() ) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Intent intent = new Intent(MainActivity.this, MainPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.btn_signIn :
                Intent intentSignIn = new Intent(this, SignIn_page.class);
                startActivity(intentSignIn);
                break;
            case R.id.txt_forgotPassword :
                Intent intentRecoverPassword = new Intent(this, RecoverPassword_page.class);
                startActivity(intentRecoverPassword);
                break;
        }
    }
}