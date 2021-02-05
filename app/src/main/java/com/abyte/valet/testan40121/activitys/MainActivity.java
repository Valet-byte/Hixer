package com.abyte.valet.testan40121.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.abyte.valet.testan40121.CustomLayoutManager.CustomLayoutManager;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.ContentAdapter;
import com.abyte.valet.testan40121.fragments.ArticleFragment;
import com.abyte.valet.testan40121.fragments.IdeaFragment;
import com.abyte.valet.testan40121.fragments.PersonalFragment;
import com.abyte.valet.testan40121.fragments.ProjectsFragment;
import com.abyte.valet.testan40121.model.Content;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private BottomNavigationView navigationView;

    public static final String MSG_NAME = "Msg";

    private Fragment projectFragment, ideaFragment, articleFragment, personFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectFragment = new ProjectsFragment();
        ideaFragment = new IdeaFragment();
        articleFragment = new ArticleFragment();
        personFragment = new PersonalFragment();

        toolbar = findViewById(R.id.my_tool_bar);

        navigationView = findViewById(R.id.bottomNavigationView);

        this.setSupportActionBar(toolbar);

        navigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
                switch (item.getItemId()){
                    case R.id.btn_project:
                        NavHostFragment.findNavController(projectFragment).navigate(R.id.projectsFragment2);
                        break;
                    case R.id.btn_idea:
                        NavHostFragment.findNavController(ideaFragment).navigate(R.id.ideaFragment2);
                        break;
                    case R.id.btn_stats:
                        NavHostFragment.findNavController(articleFragment).navigate(R.id.articleFragment);
                        break;
                    case R.id.btn_person:
                        NavHostFragment.findNavController(personFragment).navigate(R.id.personalFragment);

                }
                return true;
            }
        );
        
    }

}