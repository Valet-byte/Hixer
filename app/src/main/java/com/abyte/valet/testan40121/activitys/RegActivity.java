package com.abyte.valet.testan40121.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends AppCompatActivity {
    private EditText login, password;

    private RetrofitClient client;
    private MessageDigest digest;
    {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        login.setText("");
        password.setText("");
        if (resultCode != 2) finish();
    }

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
Person person = new Person(330L ,login.getText().toString(), password.getText().toString());
                Intent i = new Intent(RegActivity.this, MainActivity.class);
                i.putExtra(MainActivity.MSG_NAME, person);
                startActivityForResult(i, MainActivity.R_CODE);
                StringBuilder builder = new StringBuilder();

                byte[] a = digest.digest(password.getText().toString().getBytes());
                for (byte b : a) {
                    builder.append(b);
                }
                Log.i("MyTag", builder.toString());

                client.findUser(new Callback<Person>() {
                    @Override
                    public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                        runOnUiThread(() -> {
                            Person person = response.body();
                            db.addPerson(person);
                            Intent i = new Intent(RegActivity.this, MainActivity.class);
                            i.putExtra(MainActivity.MSG_NAME, person);
                            startActivityForResult(i, MainActivity.R_CODE);
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<Person> call, @Nullable Throwable t) {
                        Snackbar.make(login, t.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }, login.getText().toString(), builder.toString());
            } else {
                Snackbar.make(login, "Введены некорректные данные", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        Person p = db.getPerson();
        if (p != null) {// если в бд что-то есть
            Log.i("MyTag", p.toString());

            client.findUser(new Callback<Person>() {
                @Override
                public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                    runOnUiThread(() -> {
                        Person person = response.body();
                        Intent i = new Intent(RegActivity.this, MainActivity.class);
                        i.putExtra(MainActivity.MSG_NAME, person);
                        startActivityForResult(i, MainActivity.R_CODE);
                    });

                }

                @Override
                public void onFailure(@NonNull Call<Person> call, @Nullable Throwable t) {
                    Snackbar.make(login, t.getMessage(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();
                }

            }, p.getName(), p.getPassword());
        } else {
            Log.i("MyTag", "null");
        }
    }
}
