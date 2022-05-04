package com.example.health.ui.treatment;

import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.MED_NOM;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.health.model.Treatment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.TreatmentViewHolder> {
    private static String TAG = TreatmentAdapter.class.getSimpleName();
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
                            delete(treatment.getMedicName());
                        }
                    }
                });
                builder.show();

            }
        });
    }
    private void delete(String medicName) {
        DatabaseReference databaseRef = FirebaseUtils.getDatabaseReference();
        Query query = databaseRef.child(CHILD_TREATMENTS);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange : snapshot = " + snapshot.toString());
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        Treatment treatment = snapshot2.getValue(Treatment.class);
                        if(treatment != null && treatment.getMedicName().equals(medicName)) {
                            snapshot2.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
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
