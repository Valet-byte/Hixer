package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

    private static ArticleAdapter articleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        if (articleAdapter == null) articleAdapter = new ArticleAdapter(RetrofitClient.stats, getContext(), this);

        RecyclerView recyclerView = view.findViewById(R.id.article_rv);

        recyclerView.setAdapter(articleAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public static void invalidate(){
        if (articleAdapter != null)articleAdapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {if (articleAdapter != null) articleAdapter = null;}
}