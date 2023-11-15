package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.icu.util.LocaleData;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.sql.Date;
import java.sql.Time;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CreateNoteActivity extends AppCompatActivity {

    EditText etTitle,etDescription;
    FloatingActionButton createFab;
    FirebaseAuth auth;
    FirebaseUser firebaseUser ;
    FirebaseFirestore firestore;

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
        firebaseUser = auth.getCurrentUser();

        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title,description;
                title = etTitle.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                String createDate;
             createDate = new Date(System.currentTimeMillis()).toString();
                if (title.isEmpty() || description.isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "All field required ", Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference = firestore.collection("notes").document().collection(firebaseUser.getUid())
                            .document().collection("myNote").document();
                    Map<String,Object> note = new HashMap<>();
                    note.put("title",title);
                    note.put("description",description);
                    note.put("createDate",createDate);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateNoteActivity.this, "Success create note", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateNoteActivity.this, "Some error occur please solve it "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}