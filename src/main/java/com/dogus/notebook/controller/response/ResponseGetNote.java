package com.dogus.notebook.controller.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetNote {
    private Long id;
    private LocalDate date;
    private String title;
    private String noteDescription;
}
