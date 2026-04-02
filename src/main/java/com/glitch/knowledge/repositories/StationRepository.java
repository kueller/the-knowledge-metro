package com.glitch.knowledge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.glitch.knowledge.model.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {

    @Query(value = "SELECT * FROM station ORDER BY RAND() LIMIT 2", nativeQuery = true)
    Iterable<Station> findTwoRandom();
}
