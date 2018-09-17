package com.example.mipro.netschool.Notifications;


import com.example.mipro.netschool.R;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;



public class NotificationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notiaication_page);

        TextView textView_title = (TextView) findViewById(R.id.title);
        TextView textView_author = (TextView) findViewById(R.id.author);
        TextView textView_date = (TextView) findViewById(R.id.date);
        TextView textView_text = (TextView) findViewById(R.id.text);
        TextView textView_file_name = (TextView) findViewById(R.id.file_name);

        Intent intent = getIntent();

        String title  = intent.getStringExtra("title");
        String author  = intent.getStringExtra("author");
        String date  = intent.getStringExtra("date");
        String text  = intent.getStringExtra("description");
        String file_name  = intent.getStringExtra("file_name");


        textView_title.setText(title);
        textView_author.setText(author);
        textView_date.setText(date);
        textView_text.setText(text);
        textView_file_name.setText(file_name);
    }
}

