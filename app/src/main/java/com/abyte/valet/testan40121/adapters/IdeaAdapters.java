package com.abyte.valet.testan40121.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.LinkedList;
import java.util.List;

public class IdeaAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;
    private final Context context;

    public IdeaAdapters(Context context, List<ServerModel> contents, Fragment fragment) {
        this.context = context;
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        this.resFragment = fragment;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final ImageView logo;
        final TextView name, info;
        final LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_info);
            name = itemView.findViewById(R.id.tv_author);
            logo = itemView.findViewById(R.id.iv_logo);
            layout = itemView.findViewById(R.id.ll_main);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_idea_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ServerModel content = contents.get(position);

        ((MyViewHolder) holder).logo.setImageBitmap(content.getBitmap());
        ((MyViewHolder) holder).info.setText(content.getInfo());
        ((MyViewHolder) holder).name.setText(content.getName());

        ((MyViewHolder) holder).layout.setOnClickListener((View v) -> {
            RetrofitClient.startDownloadByMainStats(content.getName());
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainActivity.MSG_NAME, content);
            bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, R.id.ideaFragment2);
            bundle.putInt(MainActivity.MSG_POS, position);

            NavHostFragment.findNavController(resFragment).popBackStack(R.id.infoFragment, true);
            NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);

        });
        if (position == contents.size()){
            RetrofitClient.startDownload((Activity) context);
        }

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}