package com.example.mipro.netschool.Mail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.mipro.netschool.R;
import com.example.mipro.netschool.Client.Client;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;


public class Mail extends Fragment{

    String[] authors = {"autor", "Krupnova hehe"};
    String[] titles = {"title", "Дешевые ауди"};
    String[] dates = {"10.10.2010", "Today"};
    String[] description = {"description", "dont want to be shown"};
    ArrayList<Pair<Integer, View>> longClicked = new ArrayList<Pair<Integer, View>>();
    Boolean showOptions = false;

    public Mail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Mail::onCreate");
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        //YEAR = new Year();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chose_mail, menu);
        if (showOptions){
            menu.findItem(R.id.delete).setVisible(true);
            menu.findItem(R.id.answer).setVisible(true);
            menu.findItem(R.id.mark).setVisible(true);
        } else {
            menu.findItem(R.id.delete).setVisible(false);
            menu.findItem(R.id.answer).setVisible(false);
            menu.findItem(R.id.mark).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete: {
                Log.i(LOG_TAG, "I pressed for delete");
                //delete()
                break;
            }
            case R.id.answer: {
                Log.i(LOG_TAG, "I pressed for answer");
                //answer()
                break;
            }
            case R.id.mark: {
                Log.i(LOG_TAG, "I pressed for mark");
                //mark()
                break;
            }
            case R.id.first: {
                Log.i(LOG_TAG, "I pressed for Входящие");
                //
                break;
            }
            case R.id.second: {
                Log.i(LOG_TAG, "I pressed for Черновики");
                //
                break;
            }
            case R.id.third: {
                Log.i(LOG_TAG, "I pressed for Отправленные");
                //
                break;
            }
            case R.id.last: {
                Log.i(LOG_TAG, "I pressed for Удаленные");
                //
                break;
            }
            default: {
                Log.d(LOG_TAG, "Mail::onOptionsItemSelected, default");
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
           // case R.id.edit:
                //editNote(info.id);
             //   return true;
            case R.id.delete:
                //deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.mail_main, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.main_mails);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Log.v("long clicked","pos: " + pos);
                longClicked.add(new Pair<Integer, View>(pos, arg1));
                //listView.setBackgroundColor(2);
                arg1.setBackgroundColor(Color.LTGRAY);
                showOptions = true;
                getActivity().invalidateOptionsMenu();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!longClicked.isEmpty()){
                    Log.i(LOG_TAG, "long click was earlier");
                    boolean need_add = true;
                    for (Pair<Integer, View> pair : longClicked ) {
                        if (pair.first == i) {
                            //longClicked.indexOf(pair);
                            Log.i(LOG_TAG, "this element has been yet");
                            longClicked.remove(pair);
                            view.setBackgroundColor(Color.WHITE);
                            need_add = false;
                        }
                        if (longClicked.isEmpty()){
                            showOptions = false;
                            getActivity().invalidateOptionsMenu();
                        }
                    }
                    if (need_add) {
                            Log.i(LOG_TAG, "longCliced dont have this element");
                            longClicked.add(new Pair<Integer, View>(i, view));
                            view.setBackgroundColor(Color.LTGRAY);
                    }
                } else {
                    Log.i("Mail", "i = " + i);
                    Log.i("Mail", "l = " + l);
                    Intent intent = new Intent(getActivity(), MailPage.class);
                    Log.i("Mail", "MailPage created");
                    intent.putExtra("title", titles[i]);
                    intent.putExtra("author", authors[i]);
                    intent.putExtra("date", dates[i]);
                    intent.putExtra("text", description[i]);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return authors.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.notificalion_castom_layout, null);
            //ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_title = (TextView)view.findViewById(R.id.title);
            TextView textView_author = (TextView)view.findViewById(R.id.author);
            TextView textView_date = (TextView)view.findViewById(R.id.date);
            TextView textView_description = (TextView)view.findViewById(R.id.description);
            textView_title.setText(titles[i]);
            textView_author.setText(authors[i]);
            textView_date.setText(dates[i]);
            textView_description.setText(description[i]);



            //get first letter of each String item
            String firstLetter = "" + Character.toUpperCase(authors[i].charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(authors[i]);
            //int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            ImageView imageView = (ImageView) view.findViewById(R.id.image_person);
            imageView.setImageDrawable(drawable);


            return view;
        }
    }

}
