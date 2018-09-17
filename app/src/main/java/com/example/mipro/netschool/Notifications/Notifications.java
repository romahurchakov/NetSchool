package com.example.mipro.netschool.Notifications;

import com.example.mipro.netschool.Client.Pojo.Notification_;
import com.example.mipro.netschool.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.Notification;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Notifications extends Fragment{

    private Disposable disposable;
    private Notification notification;

    /*String[] authors = {"autor", "Krupnova hehe","autor", "autor", "autor", "autor", "autor", "autor","autor" };
    String[] titles = {"title", "Дешевые ауди" ,"autor", "autor", "autor", "autor", "autor", "autor", "autor"};
    String[] dates = {"10.10.2010", "Today", "autor", "autor", "autor", "autor", "autor", "autor", "autor"};
    String[] descriptions = {" description", "Приму в дар Audi TTS или Audi S5. В награду получите поцелуй в щечку и то если вы будете красивым, а не уродом каким то так что следите за собой засранцы", "autor", "autor", "autor", "autor", "autor", "autor","autor"};
    String[] file_names = {"file name.doxc",  "Описание всех ауди что я люблю скачть.doxc", "autor", "autor", "autor", "autor", "autor", "autor","autor"};
*/

    public Notifications() {
        // Required empty public constructor
        Log.i("Notification", "created notificatoin page");
        notification = new Notification();
    }


    private void getNotifiction(ListView listView) {
        disposable = Client.getInstance(getContext())
                .getNotification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<Notification>>() {

                    @Override
                    public void onNext(Response<Notification> response) {
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "getNotifications", "", getContext());

                            Log.i("notification", "I received notification");
                            notification = response.body();
                            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        } else {
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Client.getInstance().responseHandler("" + response.code(), "getNotifications", jObjError.getString("error"),getContext());
                                } else {
                                    Client.getInstance().responseHandler("" + response.code(), "getNotifications", "",getContext());
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
                        //Log.v("view", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("view", "oncreateview");
        View view = inflater.inflate(R.layout.notifications, container, false);
        ListView listView = (ListView) view.findViewById(R.id.mainMenu);

        Log.i("view", "created list mainMenu");
        getNotifiction(listView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Notification", "i = " + i);
                Log.i("Notification", "l = " + l);
                Notification_ notifI = notification.getNotifications().get(i);
                Intent intent = new Intent(getActivity(), NotificationPage.class);
                intent.putExtra("title", notifI.getTitle());
                intent.putExtra("author", notifI.getAuthor());
                intent.putExtra("date", notifI.getDate());
                intent.putExtra("description", notifI.getMessage());
                intent.putExtra("file_name", notifI.getFile());
                startActivity(intent);


            }
        });
        return view;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {

            if (notification.getNotifications() == null){
                Log.i("getCount", "0");
                return 0;
            }
            Log.i("getCount", "" + notification.getNotifications().size());
            return notification.getNotifications().size();
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
            Notification_ notifI = notification.getNotifications().get(i);
            TextView textView_title = (TextView)view.findViewById(R.id.title);
            TextView textView_author = (TextView)view.findViewById(R.id.author);
            TextView textView_date = (TextView)view.findViewById(R.id.date);
            TextView textView_description = (TextView)view.findViewById(R.id.description);
            TextView textView_file = (TextView)view.findViewById(R.id.file_name);
            textView_title.setText(notifI.getTitle());
            textView_author.setText(notifI.getAuthor());
            textView_date.setText(notifI.getDate());
            textView_description.setText(notifI.getMessage());
            Log.i("notifI: ", notifI.toString());
            Log.i(" author: ", notifI.getAuthor());



            //get first letter of each String item
            String firstLetter = "" + Character.toUpperCase(notifI.getAuthor().charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(notifI.getAuthor());
            //int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            ImageView imageView = (ImageView) view.findViewById(R.id.image_person);
            imageView.setImageDrawable(drawable);


            return view;
        }
    }

}
