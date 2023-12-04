package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SinupActivity extends AppCompatActivity {
    TextView tvGoBack;
    TextInputLayout etEmail;
    TextInputLayout etPassword;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);
// initialize all widget to xml 
        tvGoBack = findViewById(R.id.tv_go_back);
        etEmail = findViewById(R.id.et_email_signup);
        etPassword = findViewById(R.id.et_password_signup);
// initialize our user to current user
        auth = FirebaseAuth.getInstance();
        // set dialog 
        progressDialog = new ProgressDialog(this);
        // go back to login button here
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void userSignup(View view) {
        //               first show the progressDialog
        progressDialog.show();
        // email and password and get it
        String email, password;
        email = etEmail.getEditText().getText().toString().trim();
        password = etPassword.getEditText().getText().toString();
//               check it is not null or empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SinupActivity.this, "Please provide All field", Toast.LENGTH_SHORT).show();
            // progress dismiss
            progressDialog.dismiss();
        } else {
//                     create new user by email and password in Firebase
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SinupActivity.this, "Successfully create  user", Toast.LENGTH_SHORT).show();
                        verifiedEmail(email,password);
                    } else {
                        etEmail.setError("Don't created user " + task.getException().getMessage());
                        Toast.makeText(SinupActivity.this, "Don't created user " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void verifiedEmail(String email, String password) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SinupActivity.this);
                    builder.setCancelable(false)
                            .setTitle("verification Email")
                            .setMessage("Pleaser go to Email and verifi your email after email verifi you can login")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(SinupActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SinupActivity.this,LoginActivity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("password",password);
                                    intent.putExtra("isEmail",true);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    builder .create();
                    builder.show();
                    Toast.makeText(SinupActivity.this, "please Verified your Email go Login ", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(SinupActivity.this, "Don't verified your Email try again " + task.getException(), Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    progressDialog.dismiss();
                }

            }
        });
    }
}