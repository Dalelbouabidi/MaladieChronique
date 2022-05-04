package com.example.health.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.health.R;
import com.example.health.model.TreatmentMedical;

import java.util.List;

public class TreatmentMedicalAdapter extends ArrayAdapter<TreatmentMedical> {
    public TreatmentMedicalAdapter(@NonNull Context context, List<TreatmentMedical> traitementsMedicals) {
        super(context, 0, traitementsMedicals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TreatmentMedical treatmentMedical = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.traitement_cell, parent, false);

        TextView traitementCellTV = convertView.findViewById(R.id.traitementCellTV);

        String traitementMedicalTitle = treatmentMedical.getTreatmentName() + " :  " + treatmentMedical.getTreatmentDate() + "  à " + treatmentMedical.getTreatmentHour();
        traitementCellTV.setText(traitementMedicalTitle);
        return convertView;
    }
}
