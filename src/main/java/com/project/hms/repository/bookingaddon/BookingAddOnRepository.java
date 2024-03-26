package com.project.hms.repository.bookingaddon;

import com.project.hms.model_nrdb.BookingAddOn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingAddOnRepository extends MongoRepository<BookingAddOn, Integer> {
}
