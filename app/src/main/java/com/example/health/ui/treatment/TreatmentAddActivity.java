package com.example.health.ui.treatment;

import static com.example.health.Constant.ANALYSES;
import static com.example.health.Constant.CHILD_ANALYSES;
import static com.example.health.Constant.CHILD_RENDEZVOUS;
import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.MED_NOM;
import static com.example.health.Constant.MED_QUANTITY;
import static com.example.health.Constant.RENDEZVOUS;
import static com.example.health.Constant.USERS;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.health.R;
import com.example.health.ui.BaseActivity;
import com.example.health.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreatmentAddActivity extends BaseActivity {


    private LinearLayout layoutlistMed;
    private LinearLayout layoutlistAnalyse;
    private LinearLayout layoutlistRendez;


    private Button btnAddMed;
    private Button btnAddAnalyse;
    private Button btnAddRendez;
    private Button btnSave;

    private final List<String> nombreList = new ArrayList<>();
    private DatabaseReference databaseRef;

    private boolean isSuccessAddTreatment = false;
    private boolean isSuccessAddAnalyse = false;
    private boolean isSuccessAddRendez = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_traitement);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseRef = firebaseDatabase.getReference(USERS).child(firebaseUser.getUid());

        initView();

        initEvent();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        layoutlistMed = findViewById(R.id.layout_list_med);
        layoutlistAnalyse = findViewById(R.id.layout_list_analyse);
        layoutlistRendez=findViewById(R.id.layout_list_rendez);

        btnAddMed = findViewById(R.id.btn_add_med);
        btnAddAnalyse = findViewById(R.id.btn_add_analyse);
        btnAddRendez = findViewById(R.id.btn_add_rendez);
        btnSave = findViewById(R.id.sauvegarderbtn);

        nombreList.add("Quantité de prise");
        nombreList.add("1");
        nombreList.add("2");
        nombreList.add("3");
    }

    private void initEvent() {
        btnAddMed.setOnClickListener(view -> addMedication());

        btnAddAnalyse.setOnClickListener(view -> addAnalyse());
        btnAddRendez.setOnClickListener(view -> addRendez());

        btnSave.setOnClickListener(view -> {
            // treatments medicament
            List<Map<String, String>> treatments = new ArrayList<Map<String, String>>();
            for (int i = 0; i < layoutlistMed.getChildCount(); i++) {
                View item = layoutlistMed.getChildAt(i);
                EditText medicNom = item.findViewById(R.id.medic_nom);
                AppCompatSpinner spinner = item.findViewById(R.id.spinner);

                Map<String, String> treatment = new HashMap<>();
                treatment.put(MED_NOM, medicNom.getText().toString());
                treatment.put(MED_QUANTITY, nombreList.get(spinner.getSelectedItemPosition()));
                treatments.add(treatment);
            }
            // analyses
            List<Map<String, String>> analyses = new ArrayList<Map<String, String>>();
            for (int i = 0; i < layoutlistAnalyse.getChildCount(); i++) {
                View item = layoutlistAnalyse.getChildAt(i);
                EditText analyseNom = item.findViewById(R.id.analyse_nom);

                Map<String, String> analyse = new HashMap<>();
                analyse.put(ANALYSES, analyseNom.getText().toString());
                analyses.add(analyse);
            }
            //rendez
            List<Map<String, String>> rendezv =new ArrayList<Map<String, String>>();
            for (int i = 0; i < layoutlistRendez.getChildCount(); i++) {
                View item = layoutlistRendez.getChildAt(i);
                EditText rendezNom = item.findViewById(R.id.rendez_nom);

                Map<String, String> rendezvous = new HashMap<>();
                rendezvous.put(RENDEZVOUS, rendezNom.getText().toString());
                rendezv.add(rendezvous);
            }

            addDataToFirebase(treatments, analyses,rendezv);

        });
    }

    private void addAnalyse() {
        View analyseView = getLayoutInflater().inflate(R.layout.row_ajouter_analyse, null, false);
        ImageView imageClose = analyseView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewAnalyse(analyseView);
            }
        });
        layoutlistAnalyse.addView(analyseView);
    }
    private void addRendez() {
        View rendezView = getLayoutInflater().inflate(R.layout.row_ajouter_rendez, null, false);
        ImageView imageClose = rendezView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewRendez(rendezView);
            }
        });
        layoutlistRendez.addView(rendezView);
    }


    private void removeViewAnalyse(View view) {
        layoutlistAnalyse.removeView(view);
    }


    private void removeAllViewAnalyse() {
        layoutlistAnalyse.removeAllViews();
    }

    private void removeViewRendez(View view) {
        layoutlistRendez.removeView(view);
    }


    private void removeAllViewRendez() {
        layoutlistRendez.removeAllViews();
    }


    private void addMedication() {
        View medicamentView = getLayoutInflater().inflate(R.layout.row_ajouter_medicament, null, false);
        AppCompatSpinner spinner = medicamentView.findViewById(R.id.spinner);
        ImageView imageClose = medicamentView.findViewById(R.id.immage_remove);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nombreList);
        spinner.setAdapter(arrayAdapter);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewMed(medicamentView);
            }
        });
        layoutlistMed.addView(medicamentView);
    }

    private void removeViewMed(View view) {
        layoutlistMed.removeView(view);
    }

    private void removeAllViewMed() {
        layoutlistMed.removeAllViews();
    }

    private void addDataToFirebase(List<Map<String, String>> treatments, List<Map<String, String>> analyses ,List<Map<String, String>> rendezv) {

        databaseRef.child(CHILD_TREATMENTS).push().setValue(treatments).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TreatmentAddActivity.this, "les données sont ajoutées", Toast.LENGTH_SHORT).show();
                    removeAllViewMed();
                    isSuccessAddTreatment = true;
                    redirection();
                } else {
                    Toast.makeText(TreatmentAddActivity.this, "les données ne sont pas ajoutées", Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseRef.child(CHILD_ANALYSES).push().setValue(analyses).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TreatmentAddActivity.this, "les données sont ajoutées", Toast.LENGTH_SHORT).show();
                    removeAllViewAnalyse();
                    isSuccessAddAnalyse = true;
                    redirection();
                } else {
                    Toast.makeText(TreatmentAddActivity.this, "les données ne sont pas ajoutées", Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseRef.child(CHILD_RENDEZVOUS).push().setValue(rendezv).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TreatmentAddActivity.this, "les données sont ajoutées", Toast.LENGTH_SHORT).show();
                    removeAllViewRendez();
                    isSuccessAddRendez = true;
                    redirection();
                } else {
                    Toast.makeText(TreatmentAddActivity.this, "les données ne sont pas ajoutées", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirection() {
        if (isSuccessAddTreatment && isSuccessAddAnalyse && isSuccessAddRendez) {
            redirectActivity(this, TreatmentActivity.class);
        }
    }

    public void ClickConsulterTraitement(View view) {
        MainActivity.redirectActivity(this, TreatmentActivity.class);
    }

}
