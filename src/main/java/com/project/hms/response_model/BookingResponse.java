package com.project.hms.response_model;

import com.project.hms.model.Booking;
import com.project.hms.model.Room;

public class BookingResponse {

    Booking booking;

    Room room;

    public BookingResponse(Booking booking, Room room) {
        this.booking = booking;
        this.room = room;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
