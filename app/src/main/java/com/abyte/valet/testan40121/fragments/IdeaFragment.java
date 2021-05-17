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

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.AddActivity;
import com.abyte.valet.testan40121.adapters.IdeaAdapters;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.List;
import java.util.Objects;

import static com.abyte.valet.testan40121.activitys.MainActivity.BUNDLE_RECYCLER_LAYOUT;

public class IdeaFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static IdeaAdapters ideaAdapters;
    private List<ServerModel> contents;
    private RecyclerView recyclerView;

    private static Integer parcelable;

    public static void invalidate() {
        if (ideaAdapters != null) ideaAdapters.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contents = RetrofitClient.ideas;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_idea, container, false);

        recyclerView = view.findViewById(R.id.rv_content);
        ideaAdapters = new IdeaAdapters(getContext(), contents, this);
        recyclerView.setAdapter(ideaAdapters);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            parcelable = manager.findFirstVisibleItemPosition();
        });

        return view;
    }
    public static void dropAdapter() {if (ideaAdapters != null) ideaAdapters = null;}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(parcelable != null)
        {
            recyclerView.scrollToPosition(parcelable);
        }
    }
}