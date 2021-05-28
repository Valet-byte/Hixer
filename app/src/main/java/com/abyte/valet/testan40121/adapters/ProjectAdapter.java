package com.abyte.valet.testan40121.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.List;

import static com.abyte.valet.testan40121.activitys.AddActivity.TAG;

public class ProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView info, name, tvID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_name);
            name = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
            tvID = itemView.findViewById(R.id.tv_id);
        }
    }

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;
    private final Integer ID;

    public ProjectAdapter(Context context, List<ServerModel> contents, Fragment fragment, Integer idThisFragment) {
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        this.resFragment = fragment;
        ID = idThisFragment;
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

        ((MyViewHolder) holder).name.setText(content.getInfo());
        ((MyViewHolder) holder).info.setText(content.getName());
        ((MyViewHolder) holder).imageView.setImageBitmap(content.getBitmap());
        ((MyViewHolder) holder).tvID.setText("id: " + content.getID());

        ((MyViewHolder) holder).imageView.setOnClickListener((View v) -> {
            Log.i(TAG, "onBindViewHolder: " + content.toString());
                    Bundle bundle = new Bundle();

                    bundle.putString(MainActivity.MSG_NAME, content.getName());
                    bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, ID);

                    NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);
                }
        );



        if (position == contents.size() - 1) {
            RetrofitClient.startDownload();
        }

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
