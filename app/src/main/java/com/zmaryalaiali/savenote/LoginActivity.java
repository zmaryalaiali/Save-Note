package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvSignUp;
    TextInputLayout etEmail;
    TextInputLayout etPassword;
    TextView tvForgetPassword;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_sign_up);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvForgetPassword = findViewById(R.id.tv_forger_password);


        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        // this is Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the progressDialog
                progressDialog.show();
                // email and password
                String email, password;
                // get Email and password from textInput
                email = etEmail.getEditText().getText().toString();
                password = etPassword.getEditText().getText().toString();
                // check Email and if not empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All field required ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    // login new Activity
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (firebaseUser.isEmailVerified()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Sign in go ahead", Toast.LENGTH_SHORT).show();
                                    // go ahead to new activity
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // user not verified
                                    Toast.makeText(LoginActivity.this, "First verified your Email after login ", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } else {
                                // user not signup
                                Toast.makeText(LoginActivity.this, "Please ont time signup and after signIn ", Toast.LENGTH_SHORT).show();
                                // the progressDialog dismiss
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        // go to signup activity
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,SinupActivity.class));
                startActivity(new Intent(LoginActivity.this,MainActivity.class));


            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                    startActivity(intent);
            }
        });

    }
}