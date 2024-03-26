package com.project.hms.model_rdb;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "table_room_category")
public class RoomCategory {

    @Id
    private int room_category_id;

    @Column(unique = true)
    private String room_category_name;

    private double room_category_price;

    private String room_category_info;

    @OneToMany(mappedBy = "room_category")
    private List<Room> roomList;

    public RoomCategory(int room_category_id, String room_category_name, double room_category_price, String room_category_info, List<Room> roomList) {
        this.room_category_id = room_category_id;
        this.room_category_name = room_category_name;
        this.room_category_price = room_category_price;
        this.room_category_info = room_category_info;
        this.roomList = roomList;
    }

    public RoomCategory() {
    }

    public int getRoom_category_id() {
        return room_category_id;
    }

    public void setRoom_category_id(int room_category_id) {
        this.room_category_id = room_category_id;
    }

    public String getRoom_category_name() {
        return room_category_name;
    }

    public void setRoom_category_name(String room_category_name) {
        this.room_category_name = room_category_name;
    }

    public double getRoom_category_price() {
        return room_category_price;
    }

    public void setRoom_category_price(double room_category_price) {
        this.room_category_price = room_category_price;
    }

    public String getRoom_category_info() {
        return room_category_info;
    }

    public void setRoom_category_info(String room_category_info) {
        this.room_category_info = room_category_info;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
