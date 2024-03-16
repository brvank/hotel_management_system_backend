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

import java.util.List;
import java.util.Map;

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

    public ResponseEntity<Object> getRooms(User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            return appResponse.successResponse(roomCustomRepository.getRooms(), "");
        }
    }

    public ResponseEntity<Object> addRoom(Map<String, Object> roomMap, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                Room room = new Room();

                room.setRoom_category((String) roomMap.get(appUtilConstants.ROOM_CATEGORY));
                room.setRoom_info((String) roomMap.get(appUtilConstants.ROOM_INFO));
                room.setRoom_price(((int) roomMap.get(appUtilConstants.ROOM_PRICE)*1.0f));

                roomCustomRepository.addRoom(room);

                return appResponse.successResponse(success.roomAdded);
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.roomNotAdded);
            }
        }
    }
}
