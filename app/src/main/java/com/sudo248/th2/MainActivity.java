package com.sudo248.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudo248.th2.fragment.InfoFragment;
import com.sudo248.th2.fragment.ListFragment;
import com.sudo248.th2.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);
        setupBottomNav();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SongActivity.class);
            startActivity(intent);
        });

    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.frgList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fcv, new ListFragment(), null).commit();
                    break;
                case R.id.frgInfo:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fcv, new InfoFragment(), null).commit();
                    break;
                case R.id.frgSearch:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fcv, new SearchFragment(), null).commit();
                    break;
            }
            return true;
        });
    }
}