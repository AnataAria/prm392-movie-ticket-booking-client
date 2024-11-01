package com.theanimegroup.movie_ticket_booking_client.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Movie implements Serializable {
    public int id;
    public String name;
    public Date dateStart;
    public Date dateEnd;
    public String image;
    public byte status;
    public String directorName;
    public String description;
    public List<String> showtime;

    public Movie(int id, String name, Date dateStart, Date dateEnd, String image, byte status, String directorName, String description, List<String> showtime) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.image = image;
        this.status = status;
        this.directorName = directorName;
        this.description = description;
        this.showtime = showtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getShowtime() {
        return showtime;
    }

    public void setShowtime(List<String> showtime) {
        this.showtime = showtime;
    }
}

