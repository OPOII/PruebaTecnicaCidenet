package com.example.demo.service;

import com.example.demo.model.Empleado;

import java.util.List;

public interface IEmpleadoService {

    List<Empleado> getAll(String firstName,
                          String surname,
                          String secondSurname,
                          String otherNames,
                          String idNumber,
                          String employeeCountry,
                          String email,
                          String state,
                          int page);
    Empleado findByEmail(String email);
    Empleado findByIDNumber(String idNumber);
    Empleado saveEmployee(Empleado employee)throws Exception;
    boolean existByIDNumber(String idNumber);

    Empleado updateEmployee(Empleado employee,int id)throws Exception;

}
