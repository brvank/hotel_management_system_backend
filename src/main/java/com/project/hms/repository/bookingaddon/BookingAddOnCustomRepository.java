package com.project.hms.repository.bookingaddon;

import com.project.hms.model_nrdb.BookingAddOn;
import com.project.hms.model_rdb.AddOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void addMore(int bookingId, AddOn addOn){
        BookingAddOn bookingAddOn = get(bookingId);

        if(bookingAddOn == null){
            bookingAddOn = new BookingAddOn();
            bookingAddOn.setBooking_id(bookingId);
            bookingAddOn.setBooking_addons(new HashMap<>());
        }

        Map<String, Map<Double, Integer>> bookingAddOnMap = bookingAddOn.getBooking_addons();
        if (bookingAddOnMap == null) {
            bookingAddOnMap = new HashMap<>();
        }

        Map<Double, Integer> priceCountMap = bookingAddOnMap.getOrDefault(addOn.getAddon_name(), new HashMap<>());
        int prevCount = priceCountMap.getOrDefault(addOn.getAddon_price(), 0);
        priceCountMap.put(addOn.getAddon_price(), prevCount + 1);
        bookingAddOnMap.put(addOn.getAddon_name(), priceCountMap);

        Update update = new Update().set("booking_addons", bookingAddOnMap);
//            Update update = new Update().set("booking_addons." + addOn.getAddon_name() + "." + addOn.getAddon_price(), prevCount + 1);
        Query query = new Query(Criteria.where("booking_id").is(bookingId));

        mongoTemplate.upsert(query, update, BookingAddOn.class);
    }

}
