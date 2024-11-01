package com.theanimegroup.movie_ticket_booking_client.models.response;

import lombok.Getter;

@Getter
public class ResponseObject<T> {
    boolean status;
    T data;
    String error;
    String errorCode;
}
