package com.project.hms.repository.userbooking;

import com.project.hms.model_rdb.UserBooking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookingRepository extends CrudRepository<UserBooking, Integer> {
}
