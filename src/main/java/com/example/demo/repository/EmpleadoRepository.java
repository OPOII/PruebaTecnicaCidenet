package com.example.demo.repository;

import com.example.demo.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

        Empleado findByEmail(String email);
        Empleado findByIDNumber(String IDNumber);

        boolean existsByIDNumber(String idNumber);
        boolean existsByEmail(String email);
        @Query(value = "select u from Empleado u where (:name is null or u.firstName = :name) " +
                "and (:surname is null or u.surname = :surname) and (:secondSurname is null or u.secondSurname = :secondSurname)" +
                " and (:otherNames is null or u.otherNames = :otherNames) and (:IDNumber is null or u.IDNumber = :IDNumber)" +
                " and (:employeeCountry is null or u.employeeCountry = :employeeCountry) and (:email is null or u.email = :email) and (:state is null or u.state = :state)")
        List<Empleado> findWithParametersIgnoreCase(@Param("name") String firstName,@Param("surname") String surname,@Param("secondSurname") String secondSurname
                , @Param("otherNames")String otherNames, @Param("IDNumber")String idNumber, @Param("employeeCountry")String employeeCountry, @Param("email")String email, @Param("state")String state);

}
