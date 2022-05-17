package com.example.health.ui.malady;

import static com.example.health.Constant.CHILD_TYPE_MALADY;
import static com.example.health.Constant.USERS;
import static com.example.health.FirebaseUtils.getDataReference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.TypeMalady;
import com.example.health.ui.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TypeMaladyActivity extends BaseActivity {

    private RecyclerView rvMalady;
    private Button btnAdd;


    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_malady);

        databaseRef = getDataReference();

        initView();

        initEvent();

        initData();

    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        rvMalady = findViewById(R.id.rv_malady);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void initEvent() {
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TypeMaladyActivity.this, AddTypeMaladyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void initData() {

        databaseRef.child(CHILD_TYPE_MALADY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<TypeMalady> typeMaladyList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TypeMalady typeMalady = childSnapshot.getValue(TypeMalady.class);
                    typeMaladyList.add(typeMalady);
                }
                TypeMaladyAdapter adapter = new TypeMaladyAdapter(typeMaladyList);
                rvMalady.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TypeMaladyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void ClickAjouterTypeMaladie(View view) {
        recreate();
    }
}