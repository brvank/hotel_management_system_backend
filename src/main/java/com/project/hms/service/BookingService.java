package com.project.hms.service;

import com.project.hms.model.Booking;
import com.project.hms.model.Room;
import com.project.hms.model.User;
import com.project.hms.repository.BookingCustomRepository;
import com.project.hms.response_model.BookingResponse;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    RoomService roomService;

    @Autowired
    BookingCustomRepository bookingCustomRepository;

    @Autowired
    AppUtil.Constants appUtilConstants;

    @Autowired
    AppResponse appResponse;

    @Autowired
    AppMessages.Success success;

    @Autowired
    AppMessages.Error error;

    public ResponseEntity<Object> getBookings(int start, int size, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            return appResponse.successResponse(bookingCustomRepository.getBookings(start, size), "");
        }
    }

    public ResponseEntity<Object> addBooking(Booking booking, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                if(roomService.getRoomIfExist(booking.getRoom_id()) != null){

                    if(booking.getDate_time_check_out().isBefore(booking.getDate_time_check_in())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(booking.getAdvance_amount() > booking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    bookingCustomRepository.addBooking(booking);

                    return appResponse.successResponse(success.bookingAdded);
                }else{
                    return appResponse.failureResponse(error.roomDoesNotExist);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.bookingNotAdded);
            }
        }
    }

    public ResponseEntity<Object> updateBooking(Booking booking, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                if(roomService.getRoomIfExist(booking.getRoom_id()) != null){

                    if(booking.getDate_time_check_out().isBefore(booking.getDate_time_check_in())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(booking.getAdvance_amount() > booking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    bookingCustomRepository.updateBooking(booking);

                    return appResponse.successResponse(success.bookingUpdated);
                }else{
                    return appResponse.failureResponse(error.roomDoesNotExist);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.bookingNotAdded);
            }
        }
    }

    public ResponseEntity<Object> getBookingWithId(int id, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            Booking booking = bookingCustomRepository.getBookingWithId(id);

            if(booking == null){
                return appResponse.failureResponse(error.bookingDoesNotExist);
            }else{
                Room room = roomService.getRoomIfExist(booking.getRoom_id());

                return appResponse.successResponse(new BookingResponse(booking, room), "");
            }
        }
    }
}
