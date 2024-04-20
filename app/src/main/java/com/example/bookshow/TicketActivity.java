package com.example.bookshow;

import static androidx.core.graphics.drawable.DrawableKt.toBitmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TicketActivity extends AppCompatActivity {


    private static final String TAG = "TicketActivity";
    private TextView textViewMovieTime;
    private TextView textViewSeatNumber;
    private TextView movieNameTextView;
    private Button buttonLogOut;
    private ImageView imageViewQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.e(TAG, "onCreate: I am in onCreate() !");
        movieNameTextView = findViewById(R.id.textMovieName);
        textViewMovieTime = findViewById(R.id.textViewMovieTime);
        textViewSeatNumber = findViewById(R.id.textViewSeatNumber);
        buttonLogOut = findViewById(R.id.btnLogOut);
        imageViewQRCode = findViewById(R.id.imageViewQRCode);

        //Retrieve the movie name from the intent
        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        //set movie name to the TextView
        movieNameTextView.setText(movieName);

        String movieTime = getIntent().getStringExtra("MOVIE_TIME");
        textViewMovieTime.setText(movieTime);

        // Retrieve the JSON string from SharedPreferences
        SharedPreferences sharedPreference = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String json = sharedPreference.getString("seatsList", "");

        // Convert the JSON string back to a list of Seat objects
        Type type = new TypeToken<List<Seat>>() {
        }.getType();
        Gson gson = new Gson();
        List<Seat> seats = gson.fromJson(json, type);
        Log.e(TAG, "onCreate: seat :" + seats);
        StringBuilder stringBuilder = new StringBuilder();
        for (Seat seat : seats) {
            if (!seats.isEmpty()) {
                Log.i(TAG, "onCreate: seat: " + seat.getSeatNumber() + " seatStatus: " + seat.getStatus());
                if (seat.getStatus() == SeatStatus.BOOKED) {
                    Log.i(TAG, "onCreate: seatBooked: " + seat.getSeatNumber());
                    stringBuilder.append(seat.getSeatNumber()).append(" ");
//                    Log.i(TAG, "onCreate: seats"+stringBuilder);
                }
            } else {
                Log.i(TAG, "onCreate: List is empty");
            }
//            if (stringBuilder.length() > 0) {
//                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
//            }
            textViewSeatNumber.setText(stringBuilder.toString());
        }

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        generateQRCode("Your Data Here");

    }

    private void generateQRCode(String data) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500);
            Bitmap bitmap = toBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
            }
        }
        return bmp;
    }
}