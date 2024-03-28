package com.project.hms.controller;

import com.project.hms.model_rdb.AddOn;
import com.project.hms.service.AddOnService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/addOn")
public class AddOnController extends ParentController{

    @Autowired
    AddOnService addOnService;

    @GetMapping("/get")
    public ResponseEntity<Object> get(HttpServletRequest header){
        return addOnService.get(headerToUser(header));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody AddOn addOn, HttpServletRequest header){
        return addOnService.add(addOn, headerToUser(header));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AddOn addOn, HttpServletRequest header){
        return addOnService.update(addOn, headerToUser(header));
    }
}