package com.abyte.valet.testan40121.adapters;

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
import com.abyte.valet.testan40121.model.Content;

import java.util.ArrayList;

public class IdeaAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final ArrayList<Content> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;

    public IdeaAdapters(Context context, ArrayList<Content> contents, Fragment fragment) {
        this.contents = contents;
        this.inflater = LayoutInflater.from(context);
        this.resFragment = fragment;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final ImageView logo;
        final TextView author, info;
        final LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_info);
            author = itemView.findViewById(R.id.tv_author);
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

        Content content = contents.get(position);
        if (content.getKat() == 3){
            ((MyViewHolder) holder).logo.setImageResource(content.getImg());
            ((MyViewHolder) holder).author.setText(content.getAuthor());
            ((MyViewHolder) holder).info.setText(content.getInfo());

            ((MyViewHolder) holder).layout.setOnClickListener((View v) -> {

                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.MSG_NAME, contents);
                bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, R.id.ideaFragment2);
                bundle.putInt(MainActivity.MSG_POS, position);

                NavHostFragment.findNavController(resFragment).popBackStack(R.id.infoFragment, true);
                NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);

            });

        }

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
