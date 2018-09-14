package com.example.mipro.netschool.Autentification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.School;
import com.example.mipro.netschool.Client.Pojo.School_;
import com.example.mipro.netschool.R;
import com.example.mipro.netschool.Resources.Group;
import com.example.mipro.netschool.Resources.GroupList;
import com.example.mipro.netschool.Service.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class SchoolList extends AppCompatActivity {

    private Disposable disposable;
    private ArrayList<School_> arrayList;
    private ListView listView;
    private School school;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoollist);
        ListView listView = findViewById(R.id.schoolList);
        getSchoolList();
    }

    protected void initList() {
        arrayList = new ArrayList<>();
        for (School_ school_: school.getSchools()) {
            arrayList.add(school_);
        }
        SchoolAdapter schoolAdapter = new SchoolAdapter(this, arrayList);
        ListView listView = findViewById(R.id.schoolList);
        listView.setAdapter(schoolAdapter);
        Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Autentification.class);
                School_ school_ = (School_)listView.getItemAtPosition(i);;
                intent.putExtra("school", school_);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.progressBar3).setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        listView = findViewById(R.id.schoolList);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    ArrayList<School_> templist = new ArrayList<>();

                    for (School_ school_:arrayList) {
                        if (school_.getName().toLowerCase().contains(newText.toLowerCase())) {
                            templist.add(school_);
                        }
                    }
                    SchoolAdapter schoolAdapter = new SchoolAdapter(SchoolList.this, templist);
                    listView.setAdapter(schoolAdapter);
                    return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
                            school = response.body();
                            initList();
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

    private class SchoolAdapter extends ArrayAdapter<School_> {
        ArrayList<School_> school;
        Context context;

        public SchoolAdapter(Context context, ArrayList<School_> school) {
            super(context, R.layout.section, school);
            this.school = school;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.section, null);
            TextView textView = view.findViewById(R.id.title_of_section);
            textView.setText(school.get(position).getName());
            return view;
        }
    }
}
