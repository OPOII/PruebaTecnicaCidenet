package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UtilsServiceImpl implements IUtilsService {

    private List<String> idTypes= Arrays.asList("Cédula de Ciudadanía","Cédula de Extranjería","Pasaporte","Permiso Especial");
    private List<String> employeesCountries= Arrays.asList("Colombia","Estados Unidos");
    private List<String> getAreas= Arrays.asList("Administracion","Financiera","Compras","Infraestructura","Operación","Talento Humano","Servicios Varios");

    @Override
    public List<String> getIDTypes() {
        return idTypes;
    }

    @Override
    public List<String> getEmployeeCountries() {
        return employeesCountries;
    }

    @Override
    public List<String> getAreas() {
        return getAreas;
    }
}
