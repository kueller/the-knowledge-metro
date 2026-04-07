package com.glitch.knowledge.service

import com.glitch.knowledge.model.Station
import com.glitch.knowledge.repository.StationRepository
import org.springframework.stereotype.Service

@Service
class StationService(private val stationRepository: StationRepository) {
    fun getById(id: Int): Station? {
        return stationRepository.findById(id).orElse(null)
    }

    fun getTwoRandom(): List<Station> {
        return stationRepository.findTwoRandom()
    }
}