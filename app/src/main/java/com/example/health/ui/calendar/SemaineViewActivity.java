
package com.example.health.ui.calendar;

import static com.example.health.Constant.CHILD_CALENDAR;
import static com.example.health.Constant.USERS;
import static com.example.health.FirebaseUtils.getDataReference;
import static com.example.health.ui.calendar.CalendarUtils.daysInWeekArray;
import static com.example.health.ui.calendar.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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

public class SemaineViewActivity extends BaseActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView traitementMedicalListView;


    private DatabaseReference databaseReference;

    List<TreatmentMedical> treatmentMedicalList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semaine_view);

        databaseReference = getDataReference();


        initWidgets();
        setSemaineView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerData();
    }

    private void initWidgets() {
        drawerLayout = findViewById(R.id.drawerlayout);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        traitementMedicalListView = findViewById(R.id.traitementMedicalListView);


    }




    public void previousWeekAction(View view) {
        CalendarUtils.choisirDate = CalendarUtils.choisirDate.minusWeeks(1);
        setSemaineView();
    }

    private void setSemaineView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.choisirDate));
        ArrayList<LocalDate> daysInMonth = daysInWeekArray(CalendarUtils.choisirDate);

        ArrayList<Pair<LocalDate, Integer>>  data = new ArrayList<>();

        for (LocalDate day: daysInMonth) {
            int countTreatment = 0;
            if (day != null) {
                countTreatment = TreatmentMedical.getCountTreatmentByDate(treatmentMedicalList, day);
            }
            data.add(Pair.create(day, countTreatment));
        }
        CalendarAdapter calendarAdapter = new CalendarAdapter(data, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setTraitementMedicalAdapter();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.choisirDate = CalendarUtils.choisirDate.plusWeeks(1);
        setSemaineView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.choisirDate = date;
        setSemaineView();
    }

    private void setTraitementMedicalAdapter() {
        List<TreatmentMedical> localTreatmentMedicalList = TreatmentMedical.getTreatmentMedicalByDate(treatmentMedicalList, CalendarUtils.choisirDate);
        TreatmentMedicalAdapter treatmentMedicalAdapter = new TreatmentMedicalAdapter(this, localTreatmentMedicalList);
        traitementMedicalListView.setAdapter(treatmentMedicalAdapter);

    }




    private void chargerData() {
        databaseReference.child(CHILD_CALENDAR).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot children : snapshot.getChildren()) {
                    TreatmentMedical treatmentMedical = children.getValue(TreatmentMedical.class);
                    treatmentMedical.setCuid(children.getKey());
                    treatmentMedicalList.add(treatmentMedical);
                }
                setSemaineView();
                setTraitementMedicalAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SemaineViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void nouvelleTraitementMedicalAction(View view) {
        redirectActivity(this, TreatmentMedicalEditActivity.class);
    }

    public void ClickManageCalendar(View view) {
        recreate();
    }


}