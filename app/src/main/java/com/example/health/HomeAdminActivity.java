package com.example.health;

import static com.example.health.FirebaseUtils.getUserReference;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.health.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HomeAdminActivity extends AppCompatActivity {
    private TextView nom, date, phone, mail, mot_passe;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        databaseReference = getUserReference();
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
                if (userModel != null) {
                    nom.setText(userModel.getNomdutilisateur());
                    date.setText(userModel.getDatedenaissance());
                    phone.setText(userModel.getTelephone());
                    mail.setText(userModel.getEmail());
                    mot_passe.setText(userModel.getMotdepasse());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeAdminActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}