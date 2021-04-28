package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.adapters.ContentAdapter;
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.abyte.valet.testan40121.model.Content;
import com.abyte.valet.testan40121.model.Projects.Project;
import com.abyte.valet.testan40121.model.person.Person;

import java.util.LinkedList;

public class ProjectsFragment extends Fragment {

    public ContentAdapter contentAdapter;
    private LinkedList<Content> contents;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        recyclerView = view.findViewById(R.id.rv_content);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        contents = getContents(((MainActivity) getActivity()).person);
        if (contents != null){
            contentAdapter = new ContentAdapter(getContext(), contents, this);
            recyclerView.setAdapter(contentAdapter);
        }
    }

    public static ProjectsFragment getInstance(LinkedList<Project> projects){
        Bundle b = new Bundle();
        ProjectsFragment fragment = new ProjectsFragment();
        b.putSerializable(MainActivity.MSG_NAME, projects);
        fragment.setArguments(b);
        return fragment;
    }

    private LinkedList<Content> getContents(Person p){
        return null;
    }
}