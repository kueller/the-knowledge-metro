package com.glitch.knowledge.repository

import com.glitch.knowledge.model.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StationRepository : JpaRepository<Station, Int> {

    @Query(value = "SELECT * FROM station ORDER BY RAND() LIMIT 2", nativeQuery = true)
    fun findTwoRandom(): List<Station>
}