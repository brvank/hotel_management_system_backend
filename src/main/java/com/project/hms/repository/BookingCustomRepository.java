package com.project.hms.repository;

import com.project.hms.model.Booking;
import com.project.hms.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookingCustomRepository {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EntityManager entityManager;

    public void addBooking(Booking booking){
        bookingRepository.save(booking);
    }

    public List<Booking> getBookings(int start, int size){
        if(start < 0){
            start = 0;
        }

        if(size < 0){
            size = 0;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Booking> bookingCriteriaQuery = criteriaBuilder.createQuery(Booking.class);

        Root<Booking> bookingRoot = bookingCriteriaQuery.from(Booking.class);

        bookingCriteriaQuery.select(bookingRoot);

        return entityManager.createQuery(bookingCriteriaQuery).setFirstResult(start).setMaxResults(size).getResultList();
    }

    public Booking getBookingWithId(int id){
        return bookingRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateBooking(Booking booking){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Booking> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Booking.class);

        Root<Booking> bookingRoot = criteriaUpdate.from(Booking.class);

        criteriaUpdate.set("guest_name", booking.getGuest_name());
        criteriaUpdate.set("phone_number", booking.getPhone_number());
        criteriaUpdate.set("date_time_check_in", booking.getDate_time_check_in());
        criteriaUpdate.set("date_time_check_out", booking.getDate_time_check_out());
        criteriaUpdate.set("person_count", booking.getPerson_count());
        criteriaUpdate.set("room_id", booking.getRoom_id());
        criteriaUpdate.set("total_price", booking.getTotal_price());
        criteriaUpdate.set("advance_amount", booking.getAdvance_amount());
        criteriaUpdate.set("gst", booking.getGst());

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("booking_id"), booking.getBooking_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
