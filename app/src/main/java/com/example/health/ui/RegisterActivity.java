package com.example.health.ui;

import static com.example.health.FirebaseUtils.getCurrentUser;
import static com.example.health.FirebaseUtils.getUserReference;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.health.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText nomdutilisateur, datedenaissance, telephone, email, motdepasse;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mAuth = FirebaseAuth.getInstance();
        nomdutilisateur = findViewById(R.id.nomdutilisateur);
        datedenaissance = findViewById(R.id.datedenaissance);
        telephone = findViewById(R.id.telephone);
        email = findViewById(R.id.email);
        motdepasse = findViewById(R.id.motdepasse);
        datedenaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                datedenaissance.setText(date);

            }
        };
    }

    public void sinscrire(View view) {
        final String Nomdutlisateur = nomdutilisateur.getText().toString();
        final String DatedeNaissanceutilisateur = datedenaissance.getText().toString();
        final String Telephoneutilisateur = telephone.getText().toString();
        final String Emailutilisateur = email.getText().toString();
        final String Motdepasseutilisateur = motdepasse.getText().toString();


        if (TextUtils.isEmpty(Nomdutlisateur)) {
            nomdutilisateur.setError("le champ ne peut pas etre vide");
        }
        if (TextUtils.isEmpty(DatedeNaissanceutilisateur)) {
            datedenaissance.setError("entrez valide date de naissance!");
        }
        if (TextUtils.isEmpty(Telephoneutilisateur)) {
            telephone.setError("entrez valide numero de telephone!");
        }
        if (TextUtils.isEmpty(Emailutilisateur)) {
            email.setError("le champ ne peut pas etre vide");
        }
        if (!Emailutilisateur.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email.requestFocus();
            email.setError("entrez valide adresse email!");
        }

        if (TextUtils.isEmpty(Motdepasseutilisateur)) {
            motdepasse.setError("le champ ne peut pas etre vide");

        }
        if (Motdepasseutilisateur.length() < 6) {
            motdepasse.setError("mot de passe trop court,entrez au moins 6 caractères");
        }
        if (Telephoneutilisateur.length() < 8) {
            telephone.setError("le numéro de téléphone doit contenir 8 chiffre");
        } else {
            inscription(Nomdutlisateur, DatedeNaissanceutilisateur, Telephoneutilisateur, Emailutilisateur, Motdepasseutilisateur);

        }
    }

    private void inscription(String Nomdutlisateur, String DatedeNaissanceutilisateur,
                             String Telephoneutilisateur, String Emailutilisateur,
                             String Motdepasseutilisateur) {

        mAuth.createUserWithEmailAndPassword(Emailutilisateur, Motdepasseutilisateur)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = getCurrentUser();
                            String userUid = firebaseUser.getUid();
                            databaseReference = getUserReference();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("userUid", userUid);
                            hashMap.put("nomdutilisateur", Nomdutlisateur);
                            hashMap.put("datedenaissance", DatedeNaissanceutilisateur);
                            hashMap.put("telephone", Telephoneutilisateur);
                            hashMap.put("usertype", "0");
                            hashMap.put("email", Emailutilisateur);
                            hashMap.put("motdepasse", Motdepasseutilisateur);

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent navigation = new Intent(RegisterActivity.this, MainActivity.class);
                                        navigation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(navigation);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, (Objects.requireNonNull(task.getException())).getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, (Objects.requireNonNull(task.getException())).getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}









