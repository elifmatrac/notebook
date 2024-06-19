package com.dogus.notebook.controller.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateNote {

    private LocalDate date;
    private String title;
    private String noteDescription;
}
