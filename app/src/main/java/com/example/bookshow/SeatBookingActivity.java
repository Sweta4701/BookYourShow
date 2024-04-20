package com.example.bookshow;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeatBookingActivity extends AppCompatActivity implements SeatBookingGridAdapter.OnSeatSelectedListener {
    private static final String TAG = "SeatBookingActivity";
    private GridView grid;
    private List<Seat> seatList;
    private Button btnConfirmBook;
    private SeatBookingGridAdapter seatBookingGridAdapter;
    private RadioGroup radioGroup;

    private boolean isSeatSelected = false;
    private boolean isMovieTimeSelected = false;
    private String showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seat_booking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("MOVIE_NAME");

        TextView movieNameTextView = findViewById(R.id.textMovieName);

        movieNameTextView.setText(movieName);

        btnConfirmBook = findViewById(R.id.btnConfirmBook);


        seatList = initializeSeat();
        int counterr = 1;
        for (Seat abc : seatList) {
            Log.d(TAG, "onCreate: seat Name : " + counterr + " : " + abc.getSeatNumber() + " seat status : " + abc.getStatus());
            counterr++;
        }
        seatBookingGridAdapter = new SeatBookingGridAdapter(this, seatList, this);

        grid = (GridView) findViewById(R.id.dataGrid);
        grid.setAdapter(seatBookingGridAdapter);

        radioGroup = findViewById(R.id.radioGroupMovieTime);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                RadioButton checkedRadioButton = findViewById(checkedID);
                if (checkedRadioButton != null) {
                    String movieTime = checkedRadioButton.getText().toString();
                    Log.i(TAG, "onCheckedChanged: movieTime : " + movieTime);
                    isMovieTimeSelected = true;
                    showTime = movieTime;


                    //Saved in shared preference
                    SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("KEY_NAME", movieTime);
                    editor.apply();
//                    String movie_Time = sharedPreferences.getString("movie_time", "");

                    // Insert selected movie time into SQLite database
//                    insertMovieTime(movie_Time);
                }
            }
        });


        btnConfirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get value from shared preference
//                check value is null or not
//             validate
                Log.i(TAG, "onClick: btn is clicked ");

                try {
                    String savedTime = getName();
                    if (!isSeatSelected() && savedTime == null) {
                        Toast.makeText(SeatBookingActivity.this, "Please make sure to select Seat and Time", Toast.LENGTH_LONG).show();
                        return;
                    }

                    List<Seat> seats = seatBookingGridAdapter.getBookedSeat();
                    if (!seats.isEmpty()) {
                        for (Seat seat : seats) {
                            Log.e(TAG, "onClick: Seat " + seat.getSeatNumber() + "Seat status " + seat.getStatus());
                        }
                    }

                    // Convert the list of Seat objects to a JSON string
                    Gson gson = new Gson();
                    String json = gson.toJson(seats);
                    // Save the JSON string to SharedPreferences
                    SharedPreferences sharedPreference = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreference.edit();
                    edit.putString("seatsList", json);
                    edit.apply();

//                if (!isSeatSelected()) {
//                    Toast.makeText(SeatBookingActivity.this, "Please make sure to select your Seat", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
////                String savedTime = getName();
//                if (savedTime == null) {
//                    Toast.makeText(SeatBookingActivity.this, "Please make sure to select time", Toast.LENGTH_LONG).show();
//                    return;
//                }

                    seatBookingGridAdapter.notifyDataSetChanged();

                    Toast.makeText(SeatBookingActivity.this, "Your Booking is Confirm!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("KEY_NAME", null);
                    editor.apply();
                    //Pass the movie name and time to TicketActivity
                    Intent iNext = new Intent(SeatBookingActivity.this, TicketActivity.class);
                    iNext.putExtra("MOVIE_TIME", savedTime);
                    iNext.putExtra("MOVIE_NAME", movieName);
                    startActivity(iNext);

                } catch (Exception e) {
                    Log.e(TAG, "onClick: ", e);
                    e.printStackTrace();
                }
            }
        });
    }

    public String getName() {
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        Log.i(TAG, "getName: value : " + sharedPreferences.getString("KEY_NAME", null));
        return sharedPreferences.getString("KEY_NAME", null);
    }

    private List<Seat> initializeSeat() {
        List<Seat> seatList = new ArrayList<>();

        String[] seatNumber = {"S21", "S22", "S23", "S24", "S25", "S26", "S27", "S28", "S29", "S30", "S31", "S32", "S33", "S34", "S35"};
        SeatStatus[] seatStatuses = new SeatStatus[seatNumber.length];

        for (int i = 0; i < seatNumber.length; i++) {
            seatStatuses[i] = SeatStatus.AVAILABLE;
        }


        for (int i = 0; i < seatNumber.length; i++) {
            Seat seat = new Seat(seatNumber[i], seatStatuses[i], true);
            seatList.add(seat);
        }

        return seatList;
    }

    private boolean isSeatSelected() {
        int counter = 1;
        boolean isSeatChanged = false;
        for (Seat seat : seatList) {
            Log.i(TAG, "isSeatSelected: counter : " + counter);
            counter++;
            if (seat.getStatus().equals(SeatStatus.SELECTED)) {
//                return true;
                seat.setStatus(SeatStatus.BOOKED);
                isSeatChanged = true;
            }
        }

        if (isSeatChanged) {
            return true;
        } else return false;

    }

    //Method to get selected seat numbers
    private ArrayList<String> getSelectedSeatNumbers(ArrayList<Seat> selectedSeats) {
        ArrayList<String> selectedSeatNumbers = new ArrayList<>();
        for (Seat seat : seatList) {
            Log.i(TAG, "getSelectedSeatNumbers: seat " + seat.getSeatNumber());
            if (seat.getStatus().equals(SeatStatus.SELECTED)) {
                selectedSeatNumbers.add(seat.getSeatNumber());
            }
        }
        return selectedSeatNumbers;
    }

    @Override
    public void onSeatSelectedListener(boolean isSelected) {
        btnConfirmBook.setEnabled(isSelected);
    }
}