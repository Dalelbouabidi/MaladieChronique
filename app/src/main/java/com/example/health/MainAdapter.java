package com.example.health;

import static com.example.health.Constant.USERS;
import static com.example.health.FirebaseUtils.getUserReference;
import static com.example.health.FirebaseUtils.getUsersReference;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private static String TAG = MainAdapter.class.getSimpleName();
    Context context;
    ArrayList<UserModel> list;

    public MainAdapter(Context context,ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel userModel = list.get(position);
        holder.nametext.setText(userModel.getNomdutilisateur());
        holder.nametext1.setText(userModel.getDatedenaissance());
        holder.nametext2.setText(userModel.getTelephone());
       // holder.nametext3.setText(userModel.getEmail());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseRef = getUsersReference();
                databaseRef.addListenerForSingleValueEvent(
                      new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              if (snapshot.exists()){
                                  for (DataSnapshot data : snapshot.getChildren()){
                                      String key = data.getKey();
                                      if(userModel.getUserId().equals(key)) {
                                          data.getRef().removeValue();
                                          Toast.makeText(context.getApplicationContext(), "Patient supprimer", Toast.LENGTH_LONG).show();
                                      }
                                  }
                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {

                          }

                      });
            }
        });


    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nametext ,nametext1,nametext2,nametext3;
        Button delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.nametext);
            nametext1 = itemView.findViewById(R.id.nametext1);
            nametext2 = itemView.findViewById(R.id.nametext2);
            nametext3 = itemView.findViewById(R.id.nametext3);
           delete = itemView.findViewById(R.id.btndelete);

        }
    }
}
