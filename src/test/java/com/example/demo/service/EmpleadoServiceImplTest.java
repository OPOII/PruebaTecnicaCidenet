package com.example.demo.service;

import com.example.demo.model.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repository;

    @InjectMocks
    private EmpleadoServiceImpl service;

    private Empleado actual;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        actual=new Empleado();
        actual.setId(Long.valueOf(1));
        actual.setSurname("Gimenez");
        actual.setSecondSurname("Gonzales");
        actual.setFirstName("Manuela");
        actual.setOtherNames("Manu");
        actual.setEmployeeCountry("Colombia");
        actual.setIDType("Cédula de Ciudadanía");
        actual.setIDNumber("123465687");
        actual.setStartDate(new Date());
        actual.setArea("Administracion");
    }

    @Test
    void getAll() {
    }

    @Test
    void findByEmail() throws Exception {

        when(repository.save(any(Empleado.class))).thenReturn(actual);
        when(repository.findByEmail("MANUELA.GIMENEZ@cidenet.com.co")).thenReturn(actual);
        assertNotNull(service.findByEmail("MANUELA.GIMENEZ@cidenet.com.co"));

    }

    @Test
    void findByIDNumber() {
        when(repository.findByIDNumber("123465687")).thenReturn(actual);
        assertNotNull(repository.findByIDNumber("123465687"));
        assertEquals(actual,repository.findByIDNumber("123465687"));
    }

    @Test
    void saveEmployee() throws Exception {
        when(repository.save(any(Empleado.class))).thenReturn(actual);
        assertNotNull(service.saveEmployee(actual));
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(repository.existsById(Long.valueOf("1")),true);
    }

    @Test
    void existByIDNumber() {
        when(repository.existsByIDNumber("123465687")).thenReturn(true);
        assertTrue(service.existByIDNumber("123465687"));
        when(repository.existsByIDNumber("222")).thenReturn(true);
        assertFalse(service.existByIDNumber("222"));
    }

    @Test
    void updateEmployee() throws Exception {
      /*  when(repository.save(any(Empleado.class))).thenReturn(actual);
        service.saveEmployee(actual);
        actual.setSurname("PRUEBA1");

        Empleado updated=service.updateEmployee(actual,1);
        assertNotEquals(updated.getSurname(),"Gimenez");
    */
    }


    @Test
    void validationsBeforeSave() throws Exception {
        Empleado validated=service.validationsBeforeSave(actual);
        assertNotNull(validated);
        assertEquals("MANUELA",validated.getFirstName());
        assertEquals("GONZALES",validated.getSecondSurname());
        assertEquals("GIMENEZ",validated.getSurname());
        assertEquals("MANUELA.GIMENEZ@cidenet.com.co",validated.getEmail());
        assertTrue(validated.getFirstName().length()>=3 && validated.getFirstName().length()<=20);
        assertTrue(validated.getSurname().length()>=3 && validated.getSurname().length()<=20);
        assertTrue(validated.getSecondSurname().length()>=3 && validated.getSecondSurname().length()<=20);
        assertTrue(validated.getOtherNames().length()>=3 && validated.getOtherNames().length()<=20);
        assertFalse(validated.getFirstName().contains("Ñ"));
        assertFalse(validated.getSurname().contains("Ñ"));
        assertFalse(validated.getSecondSurname().contains("Ñ"));
        assertFalse(validated.getOtherNames().contains("Ñ"));
        assertTrue(validated.getArea().matches("Administracion|Financiera|Compras|Infraestructura|Operación|Talento Humano|Servicios Varios"));
        assertTrue(validated.getEmployeeCountry().matches("Colombia|colombia|Estados Unidos|estados unidos"));
    }
    @Test
    void validationsBeforeSaveFails() throws Exception {
        actual.setSurname("h");
        Throwable exception=assertThrows(Exception.class,()-> service.validationsBeforeSave(actual));
        assertEquals("The surname must be between 3 and 20 characters",exception.getMessage());

        setUp();
        actual.setSecondSurname("s");
        Throwable exception1=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The second surname must be between 3 and 20 characters",exception1.getMessage());

        setUp();
        actual.setFirstName("dlknlkajdalkgndkgjakjgkajgakg");
        Throwable exception2=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The first name must be between 3 and 20 characters",exception2.getMessage());

        setUp();
        actual.setOtherNames("onomatopeyascelebres123");
        Throwable exception3=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The other names must be between 3 and 20 characters",exception3.getMessage());

        setUp();
        actual.setIDNumber("lkfjapdhoifpa5834684687676");
        Throwable exception4=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The id number must be between 3 and 20 characters",exception4.getMessage());

        setUp();
        actual.setSurname("GEÑER");
        Throwable exception5=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The surname cant contain an Ñ",exception5.getMessage());

        setUp();
        actual.setSecondSurname("MAÑOLO");
        Throwable exception6=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The secondSurname cant contain an Ñ",exception6.getMessage());

        setUp();
        actual.setFirstName("MUÑOZ");
        Throwable exception7=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The firstName cant contain an Ñ",exception7.getMessage());

        setUp();
        actual.setOtherNames("ÑOÑO");
        Throwable exception8=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The otherNames cant contain an Ñ",exception8.getMessage());

        setUp();
        actual.setEmployeeCountry("Noruega");
        Throwable exception9=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The country employee must be Colombia or Estados Unidos",exception9.getMessage());



        setUp();
        actual.setIDType("Tarjeta de identidad");

        Throwable exception11=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The id type must be Cédula de Ciudadanía, Cédula de Extranjería, Pasaporte or Permiso Especial",exception11.getMessage());

        setUp();
        actual.setArea("Desarrollo de software");
        Throwable exception12=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The area must be Administracion,Financiera,Compras,Infraestructura,Operación,Talento Humano or Servicios Varios",exception12.getMessage());

        setUp();
        String sDate1="31/12/2021";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        actual.setStartDate(date1);
        Throwable exception13=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The date must be 1 month before today and cant be after today",exception13.getMessage());

        setUp();
        actual.setIDNumber("*%$wywrriowulw#&/&");
        Throwable exception10=assertThrows(Exception.class,()->service.validationsBeforeSave(actual));
        assertEquals("The id number cant have special characteres",exception10.getMessage());
    }
}