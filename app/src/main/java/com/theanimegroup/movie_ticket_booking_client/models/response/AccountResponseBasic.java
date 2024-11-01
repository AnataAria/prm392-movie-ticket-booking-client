package com.theanimegroup.movie_ticket_booking_client.models.response;

import lombok.Data;

@Data
public class AccountResponseBasic {
    int id;
    String name;
    String address;
    String phone;
    String role;
    int status;
    String email;
    double wallet;
}
