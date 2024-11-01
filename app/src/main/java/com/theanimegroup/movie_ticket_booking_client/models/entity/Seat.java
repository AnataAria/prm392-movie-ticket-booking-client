package com.theanimegroup.movie_ticket_booking_client.models.entity;

public class Seat {
    private int seatId;
    private String seatNumber;
    private String cinemaName;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Seat(int seatId, String seatNumber, String cinemaName) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.cinemaName = cinemaName;
    }
}
