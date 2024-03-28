package com.project.hms.model_nrdb;

import com.project.hms.model.ShiftData;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "collection_room_inventory")
public class RoomInventory {

    @Id
    private int room_id;

    /*
     *   it will be like this way
     *   {
     *       "2023-10-12": true,
     *       "2023-10-11": false
     *   }
     *
     * */

    private Map<String, Boolean> bookings;

    public RoomInventory() {
    }

    public RoomInventory(int room_id, Map<String, Boolean> bookings) {
        this.room_id = room_id;
        this.bookings = bookings;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Map<String, Boolean> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, Boolean> bookings) {
        this.bookings = bookings;
    }
}
