package com.abyte.valet.testan40121.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.person.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegActivity extends AppCompatActivity {

    private EditText login, password;

    private RetrofitClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        client = new RetrofitClient();
        login = findViewById(R.id.et_login);
        password = findViewById(R.id.et_password);
        PersonDB db = new PersonDB(this);

        findViewById(R.id.appCompatButton).setOnClickListener(v -> {
            if (!login.getText().toString().isEmpty() &&
                    !password.getText().toString().isEmpty()) {


                client.findUser(new Callback<Person>() {
                    @Override
                    public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                        runOnUiThread(() ->{
                            Person person = response.body();
                            Intent i = new Intent(RegActivity.this, MainActivity.class);
                            i.putExtra(MainActivity.MSG_NAME, person);
                            startActivity(i);
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<Person> call, @Nullable Throwable t) {
                        Snackbar.make(login, t.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }, login.getText().toString(), password.getText().toString());
            } else {
                Snackbar.make(login, "Введены некорректные данные", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

            Person p = db.getPerson();
            if (p != null) {
         // если в бд что-то есть
         Log.i("MyTag", p.toString());

         client.findUser(new Callback<Person>() {
             @Override
             public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                 runOnUiThread(() ->{
                     Person person = response.body();
                     Toast.makeText(getApplicationContext(), response.code() + "", Toast.LENGTH_LONG).show();
                     Intent i = new Intent(RegActivity.this, MainActivity.class);
                     i.putExtra(MainActivity.MSG_NAME, person);
                     startActivity(i);
                 });

             }

             @Override
             public void onFailure(@NonNull Call<Person> call, @Nullable Throwable t) {
                 Snackbar.make(login, t.getMessage(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();
             }

         }, p.getName(), p.getPassword());




     }
    }
}
