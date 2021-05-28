package com.abyte.valet.testan40121.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.List;

public class IdeaAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;

    public IdeaAdapters(Context context, List<ServerModel> contents, Fragment fragment) {
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        this.resFragment = fragment;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final ImageView logo;
        final TextView name, info, tvID;
        final View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_s);
            name = itemView.findViewById(R.id.tv_name);
            logo = itemView.findViewById(R.id.img);
            view = itemView;
            tvID = itemView.findViewById(R.id.tv_id);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ServerModel content = contents.get(position);

        ((MyViewHolder) holder).logo.setImageBitmap(content.getBitmap());
        ((MyViewHolder) holder).info.setText(content.getInfo());
        ((MyViewHolder) holder).name.setText(content.getName());
        ((MyViewHolder) holder).tvID.setText("id: " + content.getID());

        ((MyViewHolder) holder).view.setOnClickListener((View v) -> {
            RetrofitClient.startDownloadByMainStats(content.getName());
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.MSG_NAME, content.getName());
            bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, R.id.ideaFragment2);

            NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);

        });
        if (position == contents.size() - 1){
            RetrofitClient.startDownload();
        }

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}