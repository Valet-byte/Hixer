package com.abyte.valet.testan40121.cl_se;

import com.abyte.valet.testan40121.model.person.Person;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ClientAPI {

    @FormUrlEncoded
    @POST("./findPerson")
    Call<Person> findPersons(@Field("name") String name,
                             @Field("pass") String pass);

    @Multipart
    @POST("./uploadPhoto")
    Call<ResponseBody> uploadPhoto(@Part RequestBody description,
                                   @Part MultipartBody.Part photo);


}
