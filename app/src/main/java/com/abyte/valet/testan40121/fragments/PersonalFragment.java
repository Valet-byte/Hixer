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
import com.abyte.valet.testan40121.rest.RetrofitClient;

import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.db.PersonDB;

public class PersonalFragment extends Fragment {
    private static PersonContentAdapters adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        RecyclerView recyclerView = view.findViewById(R.id.rv_person_content);
        adapter = new PersonContentAdapters(RetrofitClient.projectsFromUser, getActivity(), this);
        recyclerView.setAdapter(adapter);
        login.setText(MainActivity.person.getName());

        view.findViewById(R.id.btn_out).setOnClickListener(v ->{
            PersonDB db = new PersonDB(getActivity());
            db.deletedPerson();
            requireActivity().setResult(2);
            RetrofitClient.allDowngrade();
            dropAdapter();
            IdeaFragment.dropAdapter();
            ProjectsFragment.dropAdapter();
            ArticleFragment.dropAdapter();
            InfoFragment.dropAdapter();
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


    public static void invalidate(){
        if (adapter != null) adapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {if (adapter != null) adapter = null;}
}