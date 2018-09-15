package com.example.mipro.netschool.Resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mipro.netschool.Client.Client;
import com.example.mipro.netschool.Client.Pojo.File;
import com.example.mipro.netschool.Client.Pojo.File_;
import com.example.mipro.netschool.Client.Pojo.Resources;
import com.example.mipro.netschool.Client.Pojo.Subgroup;
import com.example.mipro.netschool.Service.Log;
import com.example.mipro.netschool.R;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GroupList extends ListFragment {

    ArrayList<Group> groupList = null;
    GroupList.GroupAdapter groupAdapter;
    private Disposable disposable;
    private Resources resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Учебные материалы");
        View view = inflater.inflate(R.layout.base_fragment, null);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Fragment fragment = new SubGroupsList();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Group", groupList.get(position));
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMAIN, fragment);
        ft.addToBackStack("stack");
        ft.commit();
    }

    void getData() {
        ArrayList <Group> groupListDontHave = new ArrayList<Group>();
        ArrayList <Group> groupListHave = new ArrayList<Group>();
        for(int i = 0 ; i < resources.getData().size(); i ++) {
            if (resources.getData().get(i).getSubgroups().size() > 0) {
                groupListHave.add(new Group(resources.getData().get(i).getGroupTitle(), true, null, null));
                for (Subgroup subgroup:resources.getData().get(i).getSubgroups()) {
                    ArrayList<com.example.mipro.netschool.Resources.File> files = new ArrayList<com.example.mipro.netschool.Resources.File>();

                    for (File_ file_: subgroup.getFiles()) {
                        files.add(new com.example.mipro.netschool.Resources.File
                                (file_.getName(), file_.getLink(),getImage(file_.getLink()), true, subgroup.getSubgroupTitle() ));
                    }

                    groupListHave.add(new Group(subgroup.getSubgroupTitle(), false, files, null));
                }

            } else {

                ArrayList<com.example.mipro.netschool.Resources.File> files = new ArrayList<com.example.mipro.netschool.Resources.File>();

                for (File file: resources.getData().get(i).getFiles()) {
                    int id = getImage(file.getLink());
                    files.add(new com.example.mipro.netschool.Resources.File
                            (file.getName(),file.getLink(),getImage(file.getLink()), true, null));
                }

                groupListDontHave.add(new Group(resources.getData().get(i).getGroupTitle(), false, files, null));
            }
        }
        groupList = new ArrayList<Group>();
        groupList.add(new Group("Учебные материалы", true, null, null));
        groupList.addAll(groupListDontHave);
        groupList.addAll(groupListHave);
        getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        groupAdapter = new GroupList.GroupAdapter(getContext(), groupList);
        this.setListAdapter(groupAdapter);
    }

    void getRosources() {
        disposable = Client.getInstance(this.getActivity())
                .getResources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<Resources>>() {

                    @Override
                    public void onNext(Response<Resources> response) {
                        if (response.isSuccessful()) {
                            Client.getInstance().responseHandler("" + response.code(), "getResources", "", getContext());
                            resources = response.body();
                            getData();
                        } else {
                            try {
                                if (response.code() == 400) {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Client.getInstance().responseHandler("" + response.code(), "getResources", jObjError.getString("error"),getContext());
                                } else {
                                    Client.getInstance().responseHandler("" + response.code(), "getResources", "",getContext());
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
                        //Обработчик системных ошибок
                        //Сделаю потом нормальный обработчик, пока не вылезали такие ошибки, не могу подебажить
                        Log.v(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (groupAdapter != null) {
            this.groupAdapter.clear();
            this.groupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        getActivity().setTitle("NetSchool");
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRosources();
    }

    int getImage(String fileName) {
        String ext = fileName.toLowerCase().substring(fileName.lastIndexOf("."));
        switch (ext) {
            case ".pdf":
                return R.drawable.ic_pdf;
            case ".doc":
                return R.drawable.ic_doc;
            case ".docx":
                return R.drawable.ic_doc;
            default:
                return R.drawable.ic_file;
        }
    }

    private class GroupAdapter extends ArrayAdapter<Group> {
        ArrayList<Group> groups;
        Context context;

        public GroupAdapter(Context context, ArrayList<Group> groups) {
            super(context, R.layout.section, groups);
            this.groups = groups;
            this.context = context;
        }

        @Override
        public boolean isEnabled(int position) {
            Group group = groups.get(position);
            if (group.isHaveSub() && group.getSubGroups() == null && group.getFiles() == null) {
                return false;
            } else {
                return true;
            }
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Group group = groups.get(i);
            if (group.isHaveSub() && group.getSubGroups() == null && group.getFiles() == null) {
                view = getLayoutInflater().inflate(R.layout.group_name, null);
                TextView  textView = view.findViewById(R.id.group_name);
                textView.setText(group.getTitle());
            } else {
                view = getLayoutInflater().inflate(R.layout.section, null);
                TextView title = (TextView) view.findViewById(R.id.title_of_section);
                title.setText(group.getTitle());
            }
            return view;
        }

    }


}


