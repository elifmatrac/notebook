package com.dogus.notebook.service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteGetOutputDTO {
    private Long id;
    private LocalDate date;
    private String title;
    private String noteDescription;
}
