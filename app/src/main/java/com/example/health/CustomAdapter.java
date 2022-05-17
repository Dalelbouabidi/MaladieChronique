package com.example.health;

import static com.example.health.Constant.USERS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.health.model.Articles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Articles> articless;
    DatabaseReference ref;

    public CustomAdapter(Context c, ArrayList<Articles> articless) {
        this.c = c;
        this.articless = articless;
    }

    @Override
    public int getCount() {
        return articless.size();
    }

    @Override
    public Object getItem(int pos) {
        return articless.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        TextView descTxt= (TextView) convertView.findViewById(R.id.descTxt);

        final Articles s= (Articles) this.getItem(position);

        nameTxt.setText(s.getTitre());
        descTxt.setText(s.getDescription());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPEN DETAIL
                openDetailActivity(s.getTitre(),s.getDescription());
            }
        });
        View finalConvertView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CharSequence[] options = new CharSequence[]{
                        "Effacer",
                        "Annuler",
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                builder.setTitle("Effacer");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            ref= FirebaseDatabase.getInstance().getReference(USERS).child("Articles");
                            ref.equalTo(String.valueOf(articless)).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                for (DataSnapshot data : snapshot.getChildren()){
                                                    String key = data.getKey();
                                                    ref.child(key).setValue(null);
                                                    Toast.makeText(c.getApplicationContext(), "article supprimer",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }

                                    });

                        }
                    }
                });
                builder.show();


                return true;
            }
        });

        return convertView;
    }

    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailActivity.class);
        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("DESC_KEY",details[1]);


        c.startActivity(i);
    }
    //OPEN DETAIL ACTIVITY




}
