package com.example.health.ui.calendar;

import static com.example.health.Constant.CALENDAR_TREATMENT_DATE;
import static com.example.health.Constant.CALENDAR_TREATMENT_HOUR;
import static com.example.health.Constant.CHILD_ANALYSES;
import static com.example.health.Constant.CHILD_CALENDAR;
import static com.example.health.Constant.CHILD_RENDEZVOUS;
import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.KEY_EXTRA_TITLE;
import static com.example.health.Constant.USERS;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.health.AlarmeReceiver;
import com.example.health.R;
import com.example.health.model.Analyse;
import com.example.health.model.RendezVous;
import com.example.health.model.Treatment;
import com.example.health.ui.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class TreatmentMedicalEditActivity extends BaseActivity {
    private String  TraitementMedicalDate, TraitementMedicalHeure;

    private static final String TAG = TreatmentMedicalEditActivity.class.getSimpleName();
    private final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TextView traitementMedicalDateTV;
    private Button traitementMedicalTimeTV;
    Spinner spinner;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    int heure, minute;
    private LocalTime temps;
    private CheckBox Alarme;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traitement_medicale_edit);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference(USERS).child(firebaseUser.getUid());
        temps = LocalTime.now();
        traitementMedicalDateTV.setText(CalendarUtils.formattedDate(CalendarUtils.choisirDate));
        Alarme = findViewById(R.id.alarme);
        initWidgets();

        spinner = (Spinner)findViewById(R.id.spinnerdata);
        list=new ArrayList<String >();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String traitemnt  = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Traitement: "+traitemnt,Toast.LENGTH_LONG).show();

            }
        });
        trouvedata();


    }

    private void trouvedata() {
        listener = databaseReference.child(CHILD_TREATMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata: snapshot.getChildren()) {
                    list.add(mydata.getValue(Treatment.class).toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentMedicalEditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        listener = databaseReference.child(CHILD_ANALYSES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata: snapshot.getChildren()) {
                    list.add(mydata.getValue(Analyse.class).toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentMedicalEditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        listener = databaseReference.child(CHILD_RENDEZVOUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata: snapshot.getChildren()) {
                    list.add(mydata.getValue(RendezVous.class).toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentMedicalEditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initWidgets() {
        drawerLayout = findViewById(R.id.drawerlayout);
        traitementMedicalTimeTV = findViewById(R.id.traitementMedicalTimeTV);
        traitementMedicalDateTV = findViewById(R.id.traitementMedicalDateTV);
    }


    public void enregistrerTraitementMedicalAction(View view) {
        TraitementMedicalDate = traitementMedicalDateTV.getText().toString();
        TraitementMedicalHeure = traitementMedicalTimeTV.getText().toString();

        if (TextUtils.isEmpty(TraitementMedicalDate)) {
            traitementMedicalDateTV.setError("Svp entrez votre date de Traitement");
        } else if (TextUtils.isEmpty(TraitementMedicalHeure)) {
            traitementMedicalTimeTV.setError("Svp entrez votre  heure de Traitement");
        } else {
            addDataToBase( TraitementMedicalDate, TraitementMedicalHeure);

        }

        startActivity(new Intent(TreatmentMedicalEditActivity.this, SemaineViewActivity.class));
        finish();

    }

    private void addDataToBase( String TraitementMedicalDate, String TraitementMedicalHeure) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(CALENDAR_TREATMENT_DATE, TraitementMedicalDate);
        hashMap.put(CALENDAR_TREATMENT_HOUR, TraitementMedicalHeure);

        databaseReference.child(CHILD_CALENDAR).push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TreatmentMedicalEditActivity.this, "les données sont ajoutées", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TreatmentMedicalEditActivity.this, "les données ne sont pas ajoutées", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Timepicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int choisirHeure, int choisirMinute) {
                heure = choisirHeure;
                minute = choisirMinute;

                temps = LocalTime.of(choisirHeure, choisirMinute);
                traitementMedicalTimeTV.setText(String.format(Locale.getDefault(), "%02d:%02d", heure, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, heure, minute, true);
        timePickerDialog.setTitle("Choisir Temps");
        timePickerDialog.show();

    }

    private void createAlarm(LocalDate date, LocalTime time, String titre) {

        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        String str = localDateTime.format(DATE_TME_FORMATTER);
        Timestamp timeStamp = Timestamp.valueOf(str);

        Log.d(TAG, "LocalDateTime = " + localDateTime);
        Log.d(TAG, "Timestamp = " + timeStamp);
        final int requestCode = 1337;
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmeReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_EXTRA_TITLE, titre);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeStamp.getTime(), pendingIntent);
    }
}