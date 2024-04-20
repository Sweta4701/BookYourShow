package com.example.bookshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.ViewHolder> {

    Context context;
    MyMovieData[] myMovieData;

    public MyMovieAdapter(MyMovieData[] myMovieData, MoviesListActivity activity2) {
        this.myMovieData = myMovieData;
        this.context = activity2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyMovieData myMovieDataList = myMovieData[position];
        holder.txtName.setText(myMovieDataList.getName());
        holder.txtLanguage.setText(myMovieDataList.getLanguage());
        holder.imgMovie.setImageResource(myMovieDataList.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, myMovieDataList.getName(), Toast.LENGTH_SHORT).show();

                Intent iNext;
                iNext = new Intent(context, SeatBookingActivity.class);
                iNext.putExtra("MOVIE_NAME", myMovieDataList.getName());
                context.startActivity(iNext);

            }
        });

    }

    @Override
    public int getItemCount() {
        return myMovieData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtLanguage;
        ImageView imgMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.textName);
            txtLanguage = itemView.findViewById(R.id.textLanguage);
            imgMovie = itemView.findViewById(R.id.imageview);
        }
    }
}
