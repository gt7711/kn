package com.example.city.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedCityDto extends PageDto {
    private List<CityDto> content;
}
