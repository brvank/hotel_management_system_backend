package com.project.hms.service;

import com.project.hms.repository.user.UserCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserCustomRepository userCustomRepository;


//    public ResponseEntity<Object> getUserDetails(){
//
//    }

}
