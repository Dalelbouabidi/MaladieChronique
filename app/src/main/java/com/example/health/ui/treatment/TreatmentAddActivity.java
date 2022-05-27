package com.example.health.ui.treatment;

import static com.example.health.Constant.ANALYSES;
import static com.example.health.Constant.CHILD_ANALYSES;
import static com.example.health.Constant.CHILD_RENDEZVOUS;
import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.CHILD_TYPE_MALADY;
import static com.example.health.Constant.MALADE;
import static com.example.health.Constant.MED_NOM;
import static com.example.health.Constant.MED_QUANTITY;
import static com.example.health.Constant.RENDEZVOUS;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.health.FirebaseUtils;
import com.example.health.R;
import com.example.health.model.TypeMalady;
import com.example.health.ui.BaseActivity;
import com.example.health.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
    ArrayList<String> list = new ArrayList<String>();

    private final List<String> nombreList = new ArrayList<>();

    private boolean isSuccessAddTreatment = false;
    private boolean isSuccessAddAnalyse = false;
    private boolean isSuccessAddRendez = false;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_traitement);

        databaseReference = FirebaseUtils.getDataReference();

        initView();

        initEvent();

        fetchData();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        layoutlistMed = findViewById(R.id.layout_list_med);
        layoutlistAnalyse = findViewById(R.id.layout_list_analyse);
        layoutlistRendez = findViewById(R.id.layout_list_rendez);
        btnAddMed = findViewById(R.id.btn_add_med);
        btnAddAnalyse = findViewById(R.id.btn_add_analyse);
        btnAddRendez = findViewById(R.id.btn_add_rendez);
        btnSave = findViewById(R.id.sauvegarderbtn);

        nombreList.add("Quantité de prise");
        nombreList.add("1/2");
        nombreList.add("1");
        nombreList.add("2");
        nombreList.add("3");

/*
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner0.setAdapter(adapter);
        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mald = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Maladie: " + mald, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

    }

    private void fetchData() {
        databaseReference.child(CHILD_TYPE_MALADY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    TypeMalady typeMalady = snapshot1.getValue(TypeMalady.class);
                    if (typeMalady != null) {
                        list.add(typeMalady.getNameMalady());
                    }
                }
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentAddActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                Spinner medicQuantity = item.findViewById(R.id.medic_quantity);
                Spinner spinner = item.findViewById(R.id.medic_malady);
                String malady = spinner.getSelectedItem().toString();
                Map<String, String> treatment = new HashMap<>();
                treatment.put(MED_NOM, medicNom.getText().toString());
                treatment.put(MALADE, malady);
                treatment.put(MED_QUANTITY, nombreList.get(medicQuantity.getSelectedItemPosition()));
                treatments.add(treatment);
            }
            // analyses
            List<Map<String, String>> analyses = new ArrayList<Map<String, String>>();
            for (int i = 0; i < layoutlistAnalyse.getChildCount(); i++) {
                View item = layoutlistAnalyse.getChildAt(i);
                EditText analyseNom = item.findViewById(R.id.analyse_nom);
                Spinner spinner = item.findViewById(R.id.analyse_malady);
                String malady = spinner.getSelectedItem().toString();
                Map<String, String> analyse = new HashMap<>();
                analyse.put(ANALYSES, analyseNom.getText().toString());
                analyse.put(MALADE, malady);
                analyses.add(analyse);
            }
            //rendez
            List<Map<String, String>> rendezv = new ArrayList<Map<String, String>>();
            for (int i = 0; i < layoutlistRendez.getChildCount(); i++) {
                View item = layoutlistRendez.getChildAt(i);
                EditText rendezNom = item.findViewById(R.id.rendez_nom);
                Spinner spinner = item.findViewById(R.id.rendez_malady);
                String malady = spinner.getSelectedItem().toString();

                Map<String, String> rendezvous = new HashMap<>();
                rendezvous.put(RENDEZVOUS, rendezNom.getText().toString());
                rendezvous.put(MALADE, malady);
                rendezv.add(rendezvous);
            }

            addDataToFirebase(treatments, analyses, rendezv);

        });
    }

    private void addAnalyse() {
        View analyseView = getLayoutInflater().inflate(R.layout.row_ajouter_analyse, null, false);
        Spinner spinner = analyseView.findViewById(R.id.analyse_malady);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
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
        Spinner spinner = rendezView.findViewById(R.id.rendez_malady);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        ImageView imageClose = rendezView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewRendez(rendezView);
            }
        });
        layoutlistRendez.addView(rendezView);
    }

    private void addMedication() {
        View medicamentView = getLayoutInflater().inflate(R.layout.row_ajouter_medicament, null, false);
        Spinner medicQuantity = medicamentView.findViewById(R.id.medic_quantity);
        Spinner spinner = medicamentView.findViewById(R.id.medic_malady);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nombreList);
        medicQuantity.setAdapter(arrayAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        ImageView imageClose = medicamentView.findViewById(R.id.immage_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewMed(medicamentView);
            }
        });
        layoutlistMed.addView(medicamentView);
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

    private void removeViewMed(View view) {
        layoutlistMed.removeView(view);
    }

    private void removeAllViewMed() {
        layoutlistMed.removeAllViews();
    }

    private void addDataToFirebase(List<Map<String, String>> treatments, List<Map<String, String>> analyses, List<Map<String, String>> rendezv) {

        DatabaseReference databaseRef = FirebaseUtils.getDataReference();
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