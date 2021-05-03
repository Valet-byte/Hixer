package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.adapters.ArticleAdapter;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.util.LinkedList;
import java.util.List;

public class ArticleFragment extends Fragment {

    private List<ServerModel> contents;
    private static ArticleAdapter articleAdapter;

    public static Fragment getInstance(LinkedList<ServerModel> articles) {
        Bundle b = new Bundle();
        ProjectsFragment fragment = new ProjectsFragment();
        b.putSerializable(MainActivity.MSG_NAME, articles);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contents = RetrofitClient.stats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        articleAdapter = new ArticleAdapter(contents, getContext(), this);

        RecyclerView recyclerView = view.findViewById(R.id.article_rv);

        recyclerView.setAdapter(articleAdapter);

        return view;
    }

    public static void invalidate(){
        if (articleAdapter != null)articleAdapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {if (articleAdapter != null) articleAdapter = null;}
}