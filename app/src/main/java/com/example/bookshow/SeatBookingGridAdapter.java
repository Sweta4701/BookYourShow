package com.example.bookshow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SeatBookingGridAdapter extends BaseAdapter {
    private static final String TAG = "SeatBookingGridAdapter";

    private Context context;
    private List<Seat> seatList;
    private OnSeatSelectedListener onSeatSelectedListener;
    private SeatBookingActivity seatBookingActivity;

    public SeatBookingGridAdapter(Context context, List<Seat> seatList, SeatBookingActivity seatBookingActivity) {
        this.context = context;
        this.seatList = seatList;
        this.seatBookingActivity = seatBookingActivity;
    }

    public interface OnSeatSelectedListener {
        void onSeatSelectedListener(boolean isSelected);
    }

    public void setOnSeatSelectedListener(OnSeatSelectedListener listener) {
        this.onSeatSelectedListener = listener;
    }

    @Override
    public int getCount() {

        return seatList.size();
    }

    @Override
    public Object getItem(int position) {

        return seatList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Seat seat = seatList.get(position);
        SeatViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            viewHolder = new SeatViewHolder();
            viewHolder.seatImageButton = convertView.findViewById(R.id.seatImageButton);
            viewHolder.seatNumberTextView = convertView.findViewById(R.id.textSeatNumber);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SeatViewHolder) convertView.getTag();
        }

        viewHolder.seatNumberTextView.setText(String.valueOf(seat.getSeatNumber()));

        if (seat.getStatus().equals(SeatStatus.AVAILABLE)) {
            viewHolder.seatImageButton.setBackgroundColor(ContextCompat.getColor(context, R.color.golden));
        } else if (seat.getStatus().equals(SeatStatus.SELECTED)) {
            viewHolder.seatImageButton.setBackgroundColor(ContextCompat.getColor(context, R.color.Snow_white));
        } else if (seat.getStatus().equals(SeatStatus.BOOKED)) {
            viewHolder.seatImageButton.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }

        viewHolder.seatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seat.getStatus().equals(SeatStatus.AVAILABLE)) {
                    Log.e(TAG, "onClick: AVAILABLE " + seat.getStatus());
                    seat.setStatus(SeatStatus.SELECTED);
                    Log.e(TAG, "onClick: AVAILABLE after change " + seat.getStatus());
                    notifyDataSetChanged();

                    /*if(onSeatSelectedListener != null){
                        onSeatSelectedListener.onSeatSelected(isSeatSelected());
                    }*/

                    if (seatBookingActivity != null){
                        seatBookingActivity.onSeatSelectedListener(isSeatSelected());
                    }
                }
            }
        });

        return convertView;
    }

    public List<Seat> getBookedSeat(){
        for (Seat seat: seatList) {
            Log.i(TAG, "getBookedSeat: seat "+seat.getSeatNumber() + " seatConfirm "+seat.getStatus());

        }
        return seatList;
    }

    static class SeatViewHolder {
        ImageButton seatImageButton;
        TextView seatNumberTextView;
    }

    private boolean isSeatSelected(){
        for(Seat seat : seatList){
            if (seat.isSelected()){
                return true;
            }
        }
        return false;
    }
}
