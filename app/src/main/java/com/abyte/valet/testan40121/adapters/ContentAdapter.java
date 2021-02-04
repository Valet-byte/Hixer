package com.abyte.valet.testan40121.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.fragments.InfoFragment;
import com.abyte.valet.testan40121.model.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView name, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            name = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
        }
    }

    private List<Content> contents;
    private Context context;
    private LayoutInflater inflater;

    public ContentAdapter(Context context, List<Content> contents) {
        this.contents = contents;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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

        ((MyViewHolder) holder).name.setText(content.getInfo());
        ((MyViewHolder)holder).author.setText(content.getAuthor());
        ((MyViewHolder) holder).imageView.setImageResource(content.getImg());

        ((MyViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = new InfoFragment();

                Bundle bundle = new Bundle();

                bundle.putSerializable(MainActivity.MSG_NAME, contents.get(position));

                infoFragment.setArguments(bundle);

                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.project_fragment, infoFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
