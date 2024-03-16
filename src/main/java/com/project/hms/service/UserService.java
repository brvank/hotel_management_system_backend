package com.project.hms.service;

import com.project.hms.repository.UserCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserCustomRepository userCustomRepository;


//    public ResponseEntity<Object> getUserDetails(){
//
//    }

}
