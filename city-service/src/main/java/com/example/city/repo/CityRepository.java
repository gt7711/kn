package com.example.city.repo;

import com.example.city.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "select * from cities where LOWER(name) like :name%", nativeQuery = true)
    Page<City> findByNameIgnoreCase(final String name, final Pageable pageable);

    Page<City> findAll(final Pageable pageable);
}
