package com.sudo248.th2.tablayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sudo248.th2.R;

/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 14:38 - 15/03/2023
 */
public class TabLayoutEx extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager(), 3);

        // optional
//        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
