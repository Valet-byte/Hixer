package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.ArticleAdapter;
import com.abyte.valet.testan40121.rest.RetrofitClient;

public class ArticleFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;

    private static Integer parcelable;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        articleAdapter = new ArticleAdapter(RetrofitClient.stats, getContext(), this);

        recyclerView = view.findViewById(R.id.article_rv);

        recyclerView.setAdapter(articleAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(manager);

        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            parcelable = manager.findFirstVisibleItemPosition();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        try {
            recyclerView.setScrollingTouchSlop(savedInstanceState.getInt("Position"));
        } catch (Exception ignored){

        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("Position", recyclerView.getScrollState());
        super.onSaveInstanceState(outState);
    }

    public static void invalidate(){
        if (articleAdapter != null)articleAdapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {if (articleAdapter != null) articleAdapter = null;}
}