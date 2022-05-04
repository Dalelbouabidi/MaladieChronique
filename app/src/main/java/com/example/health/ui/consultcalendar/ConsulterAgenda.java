package com.example.health.ui.consultcalendar;

import android.os.Bundle;
import android.view.View;

import com.example.health.R;
import com.example.health.ui.BaseActivity;

public class ConsulterAgenda extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_agenda);
        drawerLayout= findViewById(R.id.drawerlayout);
    }


    public void ClickConsulterAgenda(View view){
       recreate();}


}