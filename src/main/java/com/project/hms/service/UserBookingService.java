package com.project.hms.service;

import com.project.hms.model_nrdb.BookingAddOn;
import com.project.hms.model_rdb.AddOn;
import com.project.hms.model_rdb.UserBooking;
import com.project.hms.model_rdb.Room;
import com.project.hms.model_rdb.User;
import com.project.hms.repository.bookingaddon.BookingAddOnCustomRepository;
import com.project.hms.repository.userbooking.UserBookingCustomRepository;
import com.project.hms.response_model.CountResponse;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserBookingService {

    @Autowired
    RoomService roomService;

    @Autowired
    UserBookingCustomRepository userBookingCustomRepository;

    @Autowired
    BookingAddOnCustomRepository bookingAddOnCustomRepository;

    @Autowired
    AppUtil.Constants appUtilConstants;

    @Autowired
    AppResponse appResponse;

    @Autowired
    AppMessages.Success success;

    @Autowired
    AppMessages.Error error;

    public ResponseEntity<Object> get(int start, int size, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                return appResponse.successResponse(new CountResponse(userBookingCustomRepository.countBookings(),
                        userBookingCustomRepository.getBookings(start, size)), "");
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.unknownErrorOccurred);
            }
        }
    }

    public ResponseEntity<Object> add(UserBooking userBooking, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                if(roomService.getRoomIfExist(userBooking.getRoom_id()) != null){

                    if(userBooking.getDate_time_check_out().isBefore(userBooking.getDate_time_check_in())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(userBooking.getAdvance_amount() > userBooking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    userBookingCustomRepository.addBooking(userBooking);

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

    public ResponseEntity<Object> update(UserBooking userBooking, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                if(roomService.getRoomIfExist(userBooking.getRoom_id()) != null){

                    if(userBooking.getDate_time_check_out().isBefore(userBooking.getDate_time_check_in())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(userBooking.getAdvance_amount() > userBooking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    userBookingCustomRepository.updateBooking(userBooking);

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

    public ResponseEntity<Object> getById(int id, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                UserBooking userBooking = userBookingCustomRepository.getBookingWithId(id);

                if(userBooking == null){
                    return appResponse.failureResponse(error.bookingDoesNotExist);
                }else{
                    Room room = roomService.getRoomIfExist(userBooking.getRoom_id());

                    BookingAddOn bookingAddOn = bookingAddOnCustomRepository.get(id);

                    userBooking.setRoom(room);
                    userBooking.setBookingAddOn(bookingAddOn);

                    return appResponse.successResponse(userBooking, "");
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.unknownErrorOccurred);
            }
        }
    }

    public ResponseEntity<Object> addOn(int id, AddOn addOn, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                UserBooking userBooking = userBookingCustomRepository.getBookingWithId(id);

                if(userBooking == null){
                    return appResponse.failureResponse(error.bookingDoesNotExist);
                }else{
                    if(bookingAddOnCustomRepository.update(id, addOn)){
                        return appResponse.successResponse(success.addOnUpdate);
                    }else{
                        return appResponse.failureResponse(error.addOnNotUpdated);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.addOnNotUpdated);
            }
        }
    }
}
