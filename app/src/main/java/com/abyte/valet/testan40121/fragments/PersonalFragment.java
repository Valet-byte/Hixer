package com.abyte.valet.testan40121.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.activitys.AddActivity;
import com.abyte.valet.testan40121.model.Content;
import com.abyte.valet.testan40121.model.Projects.Project;
import com.google.android.material.tabs.TabLayout;

import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.adapters.SectionsPagerAdapter;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.model.person.Person;

import java.util.LinkedList;
import java.util.List;

public class PersonalFragment extends Fragment {

    private Person p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        p = ((MainActivity)getActivity()).person;
        login.setText(p.getName());

        view.findViewById(R.id.btn_out).setOnClickListener(v ->{
            PersonDB db = new PersonDB(getActivity());
            db.deletedPerson();
            requireActivity().setResult(2);
            getActivity().finish();
        });

        view.findViewById(R.id.btn_add).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddActivity.class);
            startActivity(i);
        });

        return view;
    }

    public static PersonalFragment getInstance(Person projects){
        Bundle b = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        b.putSerializable(MainActivity.MSG_NAME, projects);
        fragment.setArguments(b);
        return fragment;
    }

    private static List<Content> getPersonInfo(Person p){
        return null;
    }

}