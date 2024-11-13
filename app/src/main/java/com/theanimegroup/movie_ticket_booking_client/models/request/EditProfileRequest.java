package com.theanimegroup.movie_ticket_booking_client.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditProfileRequest {
    int id;
    String name;
    String address;
    String phone;
}
