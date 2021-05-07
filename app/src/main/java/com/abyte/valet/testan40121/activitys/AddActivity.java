package com.abyte.valet.testan40121.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.abyte.valet.testan40121.file_util.RealPathUtil;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private LinkedList<ImageView> images;
    private Map <Integer,File> files;
    private LinkedList<View> views;
    private LinkedList<ServerModel> models;
    public static final String TAG = "MyTag";
    private Integer type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        models = new LinkedList<>();
        linearLayout = findViewById(R.id.sv_main);
        images = new LinkedList<>();
        files = new HashMap<>();
        views = new LinkedList<>();
        type = getIntent().getIntExtra(MainActivity.MSG_NAME, 1);
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

            files.put(requestCode, new File(RealPathUtil.getRealPath(this, _uri)));
        }
    }

    public void uploadToServer(View v) {
        if (views.size() >= 3){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), MainActivity.person.toString());
            ArrayList<MultipartBody.Part> parts = new ArrayList<>();
            String name;
            String texts ;
            View view;
            EditText nameEt, textEt;
            for (int i = 0; i < views.size(); i++) {
                view = views.get(i);
                nameEt = view.findViewById(R.id.et_name);
                textEt = view.findViewById(R.id.et_text);
                name = nameEt.getText().toString();
                texts = textEt.getText().toString();
                if (files.containsKey(i)){
                    File file = files.get(i);
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
                    parts.add(part);
                    models.add(new ServerModel(name, texts, MainActivity.person.getId(), file.getName(), type, i));
                } else {
                    models.add(new ServerModel(name, texts, MainActivity.person.getId(), type, i));
                }
            }


            ServerModel[] modelsArr = new ServerModel[models.size()];
            for (int i = 0; i < models.size(); i++) {
                modelsArr[i] = models.get(i);
            }

            MultipartBody.Part[] partArr = new MultipartBody.Part[parts.size()];
            for (int i = 0; i < parts.size(); i++) {
                partArr[i] = parts.get(i);
            }

            Log.i(TAG, "onBackPressed: " + Arrays.toString(models.toArray()));
            new Thread(() -> RetrofitClient.uploadPhotos(new Callback<Void>() {
                @Override
                public void onResponse(@Nullable Call<Void> call,@Nullable Response<Void> response) {
                    Log.i(TAG, "onResponse: " + response.code());
                    RetrofitClient.startDownloadByUserID(MainActivity.person.getId(), AddActivity.this);
                }

                @Override
                public void onFailure(@Nullable Call<Void> call, @Nullable Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                }
            }, requestBody, modelsArr, partArr)).start();
            onBackPressed();
        } else {
            Snackbar.make(v, "Слишком малое количество глав", BaseTransientBottomBar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }
}