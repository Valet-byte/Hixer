package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.ContentsAdapter;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.LinkedList;

public class InfoFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static ContentsAdapter contentsAdapter;

    public static void invalidate() {
        if (contentsAdapter != null) contentsAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView textLogo = view.findViewById(R.id.logo_text);
        contentsAdapter = new ContentsAdapter(RetrofitClient.infoList, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rv_list);

        textLogo.setText(((ServerModel) getArguments().getSerializable(MainActivity.MSG_NAME)).getName());
        recyclerView.setAdapter(contentsAdapter);

        ImageView btnBack = view.findViewById(R.id.btn_back_add);

        int ID = getArguments().getInt(MainActivity.MSG_ID_BACK_FRAGMENT);

        btnBack.setOnClickListener((View v) -> {
            RetrofitClient.dropInfoList();
            NavHostFragment.findNavController(this).navigate(ID);
        });

        return view;
    }
    public static void dropAdapter() {if (contentsAdapter != null) contentsAdapter = null;}
}