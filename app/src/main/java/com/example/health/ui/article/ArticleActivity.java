package com.example.health.ui.article;

import static com.example.health.Constant.USERS;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.Articles;
import com.example.health.ui.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArticleActivity extends BaseActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    AdapterMain adapter;
    ArrayList<Articles> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        drawerLayout = findViewById(R.id.drawerlayout);
        recyclerView = (RecyclerView) findViewById(R.id.lv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new AdapterMain(this, list);
        recyclerView.setAdapter(adapter);
        db = FirebaseDatabase.getInstance().getReference(USERS).child("Articles");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Articles articles = dataSnapshot.getValue(Articles.class);
                    list.add(articles);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void ClickArticleMedical(View view) {
        recreate();
    }

}