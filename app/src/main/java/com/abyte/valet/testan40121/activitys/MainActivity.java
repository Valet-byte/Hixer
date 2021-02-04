package com.abyte.valet.testan40121.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

//PPPPPPPPPPPPPPPPPPPPPPPPPOMAAAAAAAA
        projectFragment = new ProjectsFragment();
        ideaFragment = new IdeaFragment();
        articleFragment = new ArticleFragment();
        personFragment = new PersonalFragment();

        toolbar = findViewById(R.id.my_tool_bar);

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.project_fragment, projectFragment).commit();

        this.setSupportActionBar(toolbar);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.btn_project:
                        getSupportFragmentManager().beginTransaction().replace(R.id.project_fragment, projectFragment).commit();
                        break;
                    case R.id.btn_idea:
                        getSupportFragmentManager().beginTransaction().replace(R.id.project_fragment, ideaFragment).commit();
                        break;
                    case R.id.btn_stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.project_fragment, articleFragment).commit();
                        break;
                    case R.id.btn_person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.project_fragment, personFragment).commit();

                }

                return true;
            }
        });
        
    }

}