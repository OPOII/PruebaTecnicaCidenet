package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="AREA_ID_GENERATOR", sequenceName="AREA_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AREA_ID_GENERATOR")
    @Column
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Column
    private String name;
}
