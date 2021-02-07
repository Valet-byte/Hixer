package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.CustomLayoutManager.CustomLayoutManager;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.adapters.ContentAdapter;
import com.abyte.valet.testan40121.adapters.IdeaAdapters;
import com.abyte.valet.testan40121.model.Content;

import java.util.ArrayList;

public class IdeaFragment extends Fragment {

    private IdeaAdapters ideaAdapters;
    private ArrayList<Content> contents;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contents = (ArrayList<Content>) getArguments().getSerializable(MainActivity.MSG_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_idea, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_content);
        CustomLayoutManager layoutManager = new CustomLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        ideaAdapters = new IdeaAdapters(getContext(), contents, this);
        recyclerView.setAdapter(ideaAdapters);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();

                contents.remove(pos);

                ideaAdapters.notifyDataSetChanged();
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
}