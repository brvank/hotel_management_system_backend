package com.project.hms.controller;

import com.project.hms.model_rdb.Room;
import com.project.hms.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/room")
public class RoomController extends ParentController{

    @Autowired
    RoomService roomService;

    @GetMapping("/get")
    public ResponseEntity<Object> get(HttpServletRequest header){
        return roomService.get(headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Room room, HttpServletRequest header){
        return roomService.add(room, headerToUser(header));
    }

    @GetMapping("/availability/{id}")
    public ResponseEntity<Object> getAvailability(@PathVariable(value = "id") int id, HttpServletRequest header){
        return roomService.getAvailability(id, headerToUser(header));
    }
}