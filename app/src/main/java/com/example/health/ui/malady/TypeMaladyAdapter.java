package com.example.health.ui.malady;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.TypeMalady;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TypeMaladyAdapter extends RecyclerView.Adapter<TypeMaladyAdapter.TreatmentViewHolder> {
    private DatabaseReference databaseReference;
   String nameMalady, nameDoctor;
    private final ArrayList<TypeMalady> data = new ArrayList<>();

    public TypeMaladyAdapter(List<TypeMalady> data) {

        this.data.clear();
        this.data.addAll(data);
    }

    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_malady, parent, false);
        return new TreatmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {

        final TypeMalady typeMalady = data.get(position);
        holder.maladyNom.setText(typeMalady.getNameMalady());
        holder.maladyMed.setText(typeMalady.getNameDoctor());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CharSequence options[] = new CharSequence[]{
                       "Effacer",
                       "Anunuler",
               };
               AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
               builder.setTitle("Effacer");
               builder.setItems(options, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       if (which == 0){
                           effacer( position,nameMalady,nameDoctor);
                       }
                   }



               });
               builder.show();
           }
       });

        }
    private void effacer(int position, String nameMalady, String nameDoctor) {
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference().child("TypeMalady");
        Query query =databaseReference.child( nameMalady).child(nameDoctor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class TreatmentViewHolder extends RecyclerView.ViewHolder {

        public final TextView maladyNom;
        public final TextView maladyMed;

        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
            maladyNom = itemView.findViewById(R.id.tv_malady_nom);
            maladyMed = itemView.findViewById(R.id.tv_malady_med);
        }
    }


}
