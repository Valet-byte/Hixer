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

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.model.Content;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LinkedList<Content> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;

    public ArticleAdapter(LinkedList<Content> contents, Context context, Fragment resFragment){
        this.resFragment = resFragment;
        inflater = LayoutInflater.from(context);
        this.contents = contents;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageView;
        final TextView name, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.tv_author);
            name = itemView.findViewById(R.id.tv_s);
            imageView = itemView.findViewById(R.id.img);
        }
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

            ((ArticleAdapter.MyViewHolder) holder).name.setText(content.getInfo());
            ((ArticleAdapter.MyViewHolder) holder).author.setText(content.getAuthor());
            ((ArticleAdapter.MyViewHolder) holder).imageView.setImageResource(content.getImg());

            ((ArticleAdapter.MyViewHolder) holder).imageView.setOnClickListener((View v) -> {

                        Bundle bundle = new Bundle();

                        bundle.putSerializable(MainActivity.MSG_NAME, contents);
                        bundle.putInt(MainActivity.MSG_ID_BACK_FRAGMENT, R.id.articleFragment2);
                        bundle.putInt(MainActivity.MSG_POS, position);

                        NavHostFragment.findNavController(resFragment).navigate(R.id.infoFragment, bundle);
                    }
            );
        }


    @Override
    public int getItemCount() {
        return contents.size();
    }
}
