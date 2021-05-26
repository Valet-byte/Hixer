package com.abyte.valet.testan40121.adapters;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.util.List;
import java.util.Objects;

import static com.abyte.valet.testan40121.activitys.AddActivity.TAG;

public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView info, name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_name);
            name = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
        }
    }

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Context context;
    private final Integer ID;

    public FindAdapter(Context context, List<ServerModel> contents, Integer idThisFragment) {
        this.context = context;
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        ID = idThisFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServerModel content = contents.get(position);

        ((MyViewHolder) holder).name.setText(content.getInfo());
        ((MyViewHolder) holder).info.setText(content.getName());
        ((MyViewHolder) holder).imageView.setImageBitmap(content.getBitmap());

        ((MyViewHolder) holder).imageView.setOnClickListener((View v) -> {
                    Log.i(TAG, "onBindViewHolder: " + content.toString());
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.MSG_NAME, content.getName());
                    bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, ID);
            NavHostFragment.findNavController(Objects.requireNonNull(((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)))
                    .navigate(R.id.infoFragment, bundle);
                }
        );

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
