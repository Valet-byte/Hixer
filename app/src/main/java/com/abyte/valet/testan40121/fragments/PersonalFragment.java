package com.abyte.valet.testan40121.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.activitys.RegActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.model.person.Person;

import java.util.Objects;

public class PersonalFragment extends Fragment {

    private Person p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        p = (Person) getArguments().getSerializable(MainActivity.MSG_NAME);
        login.setText(p.getName());

        view.findViewById(R.id.btn_out).setOnClickListener(v ->{

            PersonDB db = new PersonDB(getActivity());
            db.deletedPerson();
            ((MainActivity) getActivity()).finish();
        });

        return view;
    }
}