package com.example.health;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminActivity extends AppCompatActivity {
    CardView cardHome;
    CardView cardPerson;
    CardView cardArticle;
    CardView cardQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        cardHome = findViewById(R.id.cardHome);
        cardPerson = findViewById(R.id.cardPerson);
        cardArticle = findViewById(R.id.cardArticle);
        cardQuitter = findViewById(R.id.cardQuitter);
        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, HomeAdminActivity.class));


            }
        });
        cardPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, UserProfileActivity.class));

            }
        });
        cardArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, ArticleAdminActivity.class));
            }
        });
        cardQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Quitter");
                builder.setMessage("\"Voulez-vous vraiment quitter");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                        finish();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
