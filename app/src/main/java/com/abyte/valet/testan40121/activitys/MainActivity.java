package com.abyte.valet.testan40121.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.abyte.valet.testan40121.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "Msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.my_tool_bar);

        this.setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);



        navigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
                switch (item.getItemId()){
                    case R.id.projectsFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.projectsFragment2);
                        break;
                    case R.id.ideaFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.ideaFragment2);
                        break;
                    case R.id.articleFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.articleFragment2);
                        break;
                    case R.id.personalFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.personalFragment2);
                        break;

                }
                return true;
            }
        );
        
    }

}