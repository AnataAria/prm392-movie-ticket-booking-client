package com.theanimegroup.movie_ticket_booking_client.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    public int id;
    public int seatID;
    public String seatName;
    public String movieName;
    public String showDateTime;
    public int price;
    public byte status;
    public int quantity;
}
