package com.example.bookshow;

public class Seat {

    public static final int AVAILABLE = 0;
    public static final int SELECTED = 1;
    public static final int BOOKED = 2;
    private String seatNumber;
    private SeatStatus status;
    private boolean isSelected;

    public Seat(String seatNumber, SeatStatus status, boolean isSelected) {
        this.seatNumber = seatNumber;
        this.status = status;
        this.isSelected = isSelected;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
