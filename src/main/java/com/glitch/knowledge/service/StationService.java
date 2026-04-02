package com.glitch.knowledge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glitch.knowledge.model.Station;
import com.glitch.knowledge.repositories.StationRepository;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    /**
     * @return Number of stations in the database
     */
    public long numberOfStations() {
        return stationRepository.count();
    }

    /**
     * @param id ID of queried station
     * @return {@link Station} if station exists, otherwise null
     */
    public Station getById(int id) {
        return stationRepository.findById(id).orElse(null);
    }

    /**
     * @return List of {@link Station}s of size 2 containing two randomly chosen
     *         stations.
     */
    public List<Station> getTwoRandom() {
        List<Station> stations = new ArrayList<Station>();
        stationRepository.findTwoRandom().forEach(stations::add);
        return stations;
    }

}
