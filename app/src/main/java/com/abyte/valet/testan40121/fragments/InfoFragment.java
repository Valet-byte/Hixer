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

public class InfoFragment extends Fragment {

    private TextView textLogo;
    private RecyclerView recyclerView;
    private ContentsAdapter contentsAdapter;

    private ImageView btnBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        Content content = (Content) (getArguments().getSerializable(MainActivity.MSG_NAME));

        textLogo = view.findViewById(R.id.logo_text);
        contentsAdapter = new ContentsAdapter(content, getContext());
        recyclerView = view.findViewById(R.id.rv_list);


        textLogo.setText(content.getInfo());
        recyclerView.setAdapter(contentsAdapter);

        btnBack = view.findViewById(R.id.btn_back_add);

        btnBack.setOnClickListener((View v) -> {

            NavHostFragment.findNavController(this).navigate(R.id.action_infoFragment_to_projectsFragment2);

            });


        return view;
    }
}