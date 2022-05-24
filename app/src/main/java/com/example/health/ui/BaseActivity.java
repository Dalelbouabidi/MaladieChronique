package com.example.health.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.health.ui.article.ArticleActivity;
import com.example.health.ui.calendar.CalendarActivity;
import com.example.health.ui.malady.TypeMaladyActivity;
import com.example.health.ui.profile.ProfileActivity;
import com.example.health.ui.treatment.TreatmentActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickProfile(View view) {
        redirectActivity(this, ProfileActivity.class);
    }

    public void ClickAjouterTypeMaladie(View view) {
        redirectActivity(this, TypeMaladyActivity.class);
    }

    public void ClickConsulterTraitement(View view) {
        redirectActivity(this, TreatmentActivity.class);
    }

    public void ClickManageCalendar(View view) {
        redirectActivity(this, CalendarActivity.class);
    }


    public void ClickArticleMedical(View view) {
        redirectActivity(this, ArticleActivity.class);
    }

    public void ClickQuitter(View view) {
        quitter(this);
    }

    public  void quitter(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        String titleText = "Quitter";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#E831C9"));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setTitle(ssBuilder);
        builder.setMessage("Voulez-vous vraiment quitter");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF03DAC5"));
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FF03DAC5"));

    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
