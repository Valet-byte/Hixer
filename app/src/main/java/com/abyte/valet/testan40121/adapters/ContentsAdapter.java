package com.abyte.valet.testan40121.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.server_model.ServerModel;
import com.abyte.valet.testan40121.rest.RetrofitClient;

import java.util.List;

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

    private final List<ServerModel> contents;
    private final LayoutInflater inflater;
    private final Context context;

    public ContentsAdapter(List<ServerModel> contents, Context context){
        this.context = context;
        this.contents = contents;
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

        ServerModel content = contents.get(position);

        ((MyHolder)holder).tvTable.setText(content.getName());
        ((MyHolder)holder).textView.setText(content.getInfo());

        if (content.getBitmap() != null) ((MyHolder) holder).imageView.setImageBitmap(content.getBitmap());

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
