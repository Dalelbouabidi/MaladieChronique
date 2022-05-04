package com.example.health.ui.treatment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.Treatment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.TreatmentViewHolder> {
    private String medicQuantity, medicName;
    private DatabaseReference databaseReference;
    Context context;
    private ArrayList<Treatment> data = new ArrayList<>();
    public TreatmentAdapter(List<Treatment> data){
        this.data.clear();
        this.data.addAll(data);
    }
    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_treatment, parent, false);
        return new TreatmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {

        final Treatment treatment = data.get(position);
        holder.medNom.setText(treatment.getMedicName());
        holder.medQuantity.setText(treatment.getMedicQuantity());
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
                            effacer( position, medicQuantity, medicName);

                        }
                    }



                });
                builder.show();

            }
        });
    }
    private void effacer(int position, String medicQuantity,String medicName) {
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference().child("Treatment");
        Query query =databaseReference.child(medicQuantity).child(medicName);
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

    protected static class TreatmentViewHolder extends RecyclerView.ViewHolder{

        public final TextView medNom;
        public final TextView medQuantity;

        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
            medNom = itemView.findViewById(R.id.medic_nom);
            medQuantity = itemView.findViewById(R.id.medic_quantity);
        }
    }

}
