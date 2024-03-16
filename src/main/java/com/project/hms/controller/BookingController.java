package com.project.hms.controller;

import com.project.hms.model.Booking;
import com.project.hms.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/booking")
public class BookingController extends ParentController{

    @Autowired
    BookingService bookingService;

    @GetMapping("/get")
    public ResponseEntity<Object> getBookings(@RequestParam int start, @RequestParam int size, HttpServletRequest header){
        return bookingService.getBookings(start, size, headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addBooking(@RequestBody Booking booking, HttpServletRequest header){
        return bookingService.addBooking(booking, headerToUser(header));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateBooking(@RequestBody Booking booking, HttpServletRequest header){
        return bookingService.updateBooking(booking, headerToUser(header));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getBookingWithId(@PathVariable(value = "id") int id, HttpServletRequest header){
        return bookingService.getBookingWithId(id, headerToUser(header));
    }

}