package com.abyte.valet.testan40121.fragments;

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
import com.abyte.valet.testan40121.model.Content;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ArrayList<Content> contents = (ArrayList<Content>) getArguments().getSerializable(MainActivity.MSG_NAME);

        TextView textLogo = view.findViewById(R.id.logo_text);
        ContentsAdapter contentsAdapter = new ContentsAdapter(contents.get(0), getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rv_list);

        textLogo.setText(contents.get(getArguments().getInt(MainActivity.MSG_POS)).getInfo());
        recyclerView.setAdapter(contentsAdapter);

        ImageView btnBack = view.findViewById(R.id.btn_back_add);

        bundle = new Bundle();
        bundle.putSerializable(MainActivity.MSG_NAME, contents);
        int ID = getArguments().getInt(MainActivity.MSG_ID_BACK_FRAGMENT);

        btnBack.setOnClickListener((View v) -> NavHostFragment.findNavController(this).navigate(ID, bundle));

        return view;
    }
}