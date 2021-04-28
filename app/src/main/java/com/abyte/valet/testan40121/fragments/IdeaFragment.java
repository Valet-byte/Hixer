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
import com.abyte.valet.testan40121.adapters.IdeaAdapters;
import com.abyte.valet.testan40121.cl_se.RetrofitClient;
import com.abyte.valet.testan40121.model.Content;
import com.abyte.valet.testan40121.model.ideas.Idea;
import com.abyte.valet.testan40121.model.person.Person;

import java.util.LinkedList;

public class IdeaFragment extends Fragment {

    private IdeaAdapters ideaAdapters;
    private LinkedList<Content> contents;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contents = getContents(((MainActivity)getActivity()).person);
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

    public static IdeaFragment getInstance(LinkedList<Idea> ideas){
        Bundle b = new Bundle();
        IdeaFragment fragment = new IdeaFragment();
        b.putSerializable(MainActivity.MSG_NAME, ideas);
        fragment.setArguments(b);
        return fragment;
    }

    private LinkedList<Content> getContents(Person p){
        return /*RetrofitClient.getContentByUser(p, 3)*/ new LinkedList<Content>();
    }
}