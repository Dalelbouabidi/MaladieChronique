package com.example.health;

import static com.example.health.Constant.CHILD_ARTICLES;
import static com.example.health.Constant.DATA;
import static com.example.health.Constant.USERS;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    static public FirebaseUser getCurrentUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }

    static public DatabaseReference getUsersReference() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference(USERS);
    }

    static public DatabaseReference getUserReference() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = getCurrentUser();
        return firebaseDatabase.getReference(USERS).child(currentUser.getUid());
    }

    static public DatabaseReference getDataReference() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = getCurrentUser();
        return firebaseDatabase.getReference(DATA).child(currentUser.getUid());
    }

    static public DatabaseReference getDataArticlesReference() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference(DATA).child(CHILD_ARTICLES);
    }
}