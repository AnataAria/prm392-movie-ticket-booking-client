package com.theanimegroup.movie_ticket_booking_client.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    String fullName;
    String email;
    String password;
    int roleId = 1;
}
