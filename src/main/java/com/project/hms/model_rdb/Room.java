package com.project.hms.model_rdb;

import com.project.hms.model_nrdb.RoomInventory;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "table_rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_id;

    @Column(unique = true)
    @Nonnull
    private String room_number;

    private int room_category_id;

    @Transient
    private RoomInventory roomInventory;

    public Room(int room_id, String room_number, int room_category_id) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.room_category_id = room_category_id;
    }

    public Room() {
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public int getRoom_category_id() {
        return room_category_id;
    }

    public void setRoom_category_id(int room_category_id) {
        this.room_category_id = room_category_id;
    }

    public RoomInventory getRoomInventory() {
        return roomInventory;
    }

    public void setRoomInventory(RoomInventory roomInventory) {
        this.roomInventory = roomInventory;
    }
}
