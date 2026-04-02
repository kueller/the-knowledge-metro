package com.glitch.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glitch.knowledge.model.Line;
import com.glitch.knowledge.repositories.LineRepository;

@Service
public class LineService {

    @Autowired
    private LineRepository lineRepository;

    /**
     * @param line_id ID of queried line 
     * @return {@link Line} if line exists, otherwise null
     */
    public Line get(int line_id) {
        return lineRepository.findById(line_id).orElse(null);
    }

}
