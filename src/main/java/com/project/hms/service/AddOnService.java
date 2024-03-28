package com.project.hms.service;

import com.project.hms.model_rdb.AddOn;
import com.project.hms.model_rdb.User;
import com.project.hms.repository.addon.AddOnCustomRepository;
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
public class AddOnService {

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

    List<AddOn> addOns = new ArrayList<>();

    public ResponseEntity<Object> get(User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            addOns = addOnCustomRepository.get();
            return appResponse.successResponse(addOns, "");
        }
    }

    public ResponseEntity<Object> add(AddOn addOn, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {
                addOnCustomRepository.add(addOn);

                return appResponse.successResponse(success.addOnAdded);
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.addOnNotAdded);
            }
        }
    }

    public ResponseEntity<Object> update(AddOn addOn, User user){
        if(user == null){
            return appResponse.failureResponse(error.notAuthorized);
        }else{
            try {

                AddOn addOnExists = getAddOnIfExist(addOn.getAddon_id());

                if(addOnExists != null){
                    addOnCustomRepository.update(addOn);

                    return appResponse.successResponse(success.addOnUpdated);
                }else{
                    return appResponse.failureResponse(error.addOnDoesNotExist);
                }
            }catch (Exception e){
                e.printStackTrace();
                return appResponse.failureResponse(error.addOnNotUpdated);
            }
        }
    }

    public AddOn getAddOnIfExist(int id){
        for(AddOn addOn : addOns){
            if(Objects.equals(addOn.getAddon_id(), id)){
                return addOn;
            }
        }

        //fallback
        addOns = addOnCustomRepository.get();
        for(AddOn room : addOns){
            if(Objects.equals(room.getAddon_id(), id)){
                return room;
            }
        }

        return null;
    }
}
