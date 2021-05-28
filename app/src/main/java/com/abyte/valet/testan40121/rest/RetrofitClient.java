package com.abyte.valet.testan40121.rest;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.fragments.ArticleFragment;
import com.abyte.valet.testan40121.fragments.FindFragment;
import com.abyte.valet.testan40121.fragments.IdeaFragment;
import com.abyte.valet.testan40121.fragments.InfoFragment;
import com.abyte.valet.testan40121.fragments.PersonalFragment;
import com.abyte.valet.testan40121.fragments.ProjectsFragment;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.abyte.valet.testan40121.activitys.AddActivity.TAG;

public class RetrofitClient {

    private static ClientAPI clientAPI;
    public static final List<ServerModel> projects =  Collections.synchronizedList(new LinkedList<>()),
            ideas = Collections.synchronizedList(new LinkedList<>()),
            stats = Collections.synchronizedList(new LinkedList<>()),
            infoList = Collections.synchronizedList(new ArrayList<>()),
            projectsFromUser =  Collections.synchronizedList(new LinkedList<>()),
            ideasFromUser = Collections.synchronizedList(new LinkedList<>()),
            statsFromUser = Collections.synchronizedList(new LinkedList<>()),
            findList = Collections.synchronizedList(new LinkedList<>());
    private RetrofitClient() { }

    public static void registrationUser(Callback<Person> callback,
                                        String name, String password,
                                        RequestBody icon, MultipartBody.Part part){
        if (clientAPI == null) { dropAll(); }
        clientAPI.addUser(name, password, icon, part).enqueue(callback);
    }

    public static void findUser(Callback<Person> callback,
                                String name, String password){
        if (clientAPI == null) { dropAll(); }
        clientAPI.findPersons(name, password).enqueue(callback);
    }

    public static void uploadPhotos(Callback<Void> callback,
                                    RequestBody description,
                                    ServerModel[] models,
                                    MultipartBody.Part... parts){
        if (clientAPI == null) { dropAll();}
        clientAPI.uploadContents(description, models, parts).enqueue(callback);}


    public static void startDownload() {
        if (clientAPI == null) { dropAll(); }

        new Thread(()->{
            try {
                ideas.addAll(Objects.requireNonNull(clientAPI.getModel(2).execute().body()));
                projects.addAll( Objects.requireNonNull(clientAPI.getModel(1).execute().body()));
                stats.addAll(Objects.requireNonNull(clientAPI.getModel(3).execute().body()));

                synchronized(projects) {
                    for (ServerModel project : projects) {
                        if (project.getPhoto() != null)
                            project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }
                synchronized (ideas){
                    for (ServerModel project : ideas) {
                        if (project.getPhoto() != null)project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }

                synchronized (stats){
                    for (ServerModel project : stats) {
                        if (project.getPhoto() != null)project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }

                MainActivity.runOnMainThread(() -> {
                    IdeaFragment.invalidate();
                    ProjectsFragment.invalidate();
                    ArticleFragment.invalidate();
                });

            } catch (IOException e) {
                e.printStackTrace();

            }
        }).start();

    }
    public static void startDownloadByUserID(Long id){
        if (clientAPI == null) { dropAll(); }
        new Thread(()->{
            try {
                projectsFromUser.addAll(Objects.requireNonNull(clientAPI.getModelsByID(id, 1).execute().body()));
                ideasFromUser.addAll(Objects.requireNonNull(clientAPI.getModelsByID(id, 2).execute().body()));
                statsFromUser.addAll(Objects.requireNonNull(clientAPI.getModelsByID(id, 3).execute().body()));

                synchronized (projectsFromUser){
                    for (ServerModel project : projectsFromUser) {
                        if (project.getPhoto() != null)project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }

                synchronized (ideasFromUser) {
                    for (ServerModel project : ideasFromUser) {
                        if (project.getPhoto() != null)project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }

                synchronized (statsFromUser) {
                    for (ServerModel project : statsFromUser) {
                        if (project.getPhoto() != null)project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                    }
                }


                MainActivity.runOnMainThread(PersonalFragment::invalidate);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }).start();

    }
    public static void startDownloadByMainStats(String mainName){

        MainActivity.getDialog().startDialog();
        Log.i(TAG, "startDownloadByMainStats: dialog start");

        new Thread(()->{
            try {
                boolean isNotEmpty = false;
                try {
                    isNotEmpty = infoList.get(0).getName().equals(mainName);
                } catch (Exception ignore){}

                if (!isNotEmpty) {

                    infoList.addAll(Objects.requireNonNull(clientAPI.getAllModels(mainName).execute().body()));
                    synchronized (infoList) {
                        for (ServerModel serverModel : infoList) {
                            if (serverModel.getPhoto() != null)
                                serverModel.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(serverModel.getPhoto()).execute().body()).byteStream());
                        }
                    }
                    MainActivity.runOnMainThread(() -> {
                        Log.i(TAG, "startDownloadByMainStats: invalidate");
                        Log.i(TAG, "startDownloadByMainStats: " + Arrays.toString(infoList.toArray()));
                        InfoFragment.invalidate();
                        Log.i(TAG, "startDownloadByMainStats: dialog stop");
                        MainActivity.getDialog().stopDialog();
                    });

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void dropInfoList(){
        MainActivity.getDialog().stopDialog();
        infoList.clear();
    }

    public static void getStatsByID(String ID){
        if (clientAPI == null) { dropAll();}
        clientAPI.getStatsByID(ID).enqueue(new Callback<LinkedList<ServerModel>>() {
            @Override
            public void onResponse(@Nullable Call<LinkedList<ServerModel>> call, @Nullable Response<LinkedList<ServerModel>> response) {
                new Thread(()->{
                    clearFindList();
                    findList.addAll(Objects.requireNonNull(Objects.requireNonNull(response).body()));

                    synchronized (findList){
                        for (ServerModel project : findList) {
                            if (project.getPhoto() != null) {
                                try {
                                    project.setBitmap(Objects.requireNonNull(clientAPI.getPhoto(project.getPhoto()).execute().body()).byteStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    MainActivity.runOnMainThread(FindFragment::invalidate);
                }).start();

                MainActivity.runOnMainThread(FindFragment::invalidate);
            }

            @Override
            public void onFailure(@Nullable Call<LinkedList<ServerModel>> call, @Nullable Throwable t) {

            }
        });
    }

    public static void dropAll(){
        clientAPI = getApiClient();
        projects.clear();
        ideas.clear();
        stats.clear();
        infoList.clear();
        projectsFromUser.clear();
        ideasFromUser.clear();
        statsFromUser.clear();
    }

    public static void clearFindList(){
        findList.clear();
        InfoFragment.invalidate();
    }

    public static void clearPersonStats(){
        projectsFromUser.clear();
        ideasFromUser.clear();
        statsFromUser.clear();
    }

    public static void downloadIcon(Person person) {
        if (clientAPI == null) { dropAll(); }
        new Thread(()->{
            try {
                person.setPhoto(Objects.requireNonNull(clientAPI.getIcon(person.getPhotoName()).execute().body()).byteStream());
                MainActivity.runOnMainThread(() -> PersonalFragment.updateIcon(person.getPhoto()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

        public static OkHttpClient.Builder getUnsafeOkHttpClient() {
            try {
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier((hostname, session) -> true);
                return builder;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static ClientAPI getApiClient() {
            ClientAPI apiInterface = null;

                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://188.225.46.21:8082")
                            .client(getUnsafeOkHttpClient().build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    apiInterface = retrofit.create(ClientAPI.class);
                } catch (Exception e) {

                    e.printStackTrace();
                }




            return apiInterface;
        }

}

