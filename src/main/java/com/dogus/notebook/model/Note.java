package com.dogus.notebook.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private String title;
    private String noteDescription;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
