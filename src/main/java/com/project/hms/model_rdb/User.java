package com.project.hms.model_rdb;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "table_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(unique = true)
    @Nonnull
    private String user_name;

    @Nonnull
    private String user_password;

    @Nonnull
    private int user_level;

    public User(){
        this.user_name = "";
        this.user_password = "";
        this.user_level = 0;
    }

    public User(String name, String password, int level){
        this.user_name = name;
        this.user_password = password;
        this.user_level = level;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }
}
