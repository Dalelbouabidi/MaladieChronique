
package com.example.health.ui.article;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.Articles;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.MyViewHolder>{
    Context context;
    ArrayList<Articles> list;

    public AdapterMain(Context context,ArrayList<Articles> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterMain.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model, parent, false);
        return new AdapterMain.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMain.MyViewHolder holder, int position) {
       Articles articles = list.get(position);
        holder.nameTxt.setText(articles.getTitre());
        holder. descTxt.setText(articles.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(articles.getTitre(),articles.getDescription());
            }
        });


    }

    private void openDetailActivity(String...details) {
        Intent i = new Intent(context, detail.class);
        i.putExtra("NAME_KEY", details[0]);
        i.putExtra("DESC_KEY", details[1]);

        context.startActivity(i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt,descTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            descTxt = itemView.findViewById(R.id.descTxt);

        }
    }

}