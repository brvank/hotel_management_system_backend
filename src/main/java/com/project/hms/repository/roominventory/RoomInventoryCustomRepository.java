package com.project.hms.repository.roominventory;

import com.project.hms.model_nrdb.RoomInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class RoomInventoryCustomRepository {

    @Autowired
    RoomInventoryRepository roomInventoryRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<RoomInventory> get(){
        return roomInventoryRepository.findAll();
    }

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

    public RoomInventory getOrCreateNew(int id){
        RoomInventory roomInventory = get(id);

        if(roomInventory == null){
            roomInventory = new RoomInventory();
            roomInventory.setRoom_id(id);
            roomInventory.setBookings(new HashMap<>());

            return add(roomInventory);
        }else{
            return roomInventory;
        }
    }

    public RoomInventory add(RoomInventory roomInventory){
        return roomInventoryRepository.save(roomInventory);
    }

    public boolean updateInventory(int id, LocalDate from, LocalDate to){
        RoomInventory roomInventory = get(id);

        if(roomInventory != null){

            Map<String, Boolean> bookings = roomInventory.getBookings();

            while(from.isBefore(to)){
                bookings.put(from.toString(), true);
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

    public void removeAllBefore(Map<String, Boolean> bookings, LocalDate upTo) throws DateTimeParseException {
        bookings.entrySet().removeIf(entry -> upTo.isAfter(LocalDate.parse(entry.getKey())));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldBookings() {
        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);

        List<RoomInventory> roomInventories = roomInventoryRepository.findAll();

        for(RoomInventory roomInventory : roomInventories){
            removeAllBefore(roomInventory.getBookings(), tenDaysAgo);
            mongoTemplate.save(roomInventory);
        }
    }

}
