package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.IdeaAdapters;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.LinkedList;
import java.util.List;

public class IdeaFragment extends Fragment {

    private static IdeaAdapters ideaAdapters;
    private List<ServerModel> contents;

    public static void invalidate() {
        if (ideaAdapters != null) ideaAdapters.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contents = RetrofitClient.ideas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_idea, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_content);
        ideaAdapters = new IdeaAdapters(getContext(), contents, this);
        recyclerView.setAdapter(ideaAdapters);

        return view;
    }
    public static void dropAdapter() {if (ideaAdapters != null) ideaAdapters = null;}

}