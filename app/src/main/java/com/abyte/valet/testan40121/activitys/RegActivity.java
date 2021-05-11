package com.abyte.valet.testan40121.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.SectionsPagerAdapter;
import com.abyte.valet.testan40121.fragments.PlaceholderFragment;
import com.google.android.material.tabs.TabLayout;


public class RegActivity extends AppCompatActivity {

    public static final int REQUEST_ICON = 5;
    private ImageView imageViewIcon;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            PlaceholderFragment.setIcon(uri, this);
            Log.i(AddActivity.TAG, "onActivityResult: " + uri.toString());
            imageViewIcon.setImageURI(uri);
        }
        else if (resultCode != 2) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    public void setIcon(View v){
        imageViewIcon = (ImageView) v;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_ICON);
    }
}
