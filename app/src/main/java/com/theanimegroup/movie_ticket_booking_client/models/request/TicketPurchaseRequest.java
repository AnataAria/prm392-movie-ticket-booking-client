package com.theanimegroup.movie_ticket_booking_client.models.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketPurchaseRequest {
    private int showtimeId;
    private List<Integer> seatIds;
}
