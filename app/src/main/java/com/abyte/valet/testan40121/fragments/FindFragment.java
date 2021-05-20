package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.FindAdapter;
import com.abyte.valet.testan40121.rest.RetrofitClient;

public class FindFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FindAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_list);

        if (adapter == null) adapter = new FindAdapter(getActivity(), RetrofitClient.findList, R.id.findFragment);

        recyclerView.setAdapter(adapter);

        return view;
    }

    public static void invalidate(){
        if (adapter != null) adapter.notifyDataSetChanged();

    }

}