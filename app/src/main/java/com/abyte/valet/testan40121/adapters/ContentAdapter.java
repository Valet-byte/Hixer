package com.abyte.valet.testan40121.adapters;

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

import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.Content;

import java.io.Serializable;
import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView name, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            name = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
        }
    }

    private final ArrayList<Content> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;

    public ContentAdapter(Context context, ArrayList<Content> contents, Fragment fragment) {
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        this.resFragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Content content = contents.get(position);
        if (content.getKat() == 1) {
            ((MyViewHolder) holder).name.setText(content.getInfo());
            ((MyViewHolder) holder).author.setText(content.getAuthor());
            ((MyViewHolder) holder).imageView.setImageResource(content.getImg());

            ((MyViewHolder) holder).imageView.setOnClickListener((View v) -> {

                        Bundle bundle = new Bundle();

                        bundle.putSerializable(MainActivity.MSG_NAME, contents);
                        bundle.putInt("ID", R.id.projectsFragment2);
                        NavHostFragment.findNavController(resFragment).navigate(R.id.action_projectsFragment2_to_infoFragment, bundle);
                    }
            );
        }

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
