package com.glitch.knowledge.service

import com.glitch.knowledge.model.Line
import com.glitch.knowledge.repository.LineRepository
import org.springframework.stereotype.Service

@Service
class LineService(private var lineRepository: LineRepository) {
    /**
     * @param line_id ID of queried line
     * @return [Line] if line exists, otherwise null
     */
    fun get(line_id: Int): Line? {
        return lineRepository.findById(line_id).orElse(null)
    }
}