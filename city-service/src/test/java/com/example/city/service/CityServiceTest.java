package com.example.city.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.city.dto.request.CityUpdateDto;
import com.example.city.entity.City;
import com.example.city.exception.CityNotFoundException;
import com.example.city.mapper.CityMapper;
import com.example.city.repo.CityRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;
    @Spy
    private CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private City buildCity() {
        var city = new City();
        city.setId(1L);
        city.setName("Dublin");
        city.setImage("dublin.jpg");
        return city;
    }

    private CityUpdateDto buildCityUpdateDto() {
        var cityUpdateDto = new CityUpdateDto();
        cityUpdateDto.setName("Tallin");
        cityUpdateDto.setImage("tallin.jpg");
        return cityUpdateDto;
    }

    @Test
    public void getAllCities() {
        var cities = new ArrayList<City>();
        cities.add(new City(1L, "New York", "image1.jpg"));
        cities.add(new City(2L, "London", "image2.jpg"));
        cities.add(new City(3L, "Paris", "image3.jpg"));

        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<City> cityPage = new PageImpl<>(cities, pageable, 3);

        Mockito.when(cityRepository.findAll(pageable)).thenReturn(cityPage);

        var cityResponseList = cityService.findAll(pageable);

        verify(cityRepository, times(1)).findAll(pageable);

        Assertions.assertEquals(cities.size(), cityResponseList.getTotalElements());
        Assertions.assertEquals(cities.get(0).getName(), cityResponseList.getContent().get(0).getName());
        Assertions.assertEquals(cities.get(0).getImage(), cityResponseList.getContent().get(0).getImage());
        Assertions.assertEquals(cities.get(0).getId(), cityResponseList.getContent().get(0).getId());
    }

    @Test
    public void getAllCitiesByName() {
        var cities = new ArrayList<City>();
        cities.add(new City(1L, "Paris2", "image1.jpg"));
        cities.add(new City(2L, "Paris0", "image2.jpg"));
        cities.add(new City(3L, "Paris1", "image3.jpg"));

        var searchTerm = "Paris";
        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<City> cityPage = new PageImpl<>(cities, pageable, 3);

        Mockito.when(cityRepository.findByNameIgnoreCase(searchTerm.toLowerCase(), pageable)).thenReturn(cityPage);

        var cityResponseList = cityService.findByName(searchTerm, pageable);

        verify(cityRepository, times(1)).findByNameIgnoreCase(searchTerm.toLowerCase(), pageable);

        Assertions.assertEquals(cities.size(), cityResponseList.getTotalElements());
        cityResponseList.getContent()
                .forEach(city -> Assertions.assertTrue(city.getName().toLowerCase().contains(searchTerm.toLowerCase())));
    }

    @Test
    void givenInvalidCityId_whenUpdateCity_thenThrowCityNotFoundException() {
        var city = this.buildCity();
        var cityUpdateDto = this.buildCityUpdateDto();

        Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cityService.updateCity(city.getId(), cityUpdateDto)).isInstanceOf(CityNotFoundException.class)
                .hasMessageContaining("City not found with id: '" + city.getId() + "'");
    }

    @Test
    void givenValidCityId_whenUpdateCity_thenReturnSuccessResponse() {
        var city = this.buildCity();
        var cityUpdateDto = this.buildCityUpdateDto();

        Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
        var response = cityService.updateCity(city.getId(), cityUpdateDto);
        Assertions.assertEquals(cityUpdateDto.getName(), response.getName());
        Assertions.assertEquals(cityUpdateDto.getImage(), response.getImage());
    }
}