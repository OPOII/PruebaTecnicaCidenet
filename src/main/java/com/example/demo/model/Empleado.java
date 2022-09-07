package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="EMPLEADO_ID_GENERATOR", sequenceName="EMPLEADO_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "EMPLEADO_ID_GENERATOR")
    @Column
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @NotBlank(message = "surname is mandatory")
    //@Size(min=3,max=20,message ="The surname cant be more than {max} characters and less than {min}")
    @Column
    private String surname;

    @NotNull
    @NotBlank(message = "secondSurname is mandatory")
    //@Size(min=3,max=20,message ="The secondSurname cant be more than 20 characters and less than 3")
    @Column
    private String secondSurname;

    @NotNull
    @NotBlank(message = "firstName is mandatory")
    //@Size(min=3,max=20,message ="The secondSurname cant be more than 20 characters and less than 3")
    @Column
    private String firstName;

    @NotNull
    @NotBlank(message = "otherNames is mandatory")
    //@Size(min=3,max=50,message ="The otherNames cant be more than 20 characters and less than 3")
    @Column
    private String otherNames;

    @NotNull
    @NotBlank(message = "employeeCountry is mandatory")
    @Column
    private String employeeCountry;
    @NotNull
    @NotBlank(message = "IDType is mandatory")
    @Column
    private String IDType;
    @NotNull
    @NotBlank(message = "IDNumber is mandatory")
    //@Size(min=3,max=20,message = "The id number must be between 3 and 20 characteres ")
    @Column(unique = true)
    private String IDNumber;
    @Column(unique = true,length = 300)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String email;
    @NotNull
    @Column
    private Date startDate;
    @NotNull
    @NotBlank(message = "area is mandatory")
    @Column
    private String area;
    @Column
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String state;
    @Column
    @JsonFormat(pattern="dd/mm/yyyy HH:mm:ss")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date registerHour;
    @Column
    @JsonFormat(pattern="dd/mm/yyyy HH:mm:ss")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date editRegister;

}
