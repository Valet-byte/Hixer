package com.abyte.valet.testan40121.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.loading.LoadingDialog;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {
    private static Handler handler;
    public static final String MSG_NAME = "Msg";
    public static final String MSG_ID_BACK_FRAGMENT = "ID";
    public static final int R_CODE = 1;
    private static LoadingDialog dialog;
    private Integer id;


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
        handler = new Handler();
        RetrofitClient.startDownload();
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
                    RetrofitClient.getStatsByID(editable.toString());
                } catch (NumberFormatException ignore){
                    RetrofitClient.clearFindList();
                }

            }
        });

        text.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                navigationView.setVisibility(View.GONE);
                id = NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)))
                        .getGraph().getId();
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)))
                .navigate(R.id.findFragment);
            }
            else {
                text.setText("");
            }
        });
    }

    public static LoadingDialog getDialog() {
        return dialog;
    }

    public void stopSearch(View view){
        navigationView.setVisibility(View.VISIBLE);
        text.clearFocus();
        if (id != null) NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(id);
    }

    public static synchronized void runOnMainThread(Runnable action){
        if (action != null) handler.post(action);
    }
}