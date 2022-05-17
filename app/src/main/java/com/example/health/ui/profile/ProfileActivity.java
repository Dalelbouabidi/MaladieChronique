package com.example.health.ui.profile;

import static com.example.health.Constant.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.health.R;
import com.example.health.model.UserModel;
import com.example.health.ui.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {
    private TextView nom, date, phone, mail, mot_passe, modifier;

    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawerlayout);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS).child(firebaseUser.getUid());
        nom = findViewById(R.id.nom);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        mot_passe = findViewById(R.id.mot_passe);
        modifier = findViewById(R.id.TVmodifier);
        chargeProfile();
    }

    public void chargeProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel != null) {
                    nom.setText(userModel.getNomdutilisateur());
                    date.setText(userModel.getDatedenaissance());
                    phone.setText(userModel.getTelephone());
                    mail.setText(firebaseUser.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void modifie(View view) {
        Intent i = new Intent(ProfileActivity.this, ModifierActivity.class);
        startActivity(i);
    }

    public void ClickProfile(View view) {
        recreate();
    }
}


