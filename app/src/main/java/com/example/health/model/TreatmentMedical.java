package com.example.health.model;

import com.example.health.ui.calendar.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TreatmentMedical {

    private String treatmentName;
    private String treatmentDate;
    private String treatmentHour;
    private String Cuid;

    public TreatmentMedical() { }

    public TreatmentMedical(String treatmentName, String treatmentDate, String treatmentHour, String Cuid) {
        this.treatmentName = treatmentName;
        this.treatmentDate = treatmentDate;
        this.treatmentHour = treatmentHour;
        this.Cuid = Cuid;
    }


    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentHour() {
        return treatmentHour;
    }

    public void setTreatmentHour(String treatmentHour) {
        this.treatmentHour = treatmentHour;
    }

    public String getCuid() {
        return Cuid;
    }

    public void setCuid(String cuid) {
        Cuid = cuid;
    }

    public static ArrayList<TreatmentMedical> getTreatmentMedicalByDate(List<TreatmentMedical> treatmentMedicalList, LocalDate localDate) {
        ArrayList<TreatmentMedical> tempTreatmentMedicalList = new ArrayList<>();
        String date = CalendarUtils.formattedDate(localDate);
        for (TreatmentMedical treatmentMedical : treatmentMedicalList) {
            if (treatmentMedical.getTreatmentDate().equals(date))
                tempTreatmentMedicalList.add(treatmentMedical);
        }

        return tempTreatmentMedicalList;
    }

    public static int getCountTreatmentByDate(List<TreatmentMedical> treatmentMedicalList, LocalDate localDate){
        int count = 0;
        String date = CalendarUtils.formattedDate(localDate);
        for (TreatmentMedical treatmentMedical : treatmentMedicalList) {
            if (treatmentMedical.getTreatmentDate().equals(date)) {
                count ++;
            }
        }
        return count;
    }
}
