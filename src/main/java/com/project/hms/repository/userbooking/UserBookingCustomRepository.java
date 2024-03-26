package com.project.hms.repository.userbooking;

import com.project.hms.model_rdb.UserBooking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBookingCustomRepository {

    @Autowired
    UserBookingRepository userBookingRepository;

    @Autowired
    EntityManager entityManager;

    public void addBooking(UserBooking userBooking){
        userBookingRepository.save(userBooking);
    }

    public List<UserBooking> getBookings(int start, int size){
        if(start < 0){
            start = 0;
        }

        if(size < 0){
            size = 0;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserBooking> bookingCriteriaQuery = criteriaBuilder.createQuery(UserBooking.class);

        Root<UserBooking> bookingRoot = bookingCriteriaQuery.from(UserBooking.class);

        bookingCriteriaQuery.select(bookingRoot);

        return entityManager.createQuery(bookingCriteriaQuery).setFirstResult(start).setMaxResults(size).getResultList();
    }

    public UserBooking getBookingWithId(int id){
        return userBookingRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateBooking(UserBooking userBooking){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<UserBooking> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserBooking.class);

        Root<UserBooking> bookingRoot = criteriaUpdate.from(UserBooking.class);

        criteriaUpdate.set("guest_name", userBooking.getGuest_name());
        criteriaUpdate.set("phone_number", userBooking.getPhone_number());
        criteriaUpdate.set("date_time_check_in", userBooking.getDate_time_check_in());
        criteriaUpdate.set("date_time_check_out", userBooking.getDate_time_check_out());
        criteriaUpdate.set("person_count", userBooking.getPerson_count());
        criteriaUpdate.set("room_id", userBooking.getRoom_id());
        criteriaUpdate.set("total_price", userBooking.getTotal_price());
        criteriaUpdate.set("advance_amount", userBooking.getAdvance_amount());
        criteriaUpdate.set("gst", userBooking.getGst());

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("booking_id"), userBooking.getBooking_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    public Long countBookings(){
        return userBookingRepository.count();
    }
}
