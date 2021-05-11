package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.ContentAdapter;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.List;

public class ProjectsFragment extends Fragment {

    public static ContentAdapter contentAdapter;
    private RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        recyclerView = view.findViewById(R.id.rv_content);

        contentAdapter = new ContentAdapter(getContext(), RetrofitClient.projects, this, R.id.projectsFragment2);

        recyclerView.setAdapter(contentAdapter);

        return view;
    }

    public static void invalidate() {
        if (contentAdapter != null) contentAdapter.notifyDataSetChanged();
    }

    public static void dropAdapter() {if (contentAdapter != null) contentAdapter = null;}

}