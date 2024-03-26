package com.project.hms.service;

import com.project.hms.model_rdb.User;
import com.project.hms.repository.user.UserCustomRepository;
import com.project.hms.response_model.AuthResponse;
import com.project.hms.utility.AppMessages;
import com.project.hms.utility.AppResponse;
import com.project.hms.utility.AppUtil;
import com.project.hms.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UserCustomRepository userCustomRepository;

    @Autowired
    AppResponse appResponse;
    @Autowired
    AppUtil.Constants appUtilConstants;
    @Autowired
    AppMessages.Success successMessages;
    @Autowired
    AppMessages.Error errorMessages;

    @Autowired
    TokenUtil tokenUtil;

    public ResponseEntity<Object> login(Map<String, String> requestMap){
        try{
            String userName = requestMap.getOrDefault(appUtilConstants.USER_NAME, null);
            String password = requestMap.getOrDefault(appUtilConstants.USER_PASSWORD, null);

            if(userName != null && password != null){
                List<User> users = userCustomRepository.getUserByNameAndPassword(userName, password);

                if(users.size() > 0){
                    String token = tokenUtil.generateToken(users.get(0));
                    AuthResponse authResponse = new AuthResponse(token);

                    return appResponse.successResponse(authResponse, successMessages.loggedIn);
                }else{
                    return appResponse.failureResponse(errorMessages.invalidCredentials);
                }
            }else{
                return appResponse.failureResponse(errorMessages.provideAllFields);
            }
        }catch (Exception e){
            e.printStackTrace();
            return appResponse.failureResponse(errorMessages.errorLogging);
        }
    }
}