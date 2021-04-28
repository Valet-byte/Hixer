package com.abyte.valet.testan40121.cl_se;

import android.util.Log;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.fragments.PersonalFragment;
import com.abyte.valet.testan40121.model.Content;
import com.abyte.valet.testan40121.model.Projects.Project;
import com.abyte.valet.testan40121.model.artcles.Article;
import com.abyte.valet.testan40121.model.ideas.Idea;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static ClientAPI clientAPI;
    public static final LinkedList<ServerModel> projects = new LinkedList<>(),
    ideas = new LinkedList<>(), stats = new LinkedList<>();

    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.107:8080")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        clientAPI = retrofit.create(ClientAPI.class);
    }

    public static void findUser(Callback<Person> callback,
                          String name, String password){
        if (clientAPI == null) { new RetrofitClient(); }
        clientAPI.findPersons(name, password).enqueue(callback);
    }

    public static void uploadPhotos(Callback<Void> callback,
                                    RequestBody description,
                                    ServerModel[] models,
                                    MultipartBody.Part... parts){

        if (clientAPI == null) { new RetrofitClient(); }
        clientAPI.uploadPhoto(description, models, parts).enqueue(callback);}

    public static void getContentByUser(Person p, int type, LinkedList<ServerModel> models){
        if (clientAPI == null) { new RetrofitClient(); }
        clientAPI.getModelsByID(p.getId(), type).enqueue(new Callback<LinkedList<ServerModel>>() {
            @Override
            public void onResponse(Call<LinkedList<ServerModel>> call, Response<LinkedList<ServerModel>> response) {
                if (response.code()==200){
                    models.addAll(response.body());
                    new Thread(() ->{
                        for (ServerModel model : models) {
                            try {
                                model.setBitmap(clientAPI.getPhoto(model.getPhoto()).execute().body().byteStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.i("myTag", "onResponse: " + Arrays.toString(models.toArray()));
                    }).start();

                }
            }

            @Override
            public void onFailure(Call<LinkedList<ServerModel>> call, Throwable t) {

            }
        });
    }

    public static void startDownload(Long id) {
        if (clientAPI == null) { new RetrofitClient(); }
        new Thread(()->{
            try {
                projects.addAll(clientAPI.getModelsByID(id, 1).execute().body());

                for (ServerModel project : projects) {
                    project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                ideas.addAll(clientAPI.getModelsByID(id, 3).execute().body());

                for (ServerModel project : ideas) {
                    project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                stats.addAll(clientAPI.getModelsByID(id, 3).execute().body());
                for (ServerModel project : stats) {
                    project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

