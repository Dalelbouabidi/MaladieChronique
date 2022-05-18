
package com.example.health;

import static com.example.health.Constant.CHILD_ARTICLES;
import static com.example.health.FirebaseUtils.getDataReference;

import com.example.health.model.Articles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {
    DatabaseReference databaseRef = getDataReference().child(CHILD_ARTICLES);
    Boolean saved = null;
    ArrayList<Articles> articless = new ArrayList<>();


    //WRITE IF NOT NULL
    public Boolean save(Articles articles) {
        if (articles == null) {
            saved = false;
        } else {
            try {
                databaseRef.push().setValue(articles);
                saved = true;

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        articless.clear();

        for (DataSnapshot data : dataSnapshot.getChildren()) {
            Articles articles = data.getValue(Articles.class);
            articles.setUid(dataSnapshot.getKey());
            articless.add(articles);
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS

}
