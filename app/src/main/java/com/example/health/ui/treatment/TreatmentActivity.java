package com.example.health.ui.treatment;

import static com.example.health.Constant.CHILD_ANALYSES;
import static com.example.health.Constant.CHILD_RENDEZVOUS;
import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.FirebaseUtils;
import com.example.health.R;
import com.example.health.model.Analyse;
import com.example.health.model.RendezVous;
import com.example.health.model.Treatment;
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

public class TreatmentActivity extends BaseActivity {

    private RecyclerView rvMedications;
    private RecyclerView rvRendez;
    private RecyclerView rvAnalyses;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        initView();

        initData();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        rvMedications = findViewById(R.id.rv_medications);
        rvAnalyses = findViewById(R.id.rv_analyses);
        rvRendez = findViewById(R.id.rv_rendez);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TreatmentActivity.this, TreatmentAddActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void initData() {
        DatabaseReference databaseRef = FirebaseUtils.getDataReference();
        databaseRef.child(CHILD_TREATMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Treatment> treatments = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        Treatment treatment = snapshot2.getValue(Treatment.class);
                        treatments.add(treatment);
                    }
                }
                TreatmentAdapter adapter = new TreatmentAdapter(treatments);
                rvMedications.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        databaseRef.child(CHILD_ANALYSES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Analyse> analyses = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        Analyse analyse = snapshot2.getValue(Analyse.class);
                        analyses.add(analyse);
                    }
                }
                AnalyseAdapter adapter = new AnalyseAdapter(analyses);
                rvAnalyses.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        databaseRef.child(CHILD_RENDEZVOUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RendezVous> rendezs = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        RendezVous rendezvous = snapshot2.getValue(RendezVous.class);
                        rendezs.add(rendezvous);
                    }
                }
                RendezAdapter adapter = new RendezAdapter(rendezs);
                rvRendez.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void ClickConsulterTraitement(View view) {
        recreate();
    }

}