package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
public class ForgetPassword extends AppCompatActivity {
    TextInputLayout etForgetPassword;
    Button btnForgetPassword;
    TextView tvGoBackTOMainActivity;
    FirebaseAuth firebaseAuth;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etForgetPassword = findViewById(R.id.et_forget_password_text);
        tvGoBackTOMainActivity = findViewById(R.id.tv_go_back);
        btnForgetPassword = findViewById(R.id.btn_forget_password);
        firebaseAuth = FirebaseAuth.getInstance();

        btnForgetPassword.setOnClickListener((t) -> {
            String email = etForgetPassword.getEditText().getText().toString();
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPassword.this, "please Reset your password ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        finish();
                        Toast.makeText(ForgetPassword.this, "Please provide correct Email ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        tvGoBackTOMainActivity.setOnClickListener((t) -> {
            finish();
        });
    }

}
