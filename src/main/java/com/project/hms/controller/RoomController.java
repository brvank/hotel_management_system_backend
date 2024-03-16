package com.project.hms.controller;

import com.project.hms.model.Room;
import com.project.hms.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = "api/v1/room")
public class RoomController extends ParentController{

    @Autowired
    RoomService roomService;

    @GetMapping("/get")
    public ResponseEntity<Object> getRooms(HttpServletRequest header){
        return roomService.getRooms(headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addRooms(@RequestBody Room room, HttpServletRequest header){
        return roomService.addRoom(room, headerToUser(header));
    }
}