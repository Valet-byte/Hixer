package com.abyte.valet.testan40121.model.person;

import com.abyte.valet.testan40121.cl_se.ClientAPI;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private ClientAPI clientAPI;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.106:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();

        clientAPI = retrofit.create(ClientAPI.class);

    }

    public void findUser(Callback<Person> callback,
                         String name, String password){ clientAPI.findPersons(name, password).enqueue(callback);}

    public void findUser(Callback<Person> callback,
                         Person p){}
}
