package com.example.mipro.netschool.Autentification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.LoginRequest;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Client.Pojo.School_;
import com.example.mipro.netschool.Service.Log;
import com.example.mipro.netschool.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class Autentification extends AppCompatActivity {

    private TextFieldBoxes school_box;
    private ExtendedEditText school;
    private studio.carbonylgroup.textfieldboxes.TextFieldBoxes login_box;
    private studio.carbonylgroup.textfieldboxes.TextFieldBoxes password_box;
    private int schoolId;
    private studio.carbonylgroup.textfieldboxes.ExtendedEditText login;
    private studio.carbonylgroup.textfieldboxes.ExtendedEditText password;
    private Button enter;
    private Disposable disposable;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autentification);
        school = findViewById(R.id.school_sign_in);
        school_box = findViewById(R.id.school_box_sign_in);

        login_box = (studio.carbonylgroup.textfieldboxes.TextFieldBoxes) findViewById(R.id.login_box_sign_in);
        password_box = (studio.carbonylgroup.textfieldboxes.TextFieldBoxes) findViewById(R.id.password_box_sign_in);
        login = (studio.carbonylgroup.textfieldboxes.ExtendedEditText) findViewById(R.id.login_sign_in);
        password = (studio.carbonylgroup.textfieldboxes.ExtendedEditText) findViewById(R.id.password_sing_in);
        enter = (Button) findViewById(R.id.enter_sign_in);

        enter.setOnClickListener(view -> {
                    if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        findViewById(R.id.enter_sign_in).setEnabled(false);
                        TextView f = findViewById(R.id.error_msg);
                        f.setText("");
                        login_box.setEnabled(false);
                        password_box.setEnabled(false);
                        school_box.setEnabled(false);
                        findViewById(R.id.button4).setEnabled(false);
                        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                        signIn("мкорнакова", md5(""+121212), 1, "kek", 2);
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = this.findViewById(R.id.button4);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SchoolList.class);
            startActivityForResult(intent,1);
        });
    }

    private void signIn(String login, String password, int id, String token, int systemType) {
        disposable = Client.getInstance(this)
                .signIn(new LoginRequest(login, password, id, token, systemType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<LoginResponse>>() {

                    @Override
                    public void onNext(Response<LoginResponse> response) {
                        findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.enter_sign_in).setEnabled(true);
                        login_box.setEnabled(true);
                        password_box.setEnabled(true);
                        school_box.setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "signIn", "", Autentification.this);
                        } else {
                            TextView textView = findViewById(R.id.error_msg);
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    textView.setText(Client.getInstance().responseHandler("" + response.code(), "signIn", jObjError.getString("error"),Autentification.this));
                                } else {
                                    textView.setText(Client.getInstance().responseHandler("" + response.code(), "signIn", "",Autentification.this));
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
                        login_box.setEnabled(true);
                        password_box.setEnabled(true);
                        school_box.setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.enter_sign_in).setEnabled(true);
                        TextView textView = findViewById(R.id.error_msg);
                        textView.setText("Ошибка сети");

                    }

                    @Override
                    public void onComplete() {
                        Autentification.this.onBackPressed();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        School_ school_= (School_)data.getSerializableExtra("school");
        schoolId = school_.getId();
        school.setText(school_.getName());
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
