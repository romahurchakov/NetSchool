package com.example.mipro.netschool.Client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.mipro.netschool.Autentification.Autentification;
import com.example.mipro.netschool.Client.Pojo.LoginRequest;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.Notification;
import com.example.mipro.netschool.Client.Pojo.Resources;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Service.Log;

import java.io.File;
import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client {
    private OkHttpClient okHttpClient;
    private static APIservice apIservice;
    private static final String BASE_URL = "https://www.netschool.app:8000/";

    public Client(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        JavaNetCookieJar jncj = new JavaNetCookieJar(CookieHandler.getDefault());
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apIservice = retrofit.create(APIservice.class);
    }

    public Client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apIservice = retrofit.create(APIservice.class);
    }

    public static Client getInstance(Context context) {
        return new Client(context);
    }

    public static Client getInstance() {
        return new Client();
    }

    public Observable<Response<School>> getSchoolList() {
        return apIservice.getSchoolList();
    }

    public Observable<Response<Resources>> getResources() {
        return apIservice.getResources();
    }

    public Observable<Response<Notification>> getNotification() {
        return apIservice.getNotification();
    }


    public Observable<Response<LoginResponse>> signIn(LoginRequest loginRequst) {
        return apIservice.signIn(loginRequst);
    }

    public Observable<Response<LoginResponse>> getPosts() {
        return apIservice.getPosts();
    }

    public String responseHandler(String responseCode, String requst, String error, Context context) {
        String status = "";
        switch (responseCode) {
            case "400":
                switch (error) {
                    case "malformed_data":
                        status += requst + ":Неверный формат тела запроса (не json) или неверный тип данных (например string вместо int)";
                        Log.v(requst + ":Неверный формат тела запроса (не json) или неверный тип данных (например string вместо int)");
                        break;
                    case "invalid_data":
                        status += requst + ":Неверные данные в запросе (несуществующий id заданий, пользователей, школ в БД)";
                        Log.v(requst + ":Неверные данные в запросе (несуществующий id заданий, пользователей, школ в БД)");
                        break;
                    case "invalid_page":
                        status += requst + ":Запрашиваемая страница не существует";
                        Log.v(requst + ":Запрашиваемая страница не существует");
                        break;
                    case "invalid_system_type":
                        status += requst + ":Невалидный тип устройства";
                        Log.v(requst + ":Невалидный тип устройства");
                        break;
                    case "empty_token":
                        status += requst + ":Пустой токен";
                        Log.v(requst + ":Пустой токен");
                        break;
                    case "invalid_device_info":
                        status += requst + ":Невалидная информация об устройстве";
                        Log.v(requst + ":Невалидная информация об устройстве");
                        break;
                    case "invalid_login_data":
                        status += requst + ":Наверный логин или пароль";
                        Log.v(requst + ":Наверный логин или пароль");
                        break;

                }
                break;
            case "200":
                status += requst + ":Успешный запрос";
                Log.v(requst + ":Успешный запрос");
                break;
            case "401":
                status += requst + ":Клиент не авторизован";
                Log.v(requst + ":Клиент не авторизован");
                context.startActivity(new Intent(context, Autentification.class));
                break;
            case "402":
                status += requst + ":У пользователя и у его школы нет доступа к сервису";
                Log.v(requst + ":У пользователя и у его школы нет доступа к сервису");
                break;
            case "404":
                status += requst + ":Неверный путь запроса";
                Log.v(requst + ":Неверный путь запроса");
                break;
            case "405":
                status += requst + ":Неверный метод (например, POST вместо GET для /get_school_list). GET используется в тех запросах, где не надо посылать json в теле";
                Log.v(requst + ":Неверный метод (например, POST вместо GET для /get_school_list). GET используется в тех запросах, где не надо посылать json в теле");
                break;
            case "500":
                status += requst + ":Фатальная ошибка на сервере";
                Log.v(requst + ":Фатальная ошибка на сервере");
                break;
            case "501":
                status += requst + ":Запрос еще не реализован на сервере";
                Log.v(requst + ":Запрос еще не реализован на сервере");
                break;
            case "502":
                status += requst + ":Ошибка на сервере школы";
                Log.v(requst + ":Ошибка на сервере школы");
                break;
        }
        return status;
    }
}
