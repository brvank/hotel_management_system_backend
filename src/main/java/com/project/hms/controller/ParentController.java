package com.project.hms.controller;

import com.project.hms.model_rdb.User;
import com.project.hms.utility.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class ParentController {

    @Autowired
    TokenUtil tokenUtil;

    public User headerToUser(HttpServletRequest header){
        try{
            String token = header.getHeader("Authorization");
            return tokenUtil.extractUserFromToken(token);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}