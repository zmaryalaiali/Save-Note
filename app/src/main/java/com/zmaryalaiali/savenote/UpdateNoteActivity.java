package com.zmaryalaiali.savenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


public class UpdateNoteActivity extends AppCompatActivity {

    EditText etTitle, etDescription;
    
    FloatingActionButton fabUpdate;
    String title, description,createDate,updateDate;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore firestore;
    CollectionReference collectionReference ;
String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        etTitle = findViewById(R.id.et_title_update);
        etDescription = findViewById(R.id.et_description_update);
        fabUpdate = findViewById(R.id.fab_update);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        docId  = intent.getStringExtra("docId");
        createDate = intent.getStringExtra("createDate");
        updateDate =  new Date(System.currentTimeMillis()).toString();

        etDescription.setText(description);
        etTitle.setText(title);
        
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.equals(etTitle.getText().toString()) || !etDescription.getText().toString().equals(description)){
              DocumentReference snapshot = firestore.collection("noteBook").document(firebaseAuth.getCurrentUser().getUid()).collection("notes").document(docId);
              Map<String , Object> map = new HashMap<>();
              map.put("title",etTitle.getText().toString());
              map.put("description",etDescription.getText().toString());
              map.put("createDate",createDate);
              map.put("updateDate",updateDate);
              snapshot.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void unused) {
                      Toast.makeText(UpdateNoteActivity.this, "updated", Toast.LENGTH_SHORT).show();
                      finish();
                  }
              });
                }
                else{
                    Toast.makeText(UpdateNoteActivity.this, "Not Update for this", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
}