package com.example.mipro.netschool.Client;

import android.content.Context;

import com.example.mipro.netschool.Client.Pojo.LoginRequest;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Log;

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

    public Observable<Response<LoginResponse>> signIn(LoginRequest loginRequst) {
        return apIservice.signIn(loginRequst);
    }

    public Observable<Response<LoginResponse>> getPosts() {
        return apIservice.getPosts();
    }


    public void responseHandler(String responseCode, String requst, String error) {
        switch (responseCode) {
            case "400":
                switch (error) {
                    case "malformed_data":
                        Log.v(requst + ":Неверный формат тела запроса (не json) или неверный тип данных (например string вместо int)");
                        break;
                    case "invalid_data":
                        Log.v(requst + ":Неверные данные в запросе (несуществующий id заданий, пользователей, школ в БД)");
                        break;
                    case "invalid_page":
                        Log.v(requst + ":Запрашиваемая страница не существует");
                        break;
                    case "invalid_system_type":
                        Log.v(requst + ":Невалидный тип устройства");
                        break;
                    case "empty_token":
                        Log.v(requst + ":Пустой токен");
                        break;
                    case "invalid_device_info":
                        Log.v(requst + ":Невалидная информация об устройстве");
                        break;
                    case "invalid_login_data":
                        Log.v(requst + ":Наверный логин или пароль");
                        break;

                }
                break;
            case "200":
                Log.v(requst + ":Успешный запрос");
                break;
            case "401":
                Log.v(requst + ":Клиент не авторизован");
                break;
            case "402":
                Log.v(requst + ":У пользователя и у его школы нет доступа к сервису");
                break;
            case "404":
                Log.v(requst + ":Неверный путь запроса");
                break;
            case "405":
                Log.v(requst + ":Неверный метод (например, POST вместо GET для /get_school_list). GET используется в тех запросах, где не надо посылать json в теле");
                break;
            case "500":
                Log.v(requst + ":Фатальная ошибка на сервере");
                break;
            case "501":
                Log.v(requst + ":Запрос еще не реализован на сервере");
                break;
            case "502":
                Log.v(requst + ":Ошибка на сервере школы");
                break;
        }
    }

    public String getSessionName(String setCookie) {
        String sessionName = "";
        boolean flag = true;
        for (int i = 0; i < setCookie.length(); i++) {
            if (setCookie.charAt(i) == ';') {
                break;
            }
            if (!flag) {
                sessionName += setCookie.charAt(i);
            }
            if (setCookie.charAt(i) == '=') {
                flag = false;
            }
        }
        return sessionName;
    }

    public String getToken(String header, String sessionName) {
        String token = "";
        String buffer = "";
        boolean flag = false;
        for (int i = 0; i < header.length(); i++) {
            if (header.charAt(i) == ';') {
                break;
            }
            buffer += header.charAt(i);
            if (flag) {
                token += header.charAt(i);
            }
            if (buffer.equals("set-cookie: " + sessionName + "=")) {
                flag = true;
            }
        }
        return token;
    }

}
