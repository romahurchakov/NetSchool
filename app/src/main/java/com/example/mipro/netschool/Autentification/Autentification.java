package com.example.mipro.netschool.Autentification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.LoginRequest;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Client.Pojo.School_;
import com.example.mipro.netschool.Log;
import com.example.mipro.netschool.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Autentification extends AppCompatActivity {

    private LinearLayout school;
    private studio.carbonylgroup.textfieldboxes.TextFieldBoxes login_box;
    private studio.carbonylgroup.textfieldboxes.TextFieldBoxes password_box;
    private studio.carbonylgroup.textfieldboxes.ExtendedEditText login;
    private studio.carbonylgroup.textfieldboxes.ExtendedEditText password;
    private Button enter;
    private List<School_> schoolList;
    private Client client;
    private SharedPreferences mSettings;
    private Disposable disposable;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SESSION_NAME = "session_name";
    public static final String APP_PREFERENCES_COOKIE = "cookie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autentification);

        school = (LinearLayout) findViewById(R.id.school_list_sign_in);
        login_box = (studio.carbonylgroup.textfieldboxes.TextFieldBoxes) findViewById(R.id.login_box_sign_in);
        password_box = (studio.carbonylgroup.textfieldboxes.TextFieldBoxes) findViewById(R.id.password_box_sign_in);
        login = (studio.carbonylgroup.textfieldboxes.ExtendedEditText) findViewById(R.id.login_sign_in);
        password = (studio.carbonylgroup.textfieldboxes.ExtendedEditText) findViewById(R.id.password_sing_in);
        enter = (Button) findViewById(R.id.enter_sign_in);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        getSchoolList();

        login.setText("мкорнакова");
        password.setText("121212");

        enter.setOnClickListener(view -> {
                    if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        signIn(login.getText().toString(), md5(password.getText().toString()), 1, "kek", 2);
                    }
                }
        );


    }

    private void signIn(String login, String password, int id, String token, int systemType) {
        disposable = Client.getInstance(this)
                .signIn(new LoginRequest(login, password, id, token, systemType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<LoginResponse>>() {

                    @Override
                    public void onNext(Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "signIn", "");
                        } else {
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Client.getInstance().responseHandler("" + response.code(), "signIn", jObjError.getString("error"));
                                } else {
                                    Client.getInstance().responseHandler("" + response.code(), "signIn", "");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getSchoolList() {
        disposable = Client.getInstance()
                .getSchoolList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<School>>() {

                    @Override
                    public void onNext(Response<School> response) {
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "getSchoolList", "");
                        } else {
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Client.getInstance().responseHandler("" + response.code(), "getSchoolList", jObjError.getString("error"));
                                } else {
                                    Client.getInstance().responseHandler("" + response.code(), "getSchoolList", "");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
