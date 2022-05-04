package com.example.health.ui.article;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.example.health.R;
import com.example.health.ui.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ArticleActivity extends BaseActivity {
    ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles = new String[]{"Maladie Chronique", "Conseil"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        drawerLayout = findViewById(R.id.drawerlayout);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);

        viewPager2.setAdapter(viewPagerFragmentAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) ->tab.setText(titles[position]))).attach();



    }

    public void ClickArticleMedical(View view) {
        recreate();
    }

}