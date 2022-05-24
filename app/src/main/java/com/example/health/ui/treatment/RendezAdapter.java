package com.example.health.ui.treatment;

import static com.example.health.Constant.CHILD_RENDEZVOUS;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.FirebaseUtils;
import com.example.health.R;
import com.example.health.model.RendezVous;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RendezAdapter extends RecyclerView.Adapter<RendezAdapter.TreatmentViewHolder> {
    private final ArrayList<RendezVous> data = new ArrayList<>();
    public RendezAdapter(List<RendezVous> data){
        this.data.clear();
        this.data.addAll(data);
    }
    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_rendez, parent, false);
        return new TreatmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder  holder, @SuppressLint("RecyclerView") int position) {

        final RendezVous rendezvous = data.get(position);
        holder.rendezNom.setText(rendezvous.getRendezvous());
        holder.mal1.setText(rendezvous.getMalade());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] options = new CharSequence[]{
                        "Effacer",
                        "Annuler",
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Effacer");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            delete(rendezvous.getRendezvous());
                        }
                    }
                });
                builder.show();
            }
        });

    }
    private void delete(String nomRendezVous) {
        DatabaseReference databaseRef = FirebaseUtils.getDataReference();
        Query query = databaseRef.child(CHILD_RENDEZVOUS);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("RendezAdapter", "onDataChange : snapshot = " + snapshot);
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        RendezVous rendezVous = snapshot2.getValue(RendezVous.class);
                        if(rendezVous != null && rendezVous.getRendezvous().equals(nomRendezVous)) {
                            snapshot2.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RendezAdapter", "onCancelled", error.toException());
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class TreatmentViewHolder extends RecyclerView.ViewHolder{

        public final TextView rendezNom;
        public final TextView mal1;

        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
            rendezNom = itemView.findViewById(R.id.rendez_nom);
            mal1 = itemView.findViewById(R.id.mal1);
        }
    }

}

