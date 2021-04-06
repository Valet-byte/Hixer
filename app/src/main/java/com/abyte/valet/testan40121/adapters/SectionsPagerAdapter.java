package com.abyte.valet.testan40121.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.fragments.PersonContentFragment;
import com.abyte.valet.testan40121.fragments.PersonalFragment;
import com.abyte.valet.testan40121.model.Content;

public class SectionsPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final class MyHolder extends RecyclerView.ViewHolder{

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
