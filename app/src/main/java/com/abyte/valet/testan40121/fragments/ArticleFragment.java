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
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.abyte.valet.testan40121.model.Content;
import com.abyte.valet.testan40121.model.artcles.Article;
import com.abyte.valet.testan40121.model.person.Person;

import java.util.LinkedList;

public class ArticleFragment extends Fragment {

    private LinkedList<Content> contents;
    private ArticleAdapter articleAdapter;

    public static Fragment getInstance(LinkedList<Article> articles) {
        Bundle b = new Bundle();
        ProjectsFragment fragment = new ProjectsFragment();
        b.putSerializable(MainActivity.MSG_NAME, articles);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contents = getContents(((MainActivity) getActivity()).person);
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

    private LinkedList<Content> getContents(Person p){
        return /*RetrofitClient.getContentByUser(p, 1)*/ new LinkedList<Content>();
    }
}