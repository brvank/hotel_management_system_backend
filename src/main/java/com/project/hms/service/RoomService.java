package com.project.hms.service;

import com.project.hms.model.Room;
import com.project.hms.model.User;
import com.project.hms.repository.RoomCustomRepository;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomCustomRepository roomCustomRepository;

    @Autowired
    AppUtil.Constants appUtilConstants;

    @Autowired
    AppResponse appResponse;

    @Autowired
    AppMessages.Success success;

    @Autowired
    AppMessages.Error error;

    List<Room> rooms = new ArrayList<>();

    public ResponseEntity<Object> getRooms(User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            rooms = roomCustomRepository.getRooms();
            return appResponse.successResponse(rooms, "");
        }
    }

    public ResponseEntity<Object> addRoom(Room room, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                roomCustomRepository.addRoom(room);

                return appResponse.successResponse(success.roomAdded);
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.roomNotAdded);
            }
        }
    }

    public Room getRoomIfExist(int id){
        for(Room room : rooms){
            if(room.getRoom_id() == id){
                return room;
            }
        }

        //fallback
        rooms = roomCustomRepository.getRooms();
        for(Room room : rooms){
            if(room.getRoom_id() == id){
                return room;
            }
        }

        return null;
    }
}
