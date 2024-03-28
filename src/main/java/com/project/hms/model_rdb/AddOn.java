package com.project.hms.model_rdb;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_addon")
public class AddOn {

    @Id
    private int id;

    @Column(unique = true)
    @Nonnull
    private String addon_name;

    @Nonnull
    private String addon_info;

    @Nonnull
    private double addon_price;


    public AddOn(int id, String addon_name, String addon_info, double addon_price) {
        this.id = id;
        this.addon_name = addon_name;
        this.addon_info = addon_info;
        this.addon_price = addon_price;
    }

    public AddOn() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddon_name() {
        return addon_name;
    }

    public void setAddon_name(String addon_name) {
        this.addon_name = addon_name;
    }

    public String getAddon_info() {
        return addon_info;
    }

    public void setAddon_info(String addon_info) {
        this.addon_info = addon_info;
    }

    public double getAddon_price() {
        return addon_price;
    }

    public void setAddon_price(double addon_price) {
        this.addon_price = addon_price;
    }
}
