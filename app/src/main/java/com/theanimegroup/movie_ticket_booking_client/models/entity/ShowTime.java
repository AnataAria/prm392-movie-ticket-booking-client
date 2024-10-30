package com.theanimegroup.movie_ticket_booking_client.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowTime {
    public int Id;
    public String CinemaRoomID;
    public int MovieID;
    public int AvaliableSeats;
}
