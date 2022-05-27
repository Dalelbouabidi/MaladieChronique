package com.example.health.ui.calendar;

import static com.example.health.Constant.CHILD_CALENDAR;
import static com.example.health.Constant.USERS;
import static com.example.health.FirebaseUtils.getDataReference;
import static com.example.health.ui.calendar.CalendarUtils.daysInMonthArray;
import static com.example.health.ui.calendar.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;
import com.example.health.model.TreatmentMedical;
import com.example.health.ui.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends BaseActivity implements CalendarAdapter.OnItemListener {


    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private DatabaseReference databaseReference;

    private List<TreatmentMedical> treatmentMedicalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_agenda);

        databaseReference = getDataReference();

        chargerData();
        initView();
        CalendarUtils.choisirDate = LocalDate.now();
        setMonthView();


    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.choisirDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.choisirDate);

        ArrayList<Pair<LocalDate, Integer>> data = new ArrayList<>();

        for (LocalDate day : daysInMonth) {
            int countTreatment = 0;
            if (day != null) {
                countTreatment = TreatmentMedical.getCountTreatmentByDate(treatmentMedicalList, day);
            }
            data.add(Pair.create(day, countTreatment));
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(data, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view) {
        CalendarUtils.choisirDate = CalendarUtils.choisirDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.choisirDate = CalendarUtils.choisirDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.choisirDate = date;
            setMonthView();
            nouvelleTraitementMedicalAction();
        }

    }

    public void nouvelleTraitementMedicalAction() {
        startActivity(new Intent(this, SemaineViewActivity.class));
    }

    private void chargerData() {
        databaseReference.child(CHILD_CALENDAR).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot children : snapshot.getChildren()) {
                    TreatmentMedical treatmentMedical = children.getValue(TreatmentMedical.class);
                    treatmentMedicalList.add(treatmentMedical);
                }

                setMonthView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CalendarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}