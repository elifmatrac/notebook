package com.dogus.notebook.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCreateInputDTO {

    private String title;
    private String noteDescription;
}
