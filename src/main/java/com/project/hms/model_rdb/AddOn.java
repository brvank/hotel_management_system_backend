package com.project.hms.model_rdb;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "table_addon")
public class AddOn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addon_id;

    @Column(unique = true)
    @Nonnull
    private String addon_name;

    @Nonnull
    private String addon_info;

    @Nonnull
    private double addon_price;


    public AddOn(int addon_id, String addon_name, String addon_info, double addon_price) {
        this.addon_id = addon_id;
        this.addon_name = addon_name;
        this.addon_info = addon_info;
        this.addon_price = addon_price;
    }

    public AddOn() {
    }

    public int getAddon_id() {
        return addon_id;
    }

    public void setAddon_id(int addon_id) {
        this.addon_id = addon_id;
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
