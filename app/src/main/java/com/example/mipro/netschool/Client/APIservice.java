package com.example.mipro.netschool.Client;

import com.example.mipro.netschool.Client.Pojo.File;
import com.example.mipro.netschool.Client.Pojo.LoginRequest;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.Resources;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Client.Pojo.Notification;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.POST;

public interface APIservice {
    @GET("get_school_list")
    Observable<Response<School>> getSchoolList();

    @POST("sign_in")
    Observable<Response<LoginResponse>> signIn(@Body LoginRequest loginRequest);

    @GET("get_posts")
    Observable<Response<LoginResponse>> getPosts();

    @GET("get_resources")
    Observable<Response<Resources>> getResources();

    @GET("get_posts")
    Observable<Response<Notification>> getNotification();

}

