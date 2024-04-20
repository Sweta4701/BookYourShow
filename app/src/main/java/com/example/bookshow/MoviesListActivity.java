package com.example.bookshow;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movielist);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyMovieData[] myMovieData = new MyMovieData[]{
                new MyMovieData("Bengal 1947", "Hindi (U)", R.drawable.bengal1947),
                new MyMovieData("Crew", "Hindi (U)", R.drawable.crew),
                new MyMovieData("Kung Fu Panda 4", "English (U/A)", R.drawable.kungfu),
                new MyMovieData("Maidaan", "Hindi (U)", R.drawable.maidaan),
                new MyMovieData("Shaitaan", "Hindi (U)", R.drawable.shaitaan),
                new MyMovieData("Teri Baaton Mein Aisa Uljha Jiya", "Hindi (U)", R.drawable.teribaat),
                new MyMovieData("Yodha", "Hindi (U)", R.drawable.yodha),
        };

        MyMovieAdapter myMovieAdapter = new MyMovieAdapter(myMovieData, MoviesListActivity.this);
        recyclerView.setAdapter(myMovieAdapter);

    }
}