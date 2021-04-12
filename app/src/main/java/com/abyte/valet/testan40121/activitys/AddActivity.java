package com.abyte.valet.testan40121.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.abyte.valet.testan40121.file_util.RealPathUtil;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private LinkedList<ImageView> images;
    private LinkedList<File> files;
    private LinkedList<View> views;
    public static final String TAG = "MyTag";

    @Override
    public void onBackPressed() {
        RequestBody requestBody;
        MultipartBody.Part[] parts = new MultipartBody.Part[files.size()];
        String[] name = new String[views.size()];
        String[] texts = new String[views.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            parts[i] = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        }
        View view;
        EditText nameEt, textEt;
        for (int i = 0; i < views.size(); i++) {
            view = views.get(i);
            nameEt = view.findViewById(R.id.et_name);
            textEt = view.findViewById(R.id.et_text);
            name[i] = nameEt.getText().toString();
            texts[i] = textEt.getText().toString();
        }

        RequestBody body = RequestBody.create(MediaType.parse("imagee/*"), "Images");
        new Thread(() ->{
            RetrofitClient.uploadPhotos(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.i(TAG, "onResponse: " + response.code());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                }
            }, body, name, texts, parts);
        }).start();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        linearLayout = findViewById(R.id.sv_main);
        images = new LinkedList<>();
        files = new LinkedList<>();
        views = new LinkedList<>();
    }

    public void addView(View v) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.add_list_view, null, false);
        if (images.size() == 0 && views.size() == 0) {
            ImageView image = view.findViewById(R.id.iv_image);
            images.add(image);
            linearLayout.addView(view);
            views.add(view);
            image.setOnClickListener(v1 -> {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, images.indexOf(image));
            });

        } else {
            View view1 = views.get(views.size()-1);
            if (((EditText)view1.findViewById(R.id.et_name)).getText().toString().isEmpty()&&
                    ((EditText)view1.findViewById(R.id.et_text)).getText().toString().isEmpty()){
                Snackbar.make(v, "Заполните поля выше", BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else {
                ImageView image = view.findViewById(R.id.iv_image);
                images.add(image);
                linearLayout.addView(view);
                views.add(view);
                image.setOnClickListener(v1 -> {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    startActivityForResult(i, images.indexOf(image));
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data!= null){
            Uri _uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), _uri);
                images.get(requestCode).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            files.add(new File(RealPathUtil.getRealPath(this, _uri)));
        }
    }
}