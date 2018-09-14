package com.example.mipro.netschool.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mipro.netschool.R;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;

 @SuppressLint("ValidFragment")
 class Password extends Fragment {

    private String first = "Старый пароль";
    private String second = "Новый пароль";
    private String third = "Повторите новый пароль";
    private String current_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_fragment, null);
        getActivity().setTitle("Смена пароля");

        return view;
    }


    @Override
    public void onResume() {
        //getActivity().findViewById(R.id.pass1).requestFocus();
        super.onResume();
        final EditText editText1 = (EditText) getActivity().findViewById(R.id.pass1);
        final EditText editText2 = (EditText) getActivity().findViewById(R.id.pass2);
        final EditText editText3 = (EditText) getActivity().findViewById(R.id.pass3);
        final TextView textView1 = (TextView) getActivity().findViewById(R.id.old);
        final TextView textView2 = (TextView) getActivity().findViewById(R.id.new1);
        final TextView textView3 = (TextView) getActivity().findViewById(R.id.confirm_new);

        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);

        editText1.setText(first);
        editText2.setText(second);
        editText3.setText(third);

        editText2.setEnabled(false);
        editText3.setEnabled(false);

       editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if (editText1.getText().toString().equals("Старый пароль")) {
                   editText1.setText("");
               } else {
                   if (textView1.getVisibility() == View.INVISIBLE) {
                       String password = "ff";
                       if (!editText1.getText().toString().equals(password)) {
                           textView1.setVisibility(View.VISIBLE);
                           textView1.setText("wrong");
                       } else {
                           editText2.setEnabled(true);
                           //editText1.setEnabled(false);
                       }
                   } else {
                       textView1.setVisibility(View.INVISIBLE);
                   }
               }
           }
       });

       editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if (editText2.getText().toString().equals("Новый пароль")) {
                   if (textView1.getVisibility() == View.INVISIBLE) {
                       editText1.setEnabled(false);
                       editText2.setText("");
                   }
               } else {
                   if (textView2.getVisibility() == View.INVISIBLE) {
                       if (editText2.getText().toString().length() < 7) {
                           textView2.setVisibility(View.VISIBLE);
                           textView2.setText(" < 7");
                       } else {
                           editText3.setEnabled(true);
                       }
                   } else {
                       textView2.setVisibility(View.INVISIBLE);
                   }
               }
           }
       });

       editText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if (editText3.getText().toString().equals("Повторите новый пароль")) {
                   if (textView2.getVisibility() == View.INVISIBLE) editText3.setText("");
               }
           }
       });

        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView3.getVisibility() == View.INVISIBLE) {
                    if (!editText3.getText().toString().equals(editText2.getText().toString())) {
                        textView3.setVisibility(View.VISIBLE);
                        textView3.setText("invalid");
                        Log.e(LOG_TAG, "asda1");
                    } else {
                        // отправить пароль на сервер
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                } else {
                    textView3.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
