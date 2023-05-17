package com.example.city.mapper;

import com.example.city.dto.response.CityDto;
import com.example.city.dto.response.PaginatedCityDto;
import com.example.city.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {
    CityDto toDto(final City city);

    @Mapping(source = "cityPage.content", target = "content")
    PaginatedCityDto toDto(final Page<City> cityPage);
}