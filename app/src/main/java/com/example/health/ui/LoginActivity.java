package com.example.health.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.health.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    EditText email, motdepasse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        motdepasse = findViewById(R.id.motdepasse);
    }

    public void sinscrire(View view) {
        Intent sinscrire = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(sinscrire);
    }

    public void connexion(View view) {
        String Emailutilisateur = email.getText().toString();
        String Motdepasseutilisateur = motdepasse.getText().toString();
        if (TextUtils.isEmpty(Emailutilisateur)) {
            Toast.makeText(LoginActivity.this, "Entrez email utilisateur!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Emailutilisateur)) {
            email.setError("le champ ne peut pas etre vide");
        }if (!Emailutilisateur.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("entrez valide adresse email!");
        }

        if (TextUtils.isEmpty(Motdepasseutilisateur)) {
            motdepasse.setError("le champ ne peut pas etre vide");

        }
        if (Motdepasseutilisateur.length() < 6) {
            motdepasse.setError("mot de passe trop court,entrez au moins 6 caractères");
        }
         else {
            login(Emailutilisateur, Motdepasseutilisateur);
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent navigation = new Intent(LoginActivity.this, MainActivity.class);
                            navigation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(navigation);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}






