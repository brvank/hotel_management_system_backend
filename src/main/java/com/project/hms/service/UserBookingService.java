package com.project.hms.service;

import com.project.hms.model_nrdb.BookingAddOn;
import com.project.hms.model_nrdb.RoomInventory;
import com.project.hms.model_rdb.AddOn;
import com.project.hms.model_rdb.UserBooking;
import com.project.hms.model_rdb.Room;
import com.project.hms.model_rdb.User;
import com.project.hms.repository.addon.AddOnCustomRepository;
import com.project.hms.repository.bookingaddon.BookingAddOnCustomRepository;
import com.project.hms.repository.roominventory.RoomInventoryCustomRepository;
import com.project.hms.repository.userbooking.UserBookingCustomRepository;
import com.project.hms.response_model.CountResponse;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserBookingService {

    @Autowired
    RoomService roomService;

    @Autowired
    UserBookingCustomRepository userBookingCustomRepository;

    @Autowired
    BookingAddOnCustomRepository bookingAddOnCustomRepository;

    @Autowired
    RoomInventoryCustomRepository roomInventoryCustomRepository;

    @Autowired
    AddOnCustomRepository addOnCustomRepository;

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
                return appResponse.successResponse(new CountResponse(userBookingCustomRepository.count(),
                        userBookingCustomRepository.get(start, size)), "");
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
            Room room = roomService.getRoomIfExist(userBooking.getRoom_id());
            if(room != null){
                try {
                    if(userBooking.getDate_time_check_in().isBefore(LocalDateTime.now())){
                        return appResponse.failureResponse(error.checkInTimeConflict);
                    }

                    if(!userBooking.getDate_time_check_in().isBefore(userBooking.getDate_time_check_out())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(userBooking.getAdvance_amount() > userBooking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    RoomInventory roomInventory = roomInventoryCustomRepository.getOrCreateNew(room.getRoom_id());
                    Map<String, Boolean> bookings = roomInventory.getBookings();

                    LocalDate from = userBooking.getDate_time_check_in().toLocalDate();
                    LocalDate to = userBooking.getDate_time_check_out().toLocalDate();

                    while(from.isBefore(to)){
                        if(bookings.getOrDefault(from.toString(), false)){
                            return appResponse.failureResponse(error.roomNotAvailable);
                        }
                        from = from.plusDays(1);
                    }

                    userBooking = userBookingCustomRepository.add(userBooking);

                    BookingAddOn bookingAddOn = new BookingAddOn();
                    bookingAddOn.setBooking_id(userBooking.getBooking_id());
                    bookingAddOn.setBooking_addons(new HashMap<>());

                    bookingAddOnCustomRepository.add(bookingAddOn);

                    roomInventoryCustomRepository.updateInventory(room.getRoom_id(),
                            userBooking.getDate_time_check_in().toLocalDate(), userBooking.getDate_time_check_out().toLocalDate());

                    return appResponse.successResponse(success.bookingAdded);
                }catch (Exception e){
                    e.printStackTrace();
                    return appResponse.failureResponse(error.bookingNotAdded);
                }
            }else{
                return appResponse.failureResponse(error.roomDoesNotExist);
            }
        }
    }

    public ResponseEntity<Object> update(UserBooking userBooking, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                Room room = roomService.getRoomIfExist(userBooking.getRoom_id());
                UserBooking oldBooking = userBookingCustomRepository.getById(userBooking.getBooking_id());
                Room oldRoom = roomService.getRoomIfExist(oldBooking.getRoom_id());

                if(room != null){

                    if(!userBooking.getDate_time_check_in().isBefore(userBooking.getDate_time_check_out())){
                        return appResponse.failureResponse(error.checkInOutTimeConflict);
                    }

                    if(userBooking.getAdvance_amount() > userBooking.getTotal_price()){
                        return appResponse.failureResponse(error.advanceTotalConflict);
                    }

                    RoomInventory roomInventory = roomInventoryCustomRepository.getOrCreateNew(oldRoom.getRoom_id());
                    Map<String, Boolean> bookings = roomInventory.getBookings();

                    LocalDate from = oldBooking.getDate_time_check_in().toLocalDate();
                    LocalDate to = oldBooking.getDate_time_check_out().toLocalDate();

                    //remove old booking from cache
                    while(from.isBefore(to)){
                        bookings.remove(from.toString());
                        from = from.plusDays(1);
                    }

                    if(room.getRoom_id() != oldRoom.getRoom_id()){
                        bookings = roomInventoryCustomRepository.getOrCreateNew(room.getRoom_id()).getBookings();
                    }

                    from = userBooking.getDate_time_check_in().toLocalDate();
                    to = userBooking.getDate_time_check_out().toLocalDate();

                    //check for room availability
                    while(from.isBefore(to)){
                        if(bookings.getOrDefault(from.toString(), false)){
                            return appResponse.failureResponse(error.roomNotAvailable);
                        }
                        from = from.plusDays(1);
                    }

                    //room available: remove old allotment
                    if(!roomInventoryCustomRepository.deduceInventory(oldRoom.getRoom_id(),
                            oldBooking.getDate_time_check_in().toLocalDate(), oldBooking.getDate_time_check_out().toLocalDate())){
                        return appResponse.failureResponse(error.roomNotAvailable);
                    }

                    //add new allotment
                    if(roomInventoryCustomRepository.updateInventory(room.getRoom_id(),
                            userBooking.getDate_time_check_in().toLocalDate(), userBooking.getDate_time_check_out().toLocalDate())){

                        //save booking
                        userBookingCustomRepository.update(userBooking);

                        return appResponse.successResponse(success.bookingUpdated);
                    }else{
                        return appResponse.failureResponse(error.roomNotAvailable);
                    }
                }else{
                    return appResponse.failureResponse(error.roomDoesNotExist);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.bookingNotUpdated);
            }
        }
    }

    public ResponseEntity<Object> getById(int id, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                UserBooking userBooking = userBookingCustomRepository.getById(id);

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

    public ResponseEntity<Object> addOn(int id, Map<Integer, Integer> addonList, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                UserBooking userBooking = userBookingCustomRepository.getById(id);

                if(userBooking == null){
                    return appResponse.failureResponse(error.bookingDoesNotExist);
                }else{
                    List<AddOn> addOnList = addOnCustomRepository.get();

                    Map<Integer, AddOn> addOnMap = new HashMap<>();

                    for(AddOn addOn: addOnList){
                        addOnMap.put(addOn.getAddon_id(), addOn);
                    }

                    bookingAddOnCustomRepository.addMore(id, addonList, addOnMap);
                    return appResponse.successResponse(success.addOnUpdated);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.addOnNotUpdated);
            }
        }
    }

    public ResponseEntity<Object> updateAddOnPrice(int id, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try{
                UserBooking userBooking = userBookingCustomRepository.getById(id);

                if(userBooking == null){
                    return appResponse.failureResponse(error.bookingDoesNotExist);
                }else{

                    BookingAddOn bookingAddOn = bookingAddOnCustomRepository.get(userBooking.getBooking_id());

                    if(bookingAddOn != null){
                        Set<Map.Entry<Integer, Map<Double, Integer>>> entrySet = bookingAddOn.getBooking_addons().entrySet();

                        double totalPrice = 0;
                        for(Map.Entry<Integer, Map<Double, Integer>> entry : entrySet){

                            Set<Map.Entry<Double, Integer>> priceCountSet = entry.getValue().entrySet();

                            for(Map.Entry<Double, Integer> priceCountEntry : priceCountSet){
                                totalPrice += priceCountEntry.getKey() * priceCountEntry.getValue();
                            }
                        }

                        if(totalPrice != userBooking.getAddon_price()){
                            userBookingCustomRepository.updateAddonPrice(userBooking, totalPrice);
                        }

                        return appResponse.successResponse(success.bookingAddOnPriceUpdated);
                    }else{
                        return appResponse.failureResponse(error.bookingNoAddon);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.bookingAddOnPriceNotUpdated);
            }
        }
    }
}
