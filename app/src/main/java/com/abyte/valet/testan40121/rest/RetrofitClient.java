package com.abyte.valet.testan40121.rest;

import android.app.Activity;
import android.util.Log;

import com.abyte.valet.testan40121.activitys.AddActivity;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.fragments.ArticleFragment;
import com.abyte.valet.testan40121.fragments.IdeaFragment;
import com.abyte.valet.testan40121.fragments.InfoFragment;
import com.abyte.valet.testan40121.fragments.PersonalFragment;
import com.abyte.valet.testan40121.fragments.ProjectsFragment;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    public static  List<ServerModel> projects = Collections.synchronizedList(new LinkedList<>()),
    ideas = Collections.synchronizedList(new LinkedList<>()),
            stats = Collections.synchronizedList(new LinkedList<>()),
            projectsFromUser =  Collections.synchronizedList(new LinkedList<>()),
            ideasFromUser = Collections.synchronizedList(new LinkedList<>()),
            statsFromUser = Collections.synchronizedList(new LinkedList<>());
    public static List<ServerModel> infoList = Collections.synchronizedList(new LinkedList<>());
    public static Activity activityRetroFit;



    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.111:8080")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        clientAPI = retrofit.create(ClientAPI.class);
        projects =  Collections.synchronizedList(new LinkedList<>());
                ideas = Collections.synchronizedList(new LinkedList<>());
                stats = Collections.synchronizedList(new LinkedList<>());
                projectsFromUser =  Collections.synchronizedList(new LinkedList<>());
                ideasFromUser = Collections.synchronizedList(new LinkedList<>());
                statsFromUser = Collections.synchronizedList(new LinkedList<>());

    }

    public static void registrationUser(Callback<Person> callback,
                                        String name, String password){
        if (clientAPI == null) { new RetrofitClient(); }
        clientAPI.addUser(name, password).enqueue(callback);
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



    public static void startDownload( Activity activity) {
        if (clientAPI == null) { new RetrofitClient(); }
        activityRetroFit = activity;
        new Thread(()->{
            try {
                projects.addAll( Objects.requireNonNull(clientAPI.getModel(1).execute().body()));
                synchronized(projects) {
                    Iterator i = projects.iterator(); // Синхронизированный итератор
                    while (i.hasNext()) {
                        ServerModel project = (ServerModel) i.next();
                        if (project.getPhoto() != null) project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                    }
                }

                activityRetroFit.runOnUiThread(()->{
                    ProjectsFragment.invalidate();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                ideas.addAll(clientAPI.getModel( 2).execute().body());

                for (ServerModel project : ideas) {
                    if (project.getPhoto() != null)project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }

                activityRetroFit.runOnUiThread(()->{
                    IdeaFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                stats.addAll(clientAPI.getModel( 3).execute().body());
                for (ServerModel project : stats) {
                    if (project.getPhoto() != null)project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
                activityRetroFit.runOnUiThread(()->{
                    ArticleFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void startDownloadByUserID(Long id, Activity activity){
        activityRetroFit = activity;
        if (clientAPI == null) { new RetrofitClient(); }
        new Thread(()->{
            try {
                projectsFromUser.addAll(clientAPI.getModelsByID( id,1).execute().body());

                for (ServerModel project : projectsFromUser) {
                    if (project.getPhoto() != null)project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
                activityRetroFit.runOnUiThread(()->{
                    PersonalFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                ideasFromUser.addAll(clientAPI.getModelsByID( id,2).execute().body());

                for (ServerModel project : ideasFromUser) {
                    if (project.getPhoto() != null)project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
                activityRetroFit.runOnUiThread(()->{
                    PersonalFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                statsFromUser.addAll(clientAPI.getModelsByID( id,3).execute().body());
                for (ServerModel project : statsFromUser) {
                    if (project.getPhoto() != null)project.setBitmap(clientAPI.getPhoto(project.getPhoto()).execute().body().byteStream());
                }
                activityRetroFit.runOnUiThread(()->{
                    PersonalFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void startDownloadByMainStats(String mainName){
        new Thread(()->{
            try {
                infoList = clientAPI.getAllModels(mainName).execute().body();
                Collections.synchronizedList(infoList);
                for (ServerModel serverModel : infoList) {
                    if (serverModel.getPhoto() != null) serverModel.setBitmap(clientAPI.getPhoto(serverModel.getPhoto()).execute().body().byteStream());
                }
                activityRetroFit.runOnUiThread(()->{
                    InfoFragment.invalidate();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void allDowngrade(){
        projects.clear();
        ideas.clear();
        stats.clear();
        infoList.clear();
        projectsFromUser.clear();
        ideasFromUser.clear();
        statsFromUser.clear();
        new RetrofitClient();
    }
}

