package com.project.hms.controller;

import com.project.hms.model_rdb.Room;
import com.project.hms.model_rdb.RoomCategory;
import com.project.hms.service.RoomCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/roomCategory")
public class RoomCategoryController extends ParentController{

    @Autowired
    RoomCategoryService roomCategoryService;

    @GetMapping("/get")
    public ResponseEntity<Object> get(HttpServletRequest header){
        return roomCategoryService.get(headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody RoomCategory roomCategory, HttpServletRequest header){
        return roomCategoryService.add(roomCategory, headerToUser(header));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody RoomCategory roomCategory, HttpServletRequest header){
        return roomCategoryService.update(roomCategory, headerToUser(header));
    }
}