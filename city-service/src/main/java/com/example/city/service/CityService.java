package com.example.city.service;

import com.example.city.dto.request.CityUpdateDto;
import com.example.city.dto.response.CityDto;
import com.example.city.dto.response.PaginatedCityDto;
import com.example.city.entity.City;
import com.example.city.exception.CityNotFoundException;
import com.example.city.mapper.CityMapper;
import com.example.city.repo.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public City findById(final long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("id", id));
    }

    public CityDto updateCity(final long id, CityUpdateDto cityUpdateDto) {
        var city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("id", id));
        log.info("[City Update] Updating the city id:{} name:{} image:{}", id, cityUpdateDto.getName(), cityUpdateDto.getImage());
        city.setName(cityUpdateDto.getName());
        city.setImage(cityUpdateDto.getImage());
        var data = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    public PaginatedCityDto findAll(final Pageable pageable) {
        var data = cityRepository.findAll(pageable);
        return cityMapper.toDto(data);
    }

    public PaginatedCityDto findByName(final String name, final Pageable pageable) {
        var data = cityRepository.findByNameIgnoreCase(name.toLowerCase(), pageable);
        return cityMapper.toDto(data);
    }
}
