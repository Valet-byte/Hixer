package com.abyte.valet.testan40121.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import android.widget.ImageView;
import android.widget.TextView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.db.PersonDB;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class PersonalFragment extends Fragment {

    private static PersonContentAdapters adapter;
    private static Integer typeContent;

    public static Integer getType() {
        return typeContent;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
                i.putExtra(MainActivity.MSG_NAME, getType());
                startActivity(i);
            });
        ImageView icon = view.findViewById(R.id.iv_icon);
            icon.setImageBitmap(MainActivity.person.getPhoto());

            TabLayout tabLayout = view.findViewById(R.id.tab);

            ViewPager2 viewPager2 = view.findViewById(R.id.pager2);
            viewPager2.setAdapter(adapter);


            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) { typeContent = position + 1; }
            });

            TabLayoutMediator mediator =  new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                switch (position){
                    case 0:{
                        tab.setText("projects");
                        break;
                    }
                    case 1:{
                        tab.setText("ideas");
                        break;
                    }
                    case 2:{
                        tab.setText("articles");
                        break;
                    }
                }
            });
            mediator.attach();
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
        requireActivity().finish();
    }
    public static void invalidate(){
        if (adapter != null) adapter.notifyDataSetChanged();
    }
    public static void dropAdapter() {
        if (adapter != null) adapter = null;
    }
}