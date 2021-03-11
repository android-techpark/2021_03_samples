package ru.techpark.lesson2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    List<NewsModel> data = DataSource.getInstance().getRemoteData();

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel model = data.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
