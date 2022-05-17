package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {
    TextView nameTxt,descTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        descTxt= (TextView) findViewById(R.id.descDetailTxt);
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String desc=i.getExtras().getString("DESC_KEY");
        nameTxt.setText(name);
        descTxt.setText(desc);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}

