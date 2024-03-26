package com.project.hms.model_rdb;

import jakarta.persistence.*;

@Entity
@Table(name = "table_rooms")
public class Room {

    @Id
    private int room_id;

    @Column(unique = true)
    private String room_number;

    @ManyToOne
    @JoinColumn(name = "room_category_id")
    private RoomCategory room_category;

    public Room(int room_id, String room_number, RoomCategory room_category) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.room_category = room_category;
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

    public RoomCategory getRoom_category() {
        return room_category;
    }

    public void setRoom_category(RoomCategory room_category) {
        this.room_category = room_category;
    }
}
