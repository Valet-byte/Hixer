package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.activitys.AddActivity;
import com.abyte.valet.testan40121.adapters.PersonContentAdapters;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static PersonContentAdapters adapter;
    private TabLayout tabLayout;
    private Integer typeContent;
    private static ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        TextView login = view.findViewById(R.id.tv_nik);
        List<List<ServerModel>> contents = new ArrayList<>();
        contents.add(RetrofitClient.projectsFromUser);
        contents.add(RetrofitClient.ideasFromUser);
        contents.add(RetrofitClient.statsFromUser);
        if (adapter == null)
        adapter = new PersonContentAdapters(contents, getActivity(), this, R.id.personalFragment2);
        login.setText(MainActivity.person.getName());
        view.findViewById(R.id.btn_out).setOnClickListener(v -> onSaveAndStop());
        view.findViewById(R.id.btn_add).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddActivity.class);
            i.putExtra(MainActivity.MSG_NAME, typeContent);
            startActivity(i);
        });

        tabLayout = view.findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("projects"));
        tabLayout.addTab(tabLayout.newTab().setText("ideas"));
        tabLayout.addTab(tabLayout.newTab().setText("articles"));
        viewPager2 = view.findViewById(R.id.pager2);
        viewPager2.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                typeContent = tab.getPosition() + 1;
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }

    private void onSaveAndStop(){
        PersonDB db = new PersonDB(getActivity());
        db.deletedPerson();
        requireActivity().setResult(2);
        RetrofitClient.dropAll();
        dropAdapter();
        IdeaFragment.dropAdapter();
        ProjectsFragment.dropAdapter();
        ArticleFragment.dropAdapter();
        InfoFragment.dropAdapter();
        getActivity().finish();
    }
    public static void invalidate(){
        if (adapter != null) adapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {
        if (adapter != null) adapter = null;
    }
}