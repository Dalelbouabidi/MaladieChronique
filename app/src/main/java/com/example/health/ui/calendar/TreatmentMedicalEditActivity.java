package com.example.health.ui.calendar;

import static com.example.health.Constant.CALENDAR_TREATMENT_DATE;
import static com.example.health.Constant.CALENDAR_TREATMENT_HOUR;
import static com.example.health.Constant.CALENDAR_TREATMENT_NAME;
import static com.example.health.Constant.CHILD_ANALYSES;
import static com.example.health.Constant.CHILD_CALENDAR;
import static com.example.health.Constant.CHILD_RENDEZVOUS;
import static com.example.health.Constant.CHILD_TREATMENTS;
import static com.example.health.Constant.KEY_EXTRA_TITLE;

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
import com.example.health.FirebaseUtils;
import com.example.health.R;
import com.example.health.model.Analyse;
import com.example.health.model.RendezVous;
import com.example.health.model.Treatment;
import com.example.health.ui.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    private static final String TAG = TreatmentMedicalEditActivity.class.getSimpleName();

    private final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TextView traitementMedicalDateTV;
    private Button traitementMedicalTimeTV;
    Spinner spinner;
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int heure, minute;
    private LocalTime temps;
    private CheckBox Alarme;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traitement_medicale_edit);
        initWidgets();

        databaseReference = FirebaseUtils.getDataReference();

        temps = LocalTime.now();
        traitementMedicalDateTV.setText(CalendarUtils.formattedDate(CalendarUtils.choisirDate));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String traitement = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Traitement: " + traitement, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchData();

    }

    private void fetchData() {
        databaseReference.child(CHILD_TREATMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        Treatment treatment = snapshot2.getValue(Treatment.class);
                        if (treatment != null) {
                            list.add( "Médicament : "+ "          "  +treatment.getMedicName() + "             " +treatment.getMedicQuantity());

                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentMedicalEditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child(CHILD_ANALYSES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren()) {
                    for (DataSnapshot mydata1 : mydata.getChildren()){
                        Analyse analyse = mydata1.getValue(Analyse.class);
                        if (analyse !=null){
                            list.add("Analyse :"+"                 "+analyse.getAnalyse());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TreatmentMedicalEditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        databaseReference.child(CHILD_RENDEZVOUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    for (DataSnapshot data1 :data.getChildren()){
                        RendezVous rendezVous = data1.getValue(RendezVous.class);
                        if (rendezVous !=null){
                            list.add("RendezVous : "+"               "+rendezVous.getRendezvous());
                        }
                    }
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
        Alarme = findViewById(R.id.alarme);
        spinner = findViewById(R.id.spinnerdata);
    }


    public void enregistrerTraitementMedicalAction(View view) {
        String treatmentMedicalDate = traitementMedicalDateTV.getText().toString();
        String treatmentMedicalTime = traitementMedicalTimeTV.getText().toString();
        String treatmentMedicalNom = spinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(treatmentMedicalDate)) {
            traitementMedicalDateTV.setError("Svp entrez votre date de Traitement");
        } else if (TextUtils.isEmpty(treatmentMedicalTime)) {
            traitementMedicalTimeTV.setError("Svp entrez votre  heure de Traitement");
        } else {
            addDataToBase(treatmentMedicalNom, treatmentMedicalDate, treatmentMedicalTime);
        }

        if (Alarme.isChecked()) {
            createAlarm(CalendarUtils.choisirDate, temps, treatmentMedicalNom);
        }
        startActivity(new Intent(TreatmentMedicalEditActivity.this, SemaineViewActivity.class));
        finish();

    }

    private void addDataToBase(String treatmentMedicalNom, String treatmentMedicalDate, String treatmentMedicalTime) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(CALENDAR_TREATMENT_NAME, treatmentMedicalNom);
        hashMap.put(CALENDAR_TREATMENT_DATE, treatmentMedicalDate);
        hashMap.put(CALENDAR_TREATMENT_HOUR, treatmentMedicalTime);

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