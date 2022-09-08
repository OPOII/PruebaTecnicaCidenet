package com.example.demo.service;

import com.example.demo.model.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class EmpleadoServiceImpl implements IEmpleadoService{

    @Autowired
    private EmpleadoRepository repository;
    @Override
    public List<Empleado> getAll(String firstName,
                                 String surname,
                                 String secondSurname,
                                 String otherNames,
                                 String idNumber,
                                 String employeeCountry,
                                 String email,
                                 String state,int page) {
        System.out.println("Entra antes de"+firstName+" "+surname);
        if(firstName.isEmpty()){
            firstName=null;
        }else{
            firstName=firstName.toUpperCase();
        }
        if(surname.isEmpty()){
            surname=null;
        }else{
            surname=surname.toUpperCase();
        }
        if(secondSurname.isEmpty()){
            secondSurname=null;
        }else{
            secondSurname=secondSurname.toUpperCase();
        }
        if(otherNames.isEmpty()){
            otherNames=null;
        }else{
            otherNames=otherNames.toUpperCase();
        }
        if(idNumber.isEmpty()){
            idNumber=null;
        }
        if(employeeCountry.isEmpty()){
            employeeCountry=null;
        }
        if(email.isEmpty()){
            email=null;
        }
        if(state.isEmpty()){
            state=null;
        }
        List<Empleado>listado= repository.findWithParametersIgnoreCase(firstName,surname,secondSurname,otherNames,idNumber,employeeCountry,email,state);
        System.out.println("Entra despues de");
        System.out.println(listado.size());
       if(listado.size()<10){
           return listado;
       }else{
           List<Empleado> sublist=null;
           if(page>1) {
            int end=page*10;
            int start=end-10;
           return listado.subList(start,end);
           }else{
        return listado.subList(0,10);
           }

       }
    }

    @Override
    public Empleado findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Empleado findByIDNumber(String idNumber) {
        return repository.findByIDNumber(idNumber);
    }

    @Override
    public Empleado saveEmployee(Empleado employee)throws Exception {
        try {
         Empleado verificated=this.validationsBeforeSave(employee);
        return repository.save(employee);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public boolean existByIDNumber(String idNumber) {
        return repository.existsByIDNumber(idNumber);
    }

    @Override
    public Empleado updateEmployee(Empleado employee,int id)throws Exception {
        Long ids= (long) id;
        employee.setId(ids);
        Empleado actual=repository.findById(ids).get();
        try{

            if(actual.getEmployeeCountry().equalsIgnoreCase(employee.getEmployeeCountry())||
                    actual.getFirstName().equalsIgnoreCase(employee.getFirstName())||
                    actual.getSurname().equalsIgnoreCase(employee.getSurname())||
                    actual.getEmployeeCountry().equalsIgnoreCase(employee.getEmployeeCountry())){
                if(actual.getIDNumber().equalsIgnoreCase(employee.getIDNumber())){
                    //Maniobra para evitar que salga una excepcion al hacer este update debido
                    //a que si hago un update sin cambiar el idNumber, tirara error porque debe de ser unico
                    actual.setIDNumber("emptys");
                    repository.saveAndFlush(actual);
                }
                employee=this.validationsBeforeSave(employee);
            }

        employee.setEditRegister(new Date());
        return repository.saveAndFlush(employee);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public Empleado validationsBeforeSave(Empleado employe)throws Exception{

        if(!(employe.getSurname().length()>=3 && employe.getSurname().length()<=20)){
            throw new Exception("The surname must be between 3 and 20 characters");
        }
        if(!(employe.getSecondSurname().length()>=3 && employe.getSecondSurname().length()<=20)){
            throw new Exception("The second surname must be between 3 and 20 characters");
        }
        if(!(employe.getFirstName().length()>=3 && employe.getFirstName().length()<=20)){
            throw new Exception("The first name must be between 3 and 20 characters");
        }
        if(!(employe.getOtherNames().length()>=3 && employe.getOtherNames().length()<=20)){
            throw new Exception("The other names must be between 3 and 20 characters");
        }
        if(!(employe.getIDNumber().length()>=3 && employe.getIDNumber().length()<=20)){
            throw new Exception("The id number must be between 3 and 20 characters");
        }
        employe.setSurname(employe.getSurname().toUpperCase());
        employe.setSecondSurname(employe.getSecondSurname().toUpperCase());
        employe.setOtherNames(employe.getOtherNames().toUpperCase());
        employe.setFirstName(employe.getFirstName().toUpperCase());

        if(employe.getSurname().contains("Ñ")){
            throw new Exception("The surname cant contain an Ñ");
        }
        if(employe.getSecondSurname().contains("Ñ")){
            throw new Exception("The secondSurname cant contain an Ñ");
        }
        if(employe.getFirstName().contains("Ñ")){
            throw new Exception("The firstName cant contain an Ñ");
        }
        if(employe.getOtherNames().contains("Ñ")){
            throw new Exception("The otherNames cant contain an Ñ");
        }
        if(!employe.getEmployeeCountry().matches("Colombia|colombia|Estados Unidos|estados unidos")){
            throw new Exception("The country employee must be Colombia or Estados Unidos");
        }
        if(!employe.getIDNumber().matches("^[-\\w.]+")){
            throw new Exception("The id number cant have special characteres");
        }
        boolean existEmployee=this.existByIDNumber(employe.getIDNumber());
        if(existEmployee){
            throw new Exception("The employee with the id "+employe.getIDNumber()+" already exist");
        }
        String regionDomain="cidenet.com.us";
        if(employe.getEmployeeCountry().equalsIgnoreCase("colombia")){
           regionDomain="cidenet.com.co";
        }
        if(!employe.getIDType().matches("Cédula de Ciudadanía|Cédula de Extranjería|Pasaporte|Permiso Especial")){
            throw new Exception("The id type must be Cédula de Ciudadanía, Cédula de Extranjería, Pasaporte or Permiso Especial");
        }
        //Email construction
        String firstPart=employe.getFirstName()+"."
                +employe.getSurname().replace(" ","");
        String secondPart=
                "@"+regionDomain;
        String emailGenerate=firstPart+secondPart;
        boolean existEmail= repository.existsByEmail(emailGenerate);
        boolean stop=true;
        int c=1;
        if(existEmail){
            while(stop){
                existEmail=repository.existsByEmail(firstPart+c+secondPart);
                if(existEmail){
                    c++;
                }else{
                    break;
                }
            }
            emailGenerate=firstPart+"."+c+secondPart;
        }
        employe.setEmail(emailGenerate);
        System.out.println("---------------------------");
        System.out.println(emailGenerate);
        Calendar calendar=Calendar.getInstance();

        Date fechaActual=new Date();
        Date mesPasado=new Date();
        calendar.setTime(mesPasado);
        calendar.add(Calendar.MONTH,-1);
        mesPasado=calendar.getTime();
        if(!(employe.getStartDate().after(mesPasado)&& employe.getStartDate().before(fechaActual))){
            throw new Exception("The date must be 1 month before today and cant be after today");
        }
        String employeeArea=employe.getArea();
        if(!employeeArea.matches("Administracion|Financiera|Compras|Infraestructura|Operación|Talento Humano|Servicios Varios")){
        throw new Exception("The area must be Administracion,Financiera,Compras,Infraestructura,Operación,Talento Humano or Servicios Varios");
        }
        employe.setRegisterHour(fechaActual);
        employe.setState("Active");
        String surname=employe.getSurname();
        String secondSurname=employe.getSecondSurname();
        String firstName=employe.getFirstName();
        surname= Normalizer.normalize(surname, Normalizer.Form.NFD);
        surname=surname.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        secondSurname= Normalizer.normalize(secondSurname, Normalizer.Form.NFD);
        secondSurname=secondSurname.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        firstName= Normalizer.normalize(firstName, Normalizer.Form.NFD);
        firstName=firstName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        employe.setSurname(surname);
        employe.setSecondSurname(secondSurname);
        employe.setFirstName(firstName);
        return employe;
    }



}
