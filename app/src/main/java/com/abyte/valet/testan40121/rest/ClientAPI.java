package com.abyte.valet.testan40121.rest;

import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.util.LinkedList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ClientAPI {

    @FormUrlEncoded
    @POST("./findUser")
    Call<Person> findPersons(@Field("name") String name,
                             @Field("pass") String pass);
    @Multipart
    @POST("./uploadPhoto")
    Call<Void> uploadPhoto(@Part("description") RequestBody description,
                           @Part("models") ServerModel[] models,
                           @Part MultipartBody.Part... photo);

    @FormUrlEncoded
    @POST("/getModelByUserID")
    Call<LinkedList<ServerModel>> getModelsByID(@Field("ID") Long ID,
                                                @Field("type") Integer type);

    @FormUrlEncoded
    @POST("/getPhoto")
    Call<ResponseBody> getPhoto(@Field("name") String name);

    @FormUrlEncoded
    @POST("./getModel")
    Call<LinkedList<ServerModel>> getModel(@Field("type") Integer type);

    @FormUrlEncoded
    @POST("/getAllModelsByMainName")
    Call<LinkedList<ServerModel>> getAllModels(@Field("mainName") String mainName);

    @FormUrlEncoded
    @POST("/addUser")
    Call<Person> addUser(@Field("name") String name,
                         @Field("pass") String pass);

}