package com.example.health;

import static com.example.health.FirebaseUtils.getCurrentUser;
import static com.example.health.FirebaseUtils.getUserReference;
import static com.example.health.FirebaseUtils.getUsersReference;

import android.os.Bundle;
import android.util.ArraySet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserProfileActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseRef;
    MainAdapter mainAdapter;
    Set<UserModel> list = new LinkedHashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainAdapter = new MainAdapter(this, new ArrayList<>(list));
        recyclerView.setAdapter(mainAdapter);

        databaseRef = getUsersReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new LinkedHashSet<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    if(!getCurrentUser().getUid().equals(dataSnapshot.getKey())) {
                        userModel.setUserId(dataSnapshot.getKey());
                        list.add(userModel);
                    }
                }

                mainAdapter.list = new ArrayList<>(list);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}