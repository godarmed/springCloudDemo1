package com.springcloud.global.entity.DTO;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class StudentDTO {
    @NotBlank
    private String name;

    @NotNull
    @Max(120)
    @Min(1)
    private Integer age;

    
    private String gender;
}
