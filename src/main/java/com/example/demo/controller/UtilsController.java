package com.example.demo.controller;

import com.example.demo.customHandler.ResponseHandler;
import com.example.demo.service.UtilsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
@CrossOrigin
public class UtilsController {

    @Autowired
    private UtilsServiceImpl service;

    @Operation(summary = "This method returns the id types")
    @GetMapping("/getIDTypes")
    public ResponseEntity<Object>getIDTypes(){
        return ResponseHandler.generateResponse("Here are the id types available", HttpStatus.OK,service.getIDTypes());
    }
    @Operation(summary = "This method returns the employee countries available to work")
    @GetMapping("/getEmployeeCountries")
    public ResponseEntity<Object>getEmployeeCountries(){
        return ResponseHandler.generateResponse("Here are the employee countries available", HttpStatus.OK,service.getEmployeeCountries());

    }
    @Operation(summary = "This method returns the areas available")
    @GetMapping("/getAreas")
    public ResponseEntity<Object>getAreas(){
        return ResponseHandler.generateResponse("Here are the areas available", HttpStatus.OK,service.getAreas());

    }
}
