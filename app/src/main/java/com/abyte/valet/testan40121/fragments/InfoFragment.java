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
import com.abyte.valet.testan40121.rest.RetrofitClient;

public class InfoFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static ContentsAdapter contentsAdapter;

    public static void invalidate() {
         contentsAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        RetrofitClient.startDownloadByMainStats( getArguments().getString(MainActivity.MSG_NAME));

        TextView textLogo = view.findViewById(R.id.logo_text);

        if(contentsAdapter == null) contentsAdapter = new ContentsAdapter(RetrofitClient.infoList, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setAdapter(contentsAdapter);

        textLogo.setText(getArguments().getString(MainActivity.MSG_NAME));

        ImageView btnBack = view.findViewById(R.id.btn_back_add);

        int ID = getArguments().getInt(MainActivity.MSG_ID_BACK_FRAGMENT);

        btnBack.setOnClickListener((View v) -> {
            RetrofitClient.dropInfoList();
            NavHostFragment.findNavController(this).navigate(ID);
        });

        return view;
    }

    public static void dropAdapter(){
        contentsAdapter = null;
    }
}