package com.project.hms.service;

import com.project.hms.model_rdb.RoomCategory;
import com.project.hms.model_rdb.User;
import com.project.hms.repository.roomcategory.RoomCategoryCustomRepository;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoomCategoryService {

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

    List<RoomCategory> roomCategories = new ArrayList<>();

    public ResponseEntity<Object> get(User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            roomCategories = roomCategoryCustomRepository.get();
            return appResponse.successResponse(roomCategories, "");
        }
    }

    public ResponseEntity<Object> add(RoomCategory roomCategory, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                roomCategoryCustomRepository.add(roomCategory);

                return appResponse.successResponse(success.roomAdded);
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.roomNotAdded);
            }
        }
    }

    public ResponseEntity<Object> update(RoomCategory roomCategory, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                RoomCategory roomCategoryExists = getRoomCategoryIfExist(roomCategory.getRoom_category_id());

                if(roomCategoryExists != null){
                    roomCategoryCustomRepository.update(roomCategory);

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

    public RoomCategory getRoomCategoryIfExist(int id){
        for(RoomCategory roomCategory : roomCategories){
            if(Objects.equals(roomCategory.getRoom_category_id(), id)){
                return roomCategory;
            }
        }

        //fallback
        roomCategories = roomCategoryCustomRepository.get();
        for(RoomCategory room : roomCategories){
            if(Objects.equals(room.getRoom_category_id(), id)){
                return room;
            }
        }

        return null;
    }
}
