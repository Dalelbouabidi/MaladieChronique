package com.example.health.ui.treatment;

import static com.example.health.Constant.CHILD_ANALYSES;

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
import com.example.health.model.Analyse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnalyseAdapter extends RecyclerView.Adapter<AnalyseAdapter.TreatmentViewHolder> {
    private final ArrayList<Analyse> data = new ArrayList<>();
    public AnalyseAdapter(List<Analyse> data){
        this.data.clear();
        this.data.addAll(data);
    }
    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_analyse, parent, false);
        return new TreatmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Analyse analyse = data.get(position);
        holder.analyseNom.setText(analyse.getAnalyse());
        holder.mal2.setText(analyse.getMaladie());
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
                            delete(analyse.getAnalyse());
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void delete(String nomAnalyse) {
        DatabaseReference databaseRef = FirebaseUtils.getDataReference();
        Query query = databaseRef.child(CHILD_ANALYSES);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("AnalyseAdapter", "onDataChange : snapshot = " + snapshot);
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        Analyse analyse = snapshot2.getValue(Analyse.class);
                        if(analyse != null && analyse.getAnalyse().equals(nomAnalyse)) {
                            snapshot2.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AnalyseAdapter", "onCancelled", error.toException());
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class TreatmentViewHolder extends RecyclerView.ViewHolder {

        public final TextView analyseNom;
        public final TextView mal2;
        private final RecyclerView rvAnalyses;

        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
            analyseNom = itemView.findViewById(R.id.analyse_nom);
            mal2 = itemView.findViewById(R.id.mal2);
            rvAnalyses = itemView.findViewById(R.id.rv_analyses);
        }
    }}


