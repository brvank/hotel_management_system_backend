package com.project.hms.repository.roominventory;

import com.project.hms.model_nrdb.RoomInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class RoomInventoryCustomRepository {

    @Autowired
    RoomInventoryRepository roomInventoryRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public RoomInventory get(int id){
        Query query = new Query();

        query.addCriteria(Criteria.where("room_id").is(id));

        List<RoomInventory> roomInventories = mongoTemplate.find(query, RoomInventory.class);

        if(roomInventories.isEmpty()){
            return null;
        }else{
            return roomInventories.get(0);
        }
    }

    public void add(RoomInventory roomInventory){
        roomInventoryRepository.save(roomInventory);
    }

    public boolean updateInventory(int id, LocalDate from, LocalDate to){
        RoomInventory roomInventory = get(id);

        if(roomInventory != null){

            Map<String, Boolean> bookings = roomInventory.getBookings();

            while(from.isBefore(to)){
                bookings.put(from.toString(), true);
            }

            Query query = new Query(Criteria.where("room_id").is(id));

            Update update = new Update().set("bookings", bookings);

            mongoTemplate.upsert(query, update, RoomInventory.class);

            return true;
        }else{
            return false;
        }
    }

    public boolean deduceInventory(int id, LocalDate from, LocalDate to){
        RoomInventory roomInventory = get(id);

        if(roomInventory != null){

            Map<String, Boolean> bookings = roomInventory.getBookings();

            while(from.isBefore(to)){
                bookings.remove(from.toString());
                from = from.plusDays(1);
            }

            Query query = new Query(Criteria.where("room_id").is(id));

            Update update = new Update().set("bookings", bookings);

            mongoTemplate.upsert(query, update, RoomInventory.class);

            return true;
        }else{
            return false;
        }
    }

}
