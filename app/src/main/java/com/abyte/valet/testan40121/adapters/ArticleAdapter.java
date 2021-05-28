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
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.io.Serializable;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;

    public ArticleAdapter(List<ServerModel> contents, Context context, Fragment resFragment){
        this.resFragment = resFragment;
        inflater = LayoutInflater.from(context);
        this.contents = contents;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageView;
        final TextView info, name, tvID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            info = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
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

            ((MyViewHolder) holder).name.setText(content.getName());
            ((MyViewHolder) holder).info.setText(content.getInfo());
            ((MyViewHolder) holder).imageView.setImageBitmap(content.getBitmap());
            ((MyViewHolder) holder).tvID.setText("id: " + content.getID());

            ((MyViewHolder) holder).imageView.setOnClickListener((View v) -> {
                RetrofitClient.startDownloadByMainStats(content.getName());

                Bundle bundle = new Bundle();

                bundle.putString(MainActivity.MSG_NAME, content.getName());
                bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, R.id.articleFragment2);
                NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);
            }
            );

            if (position == contents.size() - 1){
                RetrofitClient.startDownload();
            }
        }


    @Override
    public int getItemCount() {
        return contents.size();
    }
}
