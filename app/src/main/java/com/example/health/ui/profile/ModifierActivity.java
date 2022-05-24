package com.example.health.ui.profile;

import static com.example.health.Constant.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ModifierActivity extends BaseActivity {
    private DatabaseReference databaseReference;
    EditText phone, password;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
        drawerLayout = findViewById(R.id.drawerlayout);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.mot_passe);
        save = findViewById(R.id.save);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS).child(firebaseUser.getUid());
        charger();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("telephone").setValue(phone.getText().toString());
                databaseReference.child("motdepasse").setValue(password.getText().toString());
                Toast.makeText(ModifierActivity.this, "les données ont été modifiées", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ModifierActivity.this, ProfileActivity.class));
            }

        });
    }

    private void charger() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel != null){
                    phone.setText(userModel.getTelephone());
                    password.setText(userModel.getMotdepasse());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ModifierActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ClickProfile(View view) {
        recreate();
    }

}