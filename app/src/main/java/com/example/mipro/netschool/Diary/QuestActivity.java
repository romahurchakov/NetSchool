package com.example.mipro.netschool.Diary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipro.netschool.R;

import static com.example.mipro.netschool.Diary.Quest.COMP_Q;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;
import static com.example.mipro.netschool.MainActivity.SOCKET;

public class QuestActivity extends AppCompatActivity implements View.OnClickListener {

    private Quest quest;
    private boolean completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        quest = (Quest) getIntent().getParcelableExtra("quest_info");

        ((TextView)findViewById(R.id.lessonWork)).setText(quest.getName());
        ((TextView)findViewById(R.id.typeWork)).setText(quest.getType());
        ((TextView)findViewById(R.id.titleWork)).setText(quest.getTitle());
        ((TextView)findViewById(R.id.descriptionWork)).setText(quest.getDescription());
        ((TextView)findViewById(R.id.signWork)).setText(quest.getAuthor() + '\n'
                +  quest.getDateW() + ", " + quest.getDayOfWeek().toLowerCase());
        ((TextView)findViewById(R.id.fileTxWork)).setText(quest.getFile());

        completed = quest.getStatus() == COMP_Q;

        LinearLayout ll = (LinearLayout) findViewById(R.id.llFileWork);
        if (quest.getFile().equals(""))
            ((LinearLayout)findViewById(R.id.questBox)).removeView(ll);
        else
            ll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llFileWork: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(quest.getFile()));
                startActivity(intent);
            }
            default:
                Log.e(LOG_TAG, "bug!!!");
        }
    }

    private void setItem(MenuItem i) {
        if (!completed) {
            i.setIcon(R.drawable.quest_done);
            i.setTitle(R.string.quest_done);
        }
        else {
            i.setIcon(R.drawable.quest_undone);
            i.setTitle(R.string.quest_undone);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quest, menu);
        MenuItem i = menu.getItem(0);
        setItem(i);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;

        if (completed) {
            result = SOCKET.markAsUndone(quest);
        } else {
            result = SOCKET.markAsDone(quest);
        }
        if (result) {
            completed = !completed;
            Toast.makeText(this,"Успешно", Toast.LENGTH_SHORT).show();
            setItem(item);
        } else {
            Toast.makeText(this,"Ошибка", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
