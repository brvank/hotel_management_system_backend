package com.project.hms.repository.roomcategory;

import com.project.hms.model_rdb.RoomCategory;
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
public class RoomCategoryCustomRepository {

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    EntityManager entityManager;

    public void add(RoomCategory room){
        roomCategoryRepository.save(room);
    }

    public List<RoomCategory> get(){
        List<RoomCategory> list = new ArrayList<>();

        roomCategoryRepository.findAll().forEach(list::add);

        return list;
    }

    public RoomCategory get(int id){
        return roomCategoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(RoomCategory roomCategory){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<RoomCategory> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(RoomCategory.class);

        Root<RoomCategory> bookingRoot = criteriaUpdate.from(RoomCategory.class);

        criteriaUpdate.set("room_category_name", roomCategory.getRoom_category_name());
        criteriaUpdate.set("room_category_price", roomCategory.getRoom_category_price());
        criteriaUpdate.set("room_child_price", roomCategory.getRoom_child_price());
        criteriaUpdate.set("room_category_info", roomCategory.getRoom_category_info());

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("room_category_id"), roomCategory.getRoom_category_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
