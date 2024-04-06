package com.project.hms.repository.bookingaddon;

import com.project.hms.model_nrdb.BookingAddOn;
import com.project.hms.model_rdb.AddOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookingAddOnCustomRepository {

    @Autowired
    BookingAddOnRepository bookingAddOnRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public BookingAddOn get(int bookingId){
        Query query = new Query();

        query.addCriteria(Criteria.where("booking_id").is(bookingId));

        List<BookingAddOn> bookingAddOnList = mongoTemplate.find(query, BookingAddOn.class);

        if(bookingAddOnList.isEmpty()){
            return null;
        }else{
            return bookingAddOnList.get(0);
        }
    }

    public void add(BookingAddOn bookingAddOn){
        bookingAddOnRepository.save(bookingAddOn);
    }

    //TODO: written very raw : need to be optimized
    public void addMore(int bookingId, Map<Integer, Integer> addOnsToAdd, Map<Integer, AddOn> addOnMap){
        BookingAddOn bookingAddOn = get(bookingId);

        if(bookingAddOn == null){
            bookingAddOn = new BookingAddOn();
            bookingAddOn.setBooking_id(bookingId);
            bookingAddOn.setBooking_addons(new HashMap<>());
        }

        Map<Integer, Map<Double, Integer>> bookingAddOnMap = bookingAddOn.getBooking_addons();
        if (bookingAddOnMap == null) {
            bookingAddOnMap = new HashMap<>();
        }

        Set<Map.Entry<Integer, Integer>> addOnEntrySet = addOnsToAdd.entrySet();

        for(Map.Entry<Integer, Integer> entry : addOnEntrySet){
            AddOn addOn = addOnMap.get(entry.getKey());
            Map<Double, Integer> priceCountMap = bookingAddOnMap.getOrDefault(addOn.getAddon_id(), new HashMap<>());
            int prevCount = priceCountMap.getOrDefault(addOn.getAddon_price(), 0);
            priceCountMap.put(addOn.getAddon_price(), prevCount + entry.getValue());
            bookingAddOnMap.put(addOn.getAddon_id(), priceCountMap);
        }

        Update update = new Update().set("booking_addons", bookingAddOnMap);
//            Update update = new Update().set("booking_addons." + addOn.getAddon_name() + "." + addOn.getAddon_price(), prevCount + 1);
        Query query = new Query(Criteria.where("booking_id").is(bookingId));

        mongoTemplate.upsert(query, update, BookingAddOn.class);
    }

}
