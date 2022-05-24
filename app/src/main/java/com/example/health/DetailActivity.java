package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    TextView nameTxt,descTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        descTxt= (TextView) findViewById(R.id.descDetailTxt);
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String desc=i.getExtras().getString("DESC_KEY");
        nameTxt.setText(name);
        descTxt.setText(desc);

    }
}