package com.example.mipro.netschool.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mipro.netschool.Autentification.Autentification;
import com.example.mipro.netschool.Client.APIservice;
import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.LoginResponse;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Log;
import com.example.mipro.netschool.R;
import com.example.mipro.netschool.Settings.Push.Push;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import petrov.kristiyan.colorpicker.ColorPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class Settings extends ListFragment {
    String data[] = {"Уведомление", "Push", "Не беспокоить", "Основные", "Расписание", "Цветовая схема",
            "Пароль", "Пароля", "Подписка", "Подписка1", "Поддержка", "Оценить", "Политика"};
    SettingsAdapter settingsAdapter;
    ArrayList<SettingsElement> resource;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COLOR = "color";
    public static final String APP_PREFERENCES_SESSION_NAME = "session_name";
    public static final String APP_PREFERENCES_COOKIE = "cookie";

    private SharedPreferences mSettings;
    private int current_color;
    ArrayList < String > color_array;
    ColorPicker colorPicker;
    private Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, null);
        getData();
        getActivity().setTitle("Настройки");
        settingsAdapter = new SettingsAdapter(getContext(), resource);
        setListAdapter(settingsAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        color_array = new ArrayList<String>();
        colorPicker = new ColorPicker(getActivity());
        initColors(color_array);
        Log.v("kek");
    }

    private void initColors(ArrayList<String> array) {
        array.add("#EB8B51");
        array.add("#EAA949");
        array.add("#E9BE3F");
        array.add("#E9D53F");
        array.add("#CFCA3F");
        array.add("#A1C869");
        array.add("#34A668");
        array.add("#34AAA0");
        array.add("#3DB7E0");
        array.add("#3995C8");
        array.add("#3575AC");
        array.add("#89949C");
        array.add("#A2599E");
        array.add("#EA6695");
        array.add("#EA5E54");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_COLOR)) {
            current_color = mSettings.getInt(APP_PREFERENCES_COLOR, -1);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 4) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMAIN, new TimeTable());
            ft.addToBackStack("stack");
            ft.commit();
        } else if (position == 2) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMAIN, new NotDisturb());
            ft.addToBackStack("stack");
            ft.commit();
        } else if (position == 5) {
            final ColorPicker colorPicker = new ColorPicker(getActivity());
            colorPicker.disableDefaultButtons(true);
            colorPicker.addListenerButton("Ок", new ColorPicker.OnButtonListener() {
                @Override
                public void onClick(View v, int position, int color) {
                    current_color = color;
                    colorPicker.dismissDialog();
                }
            });
            colorPicker.addListenerButton("Отмена", new ColorPicker.OnButtonListener() {
                @Override
                public void onClick(View v, int position, int color) {
                    colorPicker.dismissDialog();
                }
            });
            colorPicker.setColors(color_array).setDefaultColorButton(current_color).setTitle("").show();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(APP_PREFERENCES_COLOR, current_color);
            editor.apply();
        } else if (position == 7) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMAIN, new Password());
            ft.addToBackStack("stack");
            ft.commit();
        } else if (position == 1) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMAIN, new Push());
            ft.addToBackStack("stack");
            ft.commit();
        } else if (position == 9) {
            getPosts();
        } else if (position == 10) {

        }
    }

    private void getPosts() {
        disposable = Client.getInstance(mSettings.getString(APP_PREFERENCES_SESSION_NAME, ""),mSettings.getString(APP_PREFERENCES_COOKIE, ""))
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<LoginResponse>>() {

                    @Override
                    public void onNext(Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "getPosts", "");
                        } else {
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Client.getInstance().responseHandler("" + response.code(), "getPosts", jObjError.getString("error"));
                                } else {
                                    Client.getInstance().responseHandler("" + response.code(), "getPosts", "");
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
                        Log.v("kek");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    void getData() {
        resource = new ArrayList<SettingsElement>();
        resource.add(new SettingsElement("УВЕДОМЛЕНИЯ", null , -1));
        resource.add(new SettingsElement("Push-уведомления", "kek" , 1));
        resource.add(new SettingsElement("Не беспокоить", "kek" , 1));
        resource.add(new SettingsElement("ОСНОВНЫЕ", null , -1));
        resource.add(new SettingsElement("Расписание", "kek" , 1));
        resource.add(new SettingsElement("Цветовая схема", "kek" , 1));
        resource.add(new SettingsElement("ПАРОЛЬ", null , -1));
        resource.add(new SettingsElement("Изменение пароля",  "kek", 1));
        resource.add(new SettingsElement("ПОДПИСКА", null , -1));
        resource.add(new SettingsElement("Подписка", "kek" , 1));
        resource.add(new SettingsElement("Тех. поддержка", "kek" , 1));
        resource.add(new SettingsElement("Оцените приложение", "kek" , 1));
        resource.add(new SettingsElement("Политика конфиденциальности", "kek" , 1));
    }

    private class SettingsAdapter extends ArrayAdapter<SettingsElement> {
        Context context;
        ArrayList<SettingsElement> resource;

        public SettingsAdapter(Context context, ArrayList<SettingsElement> resource) {
            super(context, R.layout.res_list_elem, resource);
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (belong(i)) {
                view = getLayoutInflater().inflate(R.layout.setting_section, null);
                TextView textView = view.findViewById(R.id.titleSectionList);
                textView.setText(resource.get(i).getTitle());
            } else {
                view = getLayoutInflater().inflate(R.layout.settings_elem, null);
                ImageView icon = view.findViewById(R.id.iconList);
                if (i == 1) {
                    icon.setImageResource(R.drawable.ic_notifications_black_24dp);
                }
                if (i == 2) {
                    icon.setImageResource(R.drawable.ic_do_not_disturb_alt_black_24dp);
                }
                if (i == 4) {
                    icon.setImageResource(R.drawable.ic_timetable1);
                }
                if (i == 5) {
                    icon.setImageResource(R.drawable.ic_color_lens_black_24dp);
                }
                if (i == 7) {
                    icon.setImageResource(R.drawable.ic_vpn_key_black_24dp);
                }
                if (i == 9) {
                    icon.setImageResource(R.drawable.ic_star_black_24dp);
                }
                if (i == 10) {
                    icon.setImageResource(R.drawable.ic_build_black_24dp);
                }
                if (i == 11) {
                    icon.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                if (i == 12) {
                    icon.setImageResource(R.drawable.ic_copyright_black_24dp);
                }
                TextView descr = view.findViewById(R.id.titleOfSetting);
                descr.setText(resource.get(i).getTitle());
                LinearLayout linearLayout = view.findViewById(R.id.deleteLayOut);
            }
            return view;
        }

        void setImage() {

        }

        @Override
        public boolean isEnabled(int position) {
            if (belong(position)) {
                return false;
            } else {
                return true;
            }
        }

        boolean belong(int i) {
            if (i == 0 || i == 3 || i == 6 || i == 8) {
                return true;
            } else {
                return false;
            }
        }
    }
}
