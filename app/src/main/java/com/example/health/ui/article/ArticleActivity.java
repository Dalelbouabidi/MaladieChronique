package com.example.health.ui.article;

import static com.example.health.FirebaseUtils.getDataArticlesReference;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class ArticleActivity extends BaseActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseRef;
    AdapterMain adapter;
    Set<Articles> list = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        drawerLayout = findViewById(R.id.drawerlayout);
        recyclerView = (RecyclerView) findViewById(R.id.lv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterMain(this, new ArrayList<>(list));
        recyclerView.setAdapter(adapter);

        databaseRef = getDataArticlesReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new LinkedHashSet<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Articles articles = dataSnapshot.getValue(Articles.class);

                    articles.setUid(snapshot.getKey());
                    list.add(articles);

                }
                adapter.list = new ArrayList<>(list);
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