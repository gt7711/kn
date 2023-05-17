package com.example.city.service;

import com.example.city.entity.City;
import com.example.city.repo.CityRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CSVUtil {
    private final CityRepository cityRepository;

    @PostConstruct
    public void readCsvFile() {
        var resource = new ClassPathResource("sample.csv");
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            var csvToBean = new CsvToBeanBuilder<City>(reader)
                    .withType(City.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<City> myObjects = csvToBean.parse();
            cityRepository.saveAll(myObjects);
        } catch (IOException e) {
            log.error("Unable to load city list.", e);
        }
    }
}
