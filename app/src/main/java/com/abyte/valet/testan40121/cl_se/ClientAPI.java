package com.abyte.valet.testan40121.cl_se;

import com.abyte.valet.testan40121.model.person.Person;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                           @Part("names") String [] strings,
                           @Part("texts") String[] texts,
                           @Part MultipartBody.Part... photo);


}
