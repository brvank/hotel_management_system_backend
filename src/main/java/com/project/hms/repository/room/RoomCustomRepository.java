package com.project.hms.repository.room;

import com.project.hms.model_rdb.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
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

    public Room add(Room room){
        return roomRepository.save(room);
    }

    public List<Room> get(){
        List<Room> list = new ArrayList<>();

        roomRepository.findAll().forEach(list::add);

        return list;
    }

    @Transactional
    public void update(Room room){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Room> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Room.class);

        Root<Room> bookingRoot = criteriaUpdate.from(Room.class);

        criteriaUpdate.set("room_number", room.getRoom_number());

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("room_id"), room.getRoom_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
