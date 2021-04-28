package com.abyte.valet.testan40121.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.activitys.AddActivity;
import com.abyte.valet.testan40121.adapters.PersonContentAdapters;
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.abyte.valet.testan40121.model.Content;

import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PersonalFragment extends Fragment {
    private RecyclerView recyclerView;
    private static PersonContentAdapters adapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        recyclerView = view.findViewById(R.id.rv_person_content);
        adapters = new PersonContentAdapters(RetrofitClient.projects, getActivity(), this);
        recyclerView.setAdapter(adapters);
        login.setText(MainActivity.person.getName());

        view.findViewById(R.id.btn_out).setOnClickListener(v ->{
            PersonDB db = new PersonDB(getActivity());
            db.deletedPerson();
            requireActivity().setResult(2);
            getActivity().finish();
        });

        view.findViewById(R.id.btn_add).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddActivity.class);
            i.putExtra(MainActivity.MSG_NAME, 1);
            startActivity(i);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidate();
    }

    private void getPersonInfo(Person p, LinkedList<ServerModel> models, Integer type){
        RetrofitClient.getContentByUser(MainActivity.person, type, models);
    }

    public static void invalidate(){
        adapters.notifyDataSetChanged();
    }

}