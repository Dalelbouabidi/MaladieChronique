package com.example.health;

import static com.example.health.FirebaseUtils.getDataArticlesReference;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.health.model.Articles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ArticleAdminActivity extends AppCompatActivity {

    DatabaseReference databaseRef = getDataArticlesReference();
    ArrayList<Articles> articless = new ArrayList<>();
    CustomAdapter adapter;
    ListView lv;
    EditText nameEditTxt, descTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = findViewById(R.id.lv);
        adapter = new CustomAdapter(this, articless);
        lv.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

        retrieve();

    }

    private void displayInputDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Save To Firebase");
        dialog.setContentView(R.layout.input_dialog);
        nameEditTxt = dialog.findViewById(R.id.nameEditText);
        descTxt = dialog.findViewById(R.id.descEditText);
        Button saveBtn = dialog.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditTxt.getText().toString();
                String desc = descTxt.getText().toString();

                //SET DATA
                Articles s = new Articles();
                s.setTitre(name);
                s.setDescription(desc);
                if (name.length() > 0) {
                    //THEN SAVE
                    databaseRef.push().setValue(s);
                    //IF SAVED CLEAR EDITXT
                    nameEditTxt.setText("");
                    descTxt.setText("");

                    adapter = new CustomAdapter(ArticleAdminActivity.this, articless);
                    lv.setAdapter(adapter);

                } else {
                    Toast.makeText(ArticleAdminActivity.this, "le champ ne peut pas etre vide", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void retrieve() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchData(snapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        articless.clear();

        for (DataSnapshot data : dataSnapshot.getChildren()) {
            Articles articles = data.getValue(Articles.class);
            articles.setUid(data.getKey());
            articless.add(articles);
        }
        adapter.notifyDataSetChanged();
    }
}

