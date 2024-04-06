package com.project.hms.model_rdb;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "table_room_category")
public class RoomCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_category_id;

    @Column(unique = true)
    @Nonnull
    private String room_category_name;

    @Nonnull
    private double room_category_price;

    @Nonnull
    private double room_child_price;

    @Nonnull
    private String room_category_info;

    @Transient
    private List<Room> roomList;

    public RoomCategory(int room_category_id, String room_category_name, double room_category_price, double room_child_price, String room_category_info) {
        this.room_category_id = room_category_id;
        this.room_category_name = room_category_name;
        this.room_category_price = room_category_price;
        this.room_child_price = room_child_price;
        this.room_category_info = room_category_info;
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

    public double getRoom_child_price() {
        return room_child_price;
    }

    public void setRoom_child_price(double room_child_price) {
        this.room_child_price = room_child_price;
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
