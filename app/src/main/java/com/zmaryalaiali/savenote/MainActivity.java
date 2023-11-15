package com.zmaryalaiali.savenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    FloatingActionButton createNoteFab;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_all_notes);
        createNoteFab = findViewById(R.id.create_note_fab);

        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Notes");
        createNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CreateNoteActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
    if (id == R.id.logout){
        auth.signOut();
    return true;
    }
    else if(R.id.setting== id) {
        Toast.makeText(this, "settings ", Toast.LENGTH_SHORT).show();
        return true;
    }
        return super.onOptionsItemSelected(item);
    }
}