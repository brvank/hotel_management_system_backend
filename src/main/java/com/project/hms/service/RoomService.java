package com.project.hms.service;

import com.project.hms.model_nrdb.RoomInventory;
import com.project.hms.model_rdb.Room;
import com.project.hms.model_rdb.RoomCategory;
import com.project.hms.model_rdb.User;
import com.project.hms.repository.room.RoomCustomRepository;
import com.project.hms.repository.roomcategory.RoomCategoryCustomRepository;
import com.project.hms.repository.roominventory.RoomInventoryCustomRepository;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class RoomService {

    @Autowired
    RoomCustomRepository roomCustomRepository;

    @Autowired
    RoomInventoryCustomRepository roomInventoryCustomRepository;

    @Autowired
    RoomCategoryCustomRepository roomCategoryCustomRepository;

    @Autowired
    AppUtil.Constants appUtilConstants;

    @Autowired
    AppResponse appResponse;

    @Autowired
    AppMessages.Success success;

    @Autowired
    AppMessages.Error error;

    List<Room> rooms = new ArrayList<>();

    public ResponseEntity<Object> get(User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            rooms = roomCustomRepository.get();
            return appResponse.successResponse(rooms, "");
        }
    }

    public ResponseEntity<Object> add(Room room, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                if(roomCategoryCustomRepository.get(room.getRoom_category_id()) == null){
                    return appResponse.failureResponse(error.roomCategoryDoesNotExist);
                }

                room = roomCustomRepository.add(room);

                RoomInventory roomInventory = new RoomInventory();
                roomInventory.setRoom_id(room.getRoom_id());
                roomInventory.setBookings(new HashMap<>());

                roomInventoryCustomRepository.add(roomInventory);

                return appResponse.successResponse(success.roomAdded);
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.roomNotAdded);
            }
        }
    }

    public ResponseEntity<Object> update(Room room, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                Room roomExists = getRoomIfExist(room.getRoom_id());

                if(roomExists != null){
                    roomCustomRepository.update(room);

                    return appResponse.successResponse(success.roomCategoryUpdated);
                }else{
                    return appResponse.failureResponse(error.roomCategoryDoesNotExist);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.roomCategoryNotUpdated);
            }
        }
    }

    public ResponseEntity<Object> getAvailability(int id, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                Room room = getRoomIfExist(id);

                if(room == null){
                    return appResponse.failureResponse(error.roomCategoryDoesNotExist);
                }else{
                    RoomInventory roomInventory = roomInventoryCustomRepository.get(id);

                    room.setRoomInventory(roomInventory);

                    return appResponse.successResponse(room, "");
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.unknownErrorOccurred);
            }
        }
    }

    public Room getRoomIfExist(int id){
        for(Room room : rooms){
            if(Objects.equals(room.getRoom_id(), id)){
                return room;
            }
        }

        //fallback
        rooms = roomCustomRepository.get();
        for(Room room : rooms){
            if(Objects.equals(room.getRoom_id(), id)){
                return room;
            }
        }

        return null;
    }
}
