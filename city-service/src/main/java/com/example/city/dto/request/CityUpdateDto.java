package com.example.city.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CityUpdateDto {
    @NotBlank
    private String name;
    @Length(max = 1000)
    private String image;
}
