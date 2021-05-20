package com.abyte.valet.testan40121.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.server_model.ServerModel;

import java.util.List;

public class PersonContentAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<List<ServerModel>> contents;
    private final LayoutInflater inflater;
    private final Fragment resFragment;
    private final Context context;
    private final Integer ID;

    public PersonContentAdapters(List<List<ServerModel>> contents, Context context, Fragment resFragment, Integer idThisFragment){
        this.resFragment = resFragment;
        inflater = LayoutInflater.from(context);
        this.contents = contents;
        this.context = context;
        ID = idThisFragment;
    }



    private static class MyViewHolder extends RecyclerView.ViewHolder{

        final RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_content);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_content, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<ServerModel> content = contents.get(position);

        ((MyViewHolder) holder).recyclerView.setAdapter(new ContentPersonAdapter(context, content, resFragment, ID));

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
