package com.project.hms.repository;

import com.project.hms.model.Room;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomCustomRepository {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    EntityManager entityManager;

    public void addRoom(Room room){
        roomRepository.save(room);
    }

    public List<Room> getRooms(){
        List<Room> list = new ArrayList<>();

        roomRepository.findAll().forEach(list::add);

        return list;
    }
}
