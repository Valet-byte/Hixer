package com.abyte.valet.testan40121.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abyte.valet.testan40121.R;


public class PersonContentFragment extends Fragment {

    public Integer text = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person_content, container, false);

        ((TextView) view.findViewById(R.id.tv_person_content)).setText(text.toString());

        return view;
    }

    public static PersonContentFragment newInstance(Integer text){
        PersonContentFragment fragment = new PersonContentFragment();
        fragment.text = text;
        return fragment;
    }

}