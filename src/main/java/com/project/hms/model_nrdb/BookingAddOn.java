package com.project.hms.model_nrdb;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "collection_booking_addon")
public class BookingAddOn {
    @Id
    private int booking_id;

    /*
    *   it will be like this way
    *   {
    *       "water bottle": {
    *                           20: 2,
    *                           30: 1
    *                       }
    *   }
    *
    * */
    private Map<Integer, Map<Double, Integer>> booking_addons;

    public BookingAddOn() {
    }

    public BookingAddOn(int booking_id, Map<Integer, Map<Double, Integer>> booking_addons) {
        this.booking_id = booking_id;
        this.booking_addons = booking_addons;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public Map<Integer, Map<Double, Integer>> getBooking_addons() {
        return booking_addons;
    }

    public void setBooking_addons(Map<Integer, Map<Double, Integer>> booking_addons) {
        this.booking_addons = booking_addons;
    }
}
