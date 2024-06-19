package com.dogus.notebook.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetOutputDTO {
    private Long id;
    private String name;
    private String email;
}
