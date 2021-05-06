package com.abyte.valet.testan40121.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
    private AppCompatButton btnProjects, btnIdeas, btnArticles;
    private RecyclerView recyclerView;
    private byte typeContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        recyclerView = view.findViewById(R.id.rv_person_content);
        adapter = new PersonContentAdapters(RetrofitClient.projectsFromUser, getActivity(), this);
        recyclerView.setAdapter(adapter);
        login.setText(MainActivity.person.getName());
        btnProjects = view.findViewById(R.id.btn_project);
        btnIdeas = view.findViewById(R.id.btn_ideas);
        btnArticles = view.findViewById(R.id.btn_articles);
        typeContent = 1;
        btnProjects.setOnClickListener((v)->{
            typeContent = 1;
            btnIdeas.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnArticles.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnProjects.setTextColor(getActivity().getResources().getColor(R.color.black));
            recyclerView.setAdapter(new PersonContentAdapters(RetrofitClient.projectsFromUser, getActivity(), PersonalFragment.this));
        });
        btnIdeas.setOnClickListener((v)->{
            typeContent = 2;
            btnProjects.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnArticles.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnIdeas.setTextColor(getActivity().getResources().getColor(R.color.black));
            recyclerView.setAdapter(new PersonContentAdapters(RetrofitClient.ideasFromUser, getActivity(), PersonalFragment.this));
        });
        btnArticles.setOnClickListener((v)->{
            typeContent = 3;
            btnIdeas.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnProjects.setTextColor(getActivity().getResources().getColor(R.color.my_blue));
            btnArticles.setTextColor(getActivity().getResources().getColor(R.color.black));
            recyclerView.setAdapter(new PersonContentAdapters(RetrofitClient.statsFromUser, getActivity(), PersonalFragment.this));
        });

        view.findViewById(R.id.btn_out).setOnClickListener(v -> onSaveAndStop());

        view.findViewById(R.id.btn_add).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddActivity.class);
            i.putExtra(MainActivity.MSG_NAME, typeContent);
            startActivity(i);
        });

        return view;
    }

    private void onSaveAndStop(){
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
    }
    public static void invalidate(){
        if (adapter != null) adapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {
        if (adapter != null) adapter = null;
    }
}