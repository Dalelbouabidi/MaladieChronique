package com.example.health.ui.treatment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.RendezVous;

import java.util.ArrayList;
import java.util.List;

public class RendezAdapter extends RecyclerView.Adapter<RendezAdapter.TreatmentViewHolder> {
    private ArrayList<RendezVous> data = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {

        final RendezVous rendezvous = data.get(position);
        holder.rendezNom.setText(rendezvous.getRendezvous());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class TreatmentViewHolder extends RecyclerView.ViewHolder{

        public final TextView rendezNom;

        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
            rendezNom = itemView.findViewById(R.id.rendez_nom);
        }
    }

}

