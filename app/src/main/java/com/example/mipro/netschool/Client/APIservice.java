package com.example.mipro.netschool.Client;
/** Хеддер для любый POST/GET запросов:
 * Лююой запрос, кроме *смотреть api*
 * делается с помощью функции:
 * T - класс, в который вы хотите обернуть ответ
 *private Disposable disposable;
 *  private SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
 *                  или
 *  private SharedPreferences mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
 *  private void signIn(String login, String password, int id, String token, int systemType) {
 *      disposable = Client.getInstance()
 *      .signIn(new LoginRequst(login, password, id, token, systemType))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<Response<T>>() {

            @Override
            public void onNext(Response<T> response) {
                if (response.isSuccessful()) {
                    Client.getInstance().responseHandler("" + response.code(), "signIn", "");
                    *Здесь обрабатывать успешный ответ*
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Client.getInstance().responseHandler("" + response.code(), "signIn",jObjError.getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });

         Также нужно реализовать метод в интерфейсе
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

    @GET("get_posts")
    Observable<Response<LoginResponse>> getPosts();

}

