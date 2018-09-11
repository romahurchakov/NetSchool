package com.example.mipro.netschool.Client;
/** Хеддер для любый POST/GET запросов:
 * Лююой запрос, кроме *смотреть api*
 * делается с помощью функции:
 * T - класс, в который вы хотите обернуть ответ
 *
 *
 */

import android.content.SharedPreferences;

import com.example.mipro.netschool.Client.Pojo.LoginRequst;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Client.Pojo.School_;
import com.example.mipro.netschool.Resources.File;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIservice {
    @GET("get_school_list")
    Observable<Response<School>> getSchoolList();

    @POST("sign_in")
    Observable<Response<LoginResponse>> signIn(@Body LoginRequst loginRequst);

    @POST("sign_out")
    Observable<Response<Object>> signOut(@Header("SessionName") String kek);

}

