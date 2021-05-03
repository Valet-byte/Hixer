package com.abyte.valet.testan40121.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String MSG_NAME = "Msg";
    public static final String MSG_POS = "Position";
    public static final String MSG_ID_BACK_FRAGMENT = "ID";
    public static final int R_CODE = 1;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static Person person;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(R_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        person =  (Person) getIntent().getSerializableExtra(MSG_NAME);
        RetrofitClient.startDownload( this);

        Toolbar toolbar = findViewById(R.id.my_tool_bar);

        this.setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);



        NavController navController =
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)));
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}