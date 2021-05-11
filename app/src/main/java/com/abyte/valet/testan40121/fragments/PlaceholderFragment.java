package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.file_util.RealPathUtil;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abyte.valet.testan40121.activitys.AddActivity.TAG;

public class PlaceholderFragment extends Fragment {
    private EditText login, password;
    private PersonDB db;
    private MessageDigest digest;
    private static File icon;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String type = getArguments().getString(MainActivity.MSG_NAME);
        View view;
        if (type.equals("Регистрация")) view = inflater.inflate(R.layout.fragment_placeholder_registration, container, false);
        else view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        AppCompatButton button = view.findViewById(R.id.appCompatButton);

        db = new PersonDB(getActivity());
        login = view.findViewById(R.id.et_login);
        password = view.findViewById(R.id.et_password);
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (type.equals("Регистрация")){ button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_registration));
            button.setOnClickListener((v)->{
                if (!login.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty()) {
                    StringBuilder builder = new StringBuilder();

                    byte[] a = digest.digest(password.getText().toString().getBytes());
                    for (byte b : a) {
                        builder.append(b);
                    }
                    Log.i("MyTag", builder.toString());

                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), icon);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", icon.getName(), body);
                    RequestBody bodyInfo = RequestBody.create(MediaType.parse("file/*"), icon.getName());
                    Log.i(TAG, "onCreateView: " + bodyInfo.toString());
                    RetrofitClient.registrationUser(new Callback<Person>() {
                        @Override
                        public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                            getActivity().runOnUiThread(() -> {
                                Person person = response.body();
                                if (person != null) {
                                    db.addPerson(person, icon.getName());
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    i.putExtra(MainActivity.MSG_NAME, person);
                                    RetrofitClient.startDownload( getActivity());
                                    RetrofitClient.startDownloadByUserID(person.getId(), getActivity());

                                    RetrofitClient.uploadIcon(body, part);
                                    startActivityForResult(i, MainActivity.R_CODE);

                                }
                            });
                        }
                        @Override
                        public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                            Snackbar.make(login, Objects.requireNonNull(t.getMessage()), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }, login.getText().toString(), builder.toString(), bodyInfo, part);
                } else {
                    Snackbar.make(login, "Введены некорректные данные", BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
            login.setText("");
            password.setText("");
        }

        else {Person p = db.getPerson();
            if (p != null) {// если в бд что-то есть
                Log.i("MyTag", p.toString());

                RetrofitClient.findUser(new Callback<Person>() {
                    @Override
                    public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                        getActivity().runOnUiThread(() -> {
                            Person person = response.body();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.putExtra(MainActivity.MSG_NAME, person);
                            RetrofitClient.startDownload( getActivity());
                            RetrofitClient.startDownloadByUserID(person.getId(), getActivity());
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
            button.setOnClickListener((v)->{
                if (!login.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty()) {
                    StringBuilder builder = new StringBuilder();

                    byte[] a = digest.digest(password.getText().toString().getBytes());
                    for (byte b : a) {
                        builder.append(b);
                    }
                    Log.i("MyTag", builder.toString());

                    RetrofitClient.findUser(new Callback<Person>() {
                        @Override
                        public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                            getActivity().runOnUiThread(() -> {
                                Person person = response.body();
                                if (person != null) {
                                    db.addPerson(person, person.getPhotoName());
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    i.putExtra(MainActivity.MSG_NAME, person);
                                    RetrofitClient.startDownload( getActivity());
                                    RetrofitClient.startDownloadByUserID(person.getId(), getActivity());
                                    startActivityForResult(i, MainActivity.R_CODE);

                                }
                            });
                        }

                        @Override
                        public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                            Snackbar.make(login, Objects.requireNonNull(t.getMessage()), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }, login.getText().toString(), builder.toString());
                    login.setText("");
                    password.setText("");
                } else {
                    Snackbar.make(login, "Введены некорректные данные", BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }


        return view;
    }

    public static PlaceholderFragment newInstance(String type) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MSG_NAME, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void setIcon(Uri icon, Context context) {
        PlaceholderFragment.icon = new File(RealPathUtil.getRealPath(context, icon));
    }
}