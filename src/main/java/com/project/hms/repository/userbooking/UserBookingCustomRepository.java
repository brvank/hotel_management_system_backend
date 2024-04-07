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

    public UserBooking add(UserBooking userBooking){
        return userBookingRepository.save(userBooking);
    }

    public List<UserBooking> get(int start, int size){
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

        bookingCriteriaQuery.orderBy(criteriaBuilder.desc(bookingRoot.get("booking_id")));

        return entityManager.createQuery(bookingCriteriaQuery).setFirstResult(start).setMaxResults(size).getResultList();
    }

    public UserBooking getById(int id){
        return userBookingRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(UserBooking userBooking){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<UserBooking> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserBooking.class);

        Root<UserBooking> bookingRoot = criteriaUpdate.from(UserBooking.class);

        criteriaUpdate.set("guest_name", userBooking.getGuest_name());
        criteriaUpdate.set("phone_number", userBooking.getPhone_number());
        criteriaUpdate.set("date_time_check_in", userBooking.getDate_time_check_in());
        criteriaUpdate.set("date_time_check_out", userBooking.getDate_time_check_out());
        criteriaUpdate.set("person_count", userBooking.getPerson_count());
        criteriaUpdate.set("child_count", userBooking.getChild_count());
        criteriaUpdate.set("room_id", userBooking.getRoom_id());
        criteriaUpdate.set("total_price", userBooking.getTotal_price());
        criteriaUpdate.set("advance_amount", userBooking.getAdvance_amount());
        criteriaUpdate.set("discount", userBooking.getDiscount());
        criteriaUpdate.set("gst", userBooking.getGst());

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("booking_id"), userBooking.getBooking_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Transactional
    public void updateAddonPrice(UserBooking userBooking, double addonPrice){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<UserBooking> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserBooking.class);

        Root<UserBooking> bookingRoot = criteriaUpdate.from(UserBooking.class);

        criteriaUpdate.set("addon_price", addonPrice);

        Predicate predicateBookingId = criteriaBuilder.equal(bookingRoot.get("booking_id"), userBooking.getBooking_id());

        criteriaUpdate.where(predicateBookingId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    public Long count(){
        return userBookingRepository.count();
    }
}
