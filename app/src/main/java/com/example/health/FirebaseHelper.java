
package com.example.health;

import com.example.health.model.Articles;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Articles> articless=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }


    //WRITE IF NOT NULL
    public Boolean save(Articles articles)
    {
        if(articles==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Articles").push().setValue(articles);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        articless.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Articles articles=ds.getValue(Articles.class);
            articless.add(articles);
        }
    }
    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Articles> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return articless;
    }
}
