package com.abyte.valet.testan40121.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.Content;

public class ContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static class MyHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView textView, tvTable;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTable = itemView.findViewById(R.id.tv_table);
            textView = itemView.findViewById(R.id.text_info);
            imageView = itemView.findViewById(R.id.img_info);
        }
    }

    private final Content content;
    private final LayoutInflater inflater;

    public ContentsAdapter(Content content, Context context){
        this.content = content;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contents_set, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MyHolder)holder).tvTable.setText(content.chapters.get(position).getTable());
        ((MyHolder)holder).textView.setText(content.chapters.get(position).getInfo());

        if (content.chapters.get(position).getImg() != R.drawable.ic1) ((MyHolder) holder).imageView.setImageResource(content.chapters.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return content.chapters.size();
    }
}
