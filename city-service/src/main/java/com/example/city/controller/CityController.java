package com.example.city.controller;

import com.example.city.dto.request.CityUpdateDto;
import com.example.city.dto.response.CityDto;
import com.example.city.dto.response.PaginatedCityDto;
import com.example.city.entity.City;
import com.example.city.service.CityService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping("")
    @CrossOrigin
    public PaginatedCityDto getCities(@RequestParam(required = false) final String searchTerm,
                                      @RequestParam(required = false, defaultValue = "0") final int page,
                                      @RequestParam(required = false, defaultValue = "10") final int size) {
        var pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return cityService.findByName(searchTerm, pageRequest);
        } else {
            return cityService.findAll(pageRequest);
        }
    }

    @GetMapping("/{id}")
    public City getCity(@PathVariable final long id) {
        return cityService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CityDto updateCity(@PathVariable final long id, @Valid @RequestBody CityUpdateDto cityUpdateDto) {
        return cityService.updateCity(id, cityUpdateDto);
    }
}
