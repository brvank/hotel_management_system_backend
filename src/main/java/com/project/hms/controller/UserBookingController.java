package com.project.hms.controller;

import com.project.hms.model_rdb.AddOn;
import com.project.hms.model_rdb.UserBooking;
import com.project.hms.service.UserBookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping(value = "api/v1/booking")
public class UserBookingController extends ParentController{

    @Autowired
    UserBookingService userBookingService;

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam int start, @RequestParam int size, HttpServletRequest header){
        return userBookingService.get(start, size, headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody UserBooking userBooking, HttpServletRequest header){
        return userBookingService.add(userBooking, headerToUser(header));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody UserBooking userBooking, HttpServletRequest header){
        return userBookingService.update(userBooking, headerToUser(header));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") int id, HttpServletRequest header){
        return userBookingService.getById(id, headerToUser(header));
    }

    @PutMapping("/addOn/{id}")
    public ResponseEntity<Object> addOn(@PathVariable(value = "id") int id, @RequestBody Map<Integer, Integer> addonList, HttpServletRequest header){
        return userBookingService.addOn(id, addonList, headerToUser(header));
    }

    @PutMapping("/syncAddOnPrice/{id}")
    public ResponseEntity<Object> updateAddOnPrice(@PathVariable(value = "id") int id, HttpServletRequest header){
        return userBookingService.updateAddOnPrice(id, headerToUser(header));
    }

}