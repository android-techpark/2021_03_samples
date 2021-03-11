package ru.techpark.lesson2;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewsViewHolder extends RecyclerView.ViewHolder {

    private final ImageView img;
    private final TextView title;
    private final TextView date;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.news_image);
        title = itemView.findViewById(R.id.title);
        date = itemView.findViewById(R.id.date);
    }

    public void bind(NewsModel model) {
        img.setBackgroundColor(model.mColor);
        title.setText(model.mTitle);
        date.setText(model.mDate);

        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context applicationContext = img.getContext().getApplicationContext();
                Toast.makeText(applicationContext, model.mTitle, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
