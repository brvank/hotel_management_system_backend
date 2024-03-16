package com.project.hms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "table_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_id;

    @Column(unique = true)
    private String room_category;

    private String room_info;

    private float room_price;

    public Room(){
        this.room_category = "";
        this.room_info = "";
        this.room_price = 0;
    }

    public Room(String room_category, String room_info, float room_price) {
        this.room_category = room_category;
        this.room_info = room_info;
        this.room_price = room_price;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_category() {
        return room_category;
    }

    public void setRoom_category(String room_category) {
        this.room_category = room_category;
    }

    public String getRoom_info() {
        return room_info;
    }

    public void setRoom_info(String room_info) {
        this.room_info = room_info;
    }

    public float getRoom_price() {
        return room_price;
    }

    public void setRoom_price(float room_price) {
        this.room_price = room_price;
    }
}
