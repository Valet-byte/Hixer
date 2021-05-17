package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.ProjectAdapter;
import com.abyte.valet.testan40121.rest.RetrofitClient;

public class ProjectsFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static ProjectAdapter projectAdapter;
    private RecyclerView recyclerView;
    private static Integer parcelable;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        recyclerView = view.findViewById(R.id.rv_content);

        projectAdapter = new ProjectAdapter(getContext(), RetrofitClient.projects, this, R.id.projectsFragment2);

        recyclerView.setAdapter(projectAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(manager);

        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> parcelable = manager.findFirstVisibleItemPosition());

        return view;
    }

    public static void invalidate() {
        if (projectAdapter != null) projectAdapter.notifyDataSetChanged();
    }

    public static void dropAdapter() {if (projectAdapter != null) projectAdapter = null;}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(parcelable != null)
        {
            recyclerView.scrollToPosition(parcelable);
        }
    }

}