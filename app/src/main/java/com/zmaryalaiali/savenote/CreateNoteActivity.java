package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;


public class CreateNoteActivity extends AppCompatActivity {

    EditText etTitle, etDescription;
    FloatingActionButton createFab;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;

    CollectionReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        etDescription = findViewById(R.id.et_description);
        etTitle = findViewById(R.id.et_title);
        createFab = findViewById(R.id.save_fab);

        Toolbar toolbar = findViewById(R.id.toolbar_create_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, description;
                title = etTitle.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                String createDate;
                createDate = new Date(System.currentTimeMillis()).toString();
                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "All field required ", Toast.LENGTH_SHORT).show();
                } else {

                    reference = firestore.collection("noteBook").document(firebaseUser.getUid()).collection("notes");

                    reference.add(new NoteModel(title, description, createDate, " ")).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CreateNoteActivity.this, "Success create note", Toast.LENGTH_SHORT).show();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateNoteActivity.this, "Some error occur please solve it " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}