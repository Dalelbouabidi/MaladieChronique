package com.example.health.ui.profile;

import static com.example.health.Constant.USERS;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    private EditText nom, date, phone, mail, mot_passe;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawerlayout);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS).child(firebaseUser.getUid());
        nom = findViewById(R.id.nom);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        mot_passe = findViewById(R.id.mot_passe);
        chargeProfile();
    }

    public void chargeProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                assert userModel != null;
                nom.setText(userModel.getNomdutilisateur());
                date.setText(userModel.getDatedenaissance());
                phone.setText(userModel.getTelephone());
                mail.setText(userModel.getEmail());
                mot_passe.setText(userModel.getMotdepasse());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void modifier(View view) {
        databaseReference.child("telephone").setValue(phone.getText().toString());
        databaseReference.child("motdepasse").setValue(mot_passe.getText().toString());
        databaseReference.child("email").setValue(mail.getText().toString());

        Toast.makeText(this, "les données ont été modifiées", Toast.LENGTH_LONG).show();

    }

    public void ClickProfile(View view) {
        recreate();
    }

}


