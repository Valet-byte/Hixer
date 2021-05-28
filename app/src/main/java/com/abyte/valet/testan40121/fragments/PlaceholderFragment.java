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
import com.abyte.valet.testan40121.activitys.RegActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.file_util.RealPathUtil;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                        !password.getText().toString().isEmpty() && icon != null) {
                    StringBuilder builder = new StringBuilder();

                    ((RegActivity) getActivity()).getDialog().startDialog();
                    Log.i(TAG, "onCreateView: pass " + password.getText().toString());
                    Log.i(TAG, "onCreateView: login " + login.getText().toString());

                    byte[] a = digest.digest(password.getText().toString().getBytes());
                    for (byte b : a) {
                        builder.append(b);
                    }
                    Log.i("MyTag", builder.toString());

                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), icon);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", icon.getName(), body);
                    RequestBody bodyInfo = RequestBody.create(MediaType.parse("file/*"), icon.getName());
                    Log.i(TAG, "onCreateView: " + bodyInfo.toString());

                    RegActivity.registration(getActivity(), db,
                            bodyInfo, part, login.getText().toString(), builder.toString(), login);

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

                ((RegActivity) getActivity()).getDialog().startDialog();


                RetrofitClient.findUser(new Callback<Person>() {
                    @Override
                    public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                        getActivity().runOnUiThread(() -> {
                            Person person = response.body();
                            ((RegActivity) getActivity()).getDialog().stopDialog();
                            if (person != null) {
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                MainActivity.setPerson(person);
                                RetrofitClient.startDownload();
                                RetrofitClient.startDownloadByUserID(person.getId());
                                startActivityForResult(i, MainActivity.R_CODE);
                            }
                        });

                    }

                    @Override
                    public void onFailure(@NonNull Call<Person> call, @Nullable Throwable t) {
                        ((RegActivity) getActivity()).getDialog().stopDialog();
                        Snackbar.make(login, "Произошла неизвестная ошибка", BaseTransientBottomBar.LENGTH_LONG).show();
                        Log.i(TAG, "onFailure: " + t.getMessage());
                    }

                }, p.getName(), p.getPassword());
            } else {
                Log.i("MyTag", "null");
            }
            button.setOnClickListener((v)->{
                if (!login.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty()) {

                    Log.i(TAG, "onCreateView: pass " + password.getText().toString());
                    Log.i(TAG, "onCreateView: login " + login.getText().toString());

                    StringBuilder builder = new StringBuilder();
                    ((RegActivity) getActivity()).getDialog().startDialog();
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
                                ((RegActivity) getActivity()).getDialog().stopDialog();
                                if (person != null) {
                                    db.addPerson(person, person.getPhotoName());
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    MainActivity.setPerson(person);
                                    RetrofitClient.startDownload();
                                    RetrofitClient.startDownloadByUserID(person.getId());
                                    startActivityForResult(i, MainActivity.R_CODE);

                                }
                            });
                        }

                        @Override
                        public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                            ((RegActivity) getActivity()).getDialog().stopDialog();
                            Snackbar.make(login, "Неверный логин или пароль", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }, "\"" +login.getText().toString() + "\"", "\"" +builder.toString() + "\"");
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