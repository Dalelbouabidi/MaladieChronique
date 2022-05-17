package com.example.health;

import static com.example.health.Constant.USERS;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.health.model.Articles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ArticleAdminActivity extends AppCompatActivity {
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
    EditText nameEditTxt,descTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lv);
        db= FirebaseDatabase.getInstance().getReference(USERS).child("Articles");
        helper=new FirebaseHelper(db);
        adapter=new CustomAdapter(this,helper.retrieve());
        lv.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

    }

    private void displayInputDialog() {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);
        nameEditTxt= (EditText) d.findViewById(R.id.nameEditText);
        descTxt= (EditText) d.findViewById(R.id.descEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEditTxt.getText().toString();
                String desc=descTxt.getText().toString();

                //SET DATA
                Articles s=new Articles();
                s.setTitre(name);
                s.setDescription(desc);
                if(name != null && name.length()>0)
                {
                    //THEN SAVE
                    if(helper.save(s))
                    {
                        //IF SAVED CLEAR EDITXT
                        nameEditTxt.setText("");
                        descTxt.setText("");


                        adapter=new CustomAdapter(ArticleAdminActivity.this,helper.retrieve());
                        lv.setAdapter(adapter);


                    }
                }else
                {
                    Toast.makeText(ArticleAdminActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        d.show();
    }


}

