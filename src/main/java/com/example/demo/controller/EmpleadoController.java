package com.example.demo.controller;

import com.example.demo.customHandler.ResponseHandler;
import com.example.demo.model.Empleado;
import com.example.demo.service.EmpleadoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmpleadoController {

    @Autowired
    private EmpleadoServiceImpl service;


    @Operation(summary="Register an employee")
    @PostMapping("/registerEmployee")
    public ResponseEntity<Object>registerEmployee(@RequestBody Empleado employee){
        try {
            Empleado result=service.saveEmployee(employee);
            return ResponseHandler.generateResponse("The user was added successfully", HttpStatus.OK,result);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
    @Operation(summary="Get the employees by the query parameters")
    @GetMapping("/employees")
    public ResponseEntity<Object>getEmployee(@Parameter(description = "Parameter filtered by first name",allowEmptyValue = true)String firstName,
                                             @Parameter(description = "Parameter filtered by surname",allowEmptyValue = true)String surname,
                                             @Parameter(description = "Parameter filtered by second surname",allowEmptyValue = true)String secondSurname,
                                             @Parameter(description = "Parameter filtered by other names",allowEmptyValue = true)String otherNames,
                                             @Parameter(description = "Parameter filtered by idNumber",allowEmptyValue = true)String idNumber,
                                             @Parameter(description = "Parameter filtered by employee country",allowEmptyValue = true)String employeeCountry,
                                             @Parameter(description = "Parameter filtered by email",allowEmptyValue = true)String email,
                                             @Parameter(description = "Parameter filtered by state",allowEmptyValue = true)String state,
                                             @Parameter(description = "Page you search")Integer page){

        try {
            return ResponseHandler.generateResponse("The records were founded successfully", HttpStatus.OK,service.getAll(firstName,surname,secondSurname,otherNames,idNumber,employeeCountry,email,state,page));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
    @Operation(summary = "Update the employee information")
    @PutMapping("/employee")
    public ResponseEntity<Object>updateEmployee(@RequestBody Empleado employee,@Parameter(description = "The id (database id) of the employee you want to update")Integer idDatabase){
        try {
            return ResponseHandler.generateResponse("The user was updated successfully", HttpStatus.OK,service.updateEmployee(employee,idDatabase));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
}
