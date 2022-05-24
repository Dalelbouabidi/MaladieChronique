package com.example.health.ui.calendar;

import static com.example.health.Constant.CHILD_CALENDAR;
import static com.example.health.FirebaseUtils.getDataReference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.health.R;
import com.example.health.model.TreatmentMedical;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TreatmentMedicalAdapter extends ArrayAdapter<TreatmentMedical> {
    public TreatmentMedicalAdapter(@NonNull Context context, List<TreatmentMedical> traitementsMedicals) {
        super(context, 0, traitementsMedicals);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TreatmentMedical treatmentMedical = getItem(position);
        DatabaseReference databaseRef = getDataReference();

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.traitement_cell, parent, false);

        TextView traitementCellTV = convertView.findViewById(R.id.traitementCellTV);

        String traitementMedicalTitle = treatmentMedical.getTreatmentName() + " :  " + treatmentMedical.getTreatmentDate() + "  à " + treatmentMedical.getTreatmentHour();
        traitementCellTV.setText(traitementMedicalTitle);
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
                        if (which == 0) {
                            databaseRef.child(CHILD_CALENDAR).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for (DataSnapshot data : snapshot.getChildren()) {
                                                    String key = data.getKey();
                                                    if (treatmentMedical.getCuid().equals(key)) {
                                                        data.getRef().removeValue();
                                                        Toast.makeText(getContext().getApplicationContext(), "traitement supprimer", Toast.LENGTH_LONG).show();
                                                    }
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
}
