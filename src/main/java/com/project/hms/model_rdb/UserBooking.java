package com.project.hms.model_rdb;

import com.project.hms.model_nrdb.BookingAddOn;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "table_booking")
public class UserBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;

    @Nonnull
    private String guest_name;

    @Nonnull
    private String phone_number;

    @Nonnull
    private LocalDateTime date_time_check_in;

    @Nonnull
    private LocalDateTime date_time_check_out;

    @Nonnull
    private int person_count;

    @Nonnull
    private int child_count;

    @Nonnull
    private int room_id;

    @Nonnull
    private double total_price;

    @Nonnull
    private double addon_price;

    @Nonnull
    private double discount;

    @Nonnull
    private double advance_amount;

    @Nonnull
    private double gst;

    @Transient
    private BookingAddOn bookingAddOn;

    @Transient
    Room room;

    public UserBooking() {

    }

    public UserBooking(int booking_id, String guest_name, String phone_number, LocalDateTime date_time_check_in, LocalDateTime date_time_check_out, int person_count, int child_count, int room_id, double total_price, double discount, double advance_amount, double gst) {
        this.booking_id = booking_id;
        this.guest_name = guest_name;
        this.phone_number = phone_number;
        this.date_time_check_in = date_time_check_in;
        this.date_time_check_out = date_time_check_out;
        this.person_count = person_count;
        this.child_count = child_count;
        this.room_id = room_id;
        this.total_price = total_price;
        this.discount = discount;
        this.advance_amount = advance_amount;
        this.gst = gst;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public LocalDateTime getDate_time_check_in() {
        return date_time_check_in;
    }

    public void setDate_time_check_in(LocalDateTime date_time_check_in) {
        this.date_time_check_in = date_time_check_in;
    }

    public LocalDateTime getDate_time_check_out() {
        return date_time_check_out;
    }

    public void setDate_time_check_out(LocalDateTime date_time_check_out) {
        this.date_time_check_out = date_time_check_out;
    }

    public int getPerson_count() {
        return person_count;
    }

    public void setPerson_count(int person_count) {
        this.person_count = person_count;
    }

    public int getChild_count() {
        return child_count;
    }

    public void setChild_count(int child_count) {
        this.child_count = child_count;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getAddon_price() {
        return addon_price;
    }

    public void setAddon_price(double addon_price) {
        this.addon_price = addon_price;
    }

    public double getAdvance_amount() {
        return advance_amount;
    }

    public void setAdvance_amount(double advance_amount) {
        this.advance_amount = advance_amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public BookingAddOn getBookingAddOn() {
        return bookingAddOn;
    }

    public void setBookingAddOn(BookingAddOn bookingAddOn) {
        this.bookingAddOn = bookingAddOn;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}