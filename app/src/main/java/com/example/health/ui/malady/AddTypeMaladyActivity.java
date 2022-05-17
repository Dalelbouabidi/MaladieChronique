package com.example.health.ui.malady;

import static com.example.health.Constant.CHILD_TYPE_MALADY;
import static com.example.health.Constant.NAME_DOCTOR;
import static com.example.health.Constant.NAME_MALADY;
import static com.example.health.Constant.USERS;
import static com.example.health.FirebaseUtils.getDataReference;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.health.R;
import com.example.health.ui.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTypeMaladyActivity extends BaseActivity {
    String[] maladie = {"Diabète", "Dysthyroidies", "Affections hypophysaires","Affections surrénaliennes",
            "HTA sévère", "Cardiopathies congénitales et valvulopathies","Sclérose en plaques",
            "Affections coronariennes et leurs complications", "Phlébites", "Tuberculose active",
            "Insuffisance respiratoire chronique","Insuffisance cardiaque et troubles du rythme" ,
            "Epilepise", "Maladie de Parkinson", "Psychoses et névroses", "Insuffisance rénale chronique"
            , "Rhumatismes inflamatoires chronique", "Maladies auto-immunes",
            "Tumeurs et hémopathies malignes", "Maladies inflamatoires de l'intestin",
            "Hepatites chroniques actives"};


    EditText NomMedecin;
    Button ajouter;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterMaladie;
    private DatabaseReference databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        databaseRef = getDataReference();
        drawerLayout = findViewById(R.id.drawerlayout);
        NomMedecin = findViewById(R.id.NomMedecin);
        ajouter = findViewById(R.id.ajouterbtn);
        autoCompleteTextView=findViewById(R.id.NomMaladie);
        adapterMaladie = new ArrayAdapter<String>(this,R.layout.list_item,maladie);
        autoCompleteTextView.setAdapter(adapterMaladie);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long id) {
                String maladie  = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Maladie: "+maladie,Toast.LENGTH_LONG).show();
            }
        });



        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomMalady = autoCompleteTextView.getText().toString();
                String nomMed = NomMedecin.getText().toString();
                if (TextUtils.isEmpty(nomMalady)) {
                    autoCompleteTextView.setError("Svp entrez votre nom de maladie");
                }
                if (TextUtils.isEmpty(nomMed)) {
                    NomMedecin.setError("Svp entrez votre nom de medecin");
                } else {
                    addDataToDatabase(nomMalady, nomMed);
                    Intent i = new Intent(AddTypeMaladyActivity.this, TypeMaladyActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void addDataToDatabase(String nameMalady, String nameMed) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put(NAME_MALADY, nameMalady);
        hashMap.put(NAME_DOCTOR, nameMed);
        databaseRef.child(CHILD_TYPE_MALADY).push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTypeMaladyActivity.this, "les données sont ajoutées", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTypeMaladyActivity.this, "les données ne sont pas ajoutées", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}