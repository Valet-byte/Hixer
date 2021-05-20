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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.fragments.FindFragment;
import com.abyte.valet.testan40121.loading.LoadingDialog;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String MSG_NAME = "Msg";
    public static final String MSG_ID_BACK_FRAGMENT = "ID";
    public static final String BUNDLE_RECYCLER_LAYOUT = "Position";
    public static final int R_CODE = 1;
    private LoadingDialog dialog;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private BottomNavigationView navigationView;

    public static Person person;
    private EditText text;

    public static void setPerson(Person person) {
        MainActivity.person = person;
    }

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
        RetrofitClient.startDownload(this);
        RetrofitClient.downloadIcon(person);
        Toolbar toolbar = findViewById(R.id.my_tool_bar);

        dialog = new LoadingDialog(this);

        this.setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.bottomNavigationView);

        NavController navController =
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)));
        NavigationUI.setupWithNavController(navigationView, navController);

        text = findViewById(R.id.et_text);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    RetrofitClient.getStatsByID(Long.decode(editable.toString()));
                } catch (NumberFormatException ignore){
                    RetrofitClient.clearFindList();
                }

            }
        });

        text.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                navigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fm_container, new FindFragment()).commit();
            }
            else {
                text.setText("");
            }
        });
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
    public LoadingDialog getDialog() {
        return dialog;
    }

    public void stopSearch(View view){
        navigationView.setVisibility(View.VISIBLE);
        text.clearFocus();
        try {
            getSupportFragmentManager().beginTransaction().detach(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.fm_container))).commit();
        } catch (Exception ignore){

        }

    }
}