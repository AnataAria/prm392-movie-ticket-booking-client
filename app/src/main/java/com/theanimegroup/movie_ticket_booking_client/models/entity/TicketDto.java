package com.theanimegroup.movie_ticket_booking_client.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    public int id;
    public String movieName;
    public String seatName;
    public String showDateTime;
    public int price;
    public byte status;
    public int quantity;
}
