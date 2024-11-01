package com.theanimegroup.movie_ticket_booking_client.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ShowTime {
    public int id;
    public String showDateTime;
    public String roomName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowDateTime() {
        return showDateTime;
    }

    public void setShowDateTime(String showDateTime) {
        this.showDateTime = showDateTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ShowTime(int id, String showDateTime, String roomName) {
        this.id = id;
        this.showDateTime = showDateTime;
        this.roomName = roomName;
    }
}
