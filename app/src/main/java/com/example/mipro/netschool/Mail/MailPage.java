package com.example.mipro.netschool.Mail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.mipro.netschool.R;


public class MailPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_castom_layout);

        TextView textView_title = (TextView) findViewById(R.id.titleWork);
        TextView textView_author = (TextView) findViewById(R.id.signWork);
        TextView textView_date = (TextView) findViewById(R.id.date);
        TextView textView_text = (TextView) findViewById(R.id.descriptionWork);

        Intent intent = getIntent();

        String title  = intent.getStringExtra("title");
        String author  = intent.getStringExtra("author");
        String date  = intent.getStringExtra("date");
        String text  = intent.getStringExtra("text");


        textView_title.setText(title);
        textView_author.setText("От: " + author);
        textView_date.setText(date);
        textView_text.setText(text);
    }

}