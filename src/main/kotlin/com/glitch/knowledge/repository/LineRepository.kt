package com.glitch.knowledge.repository

import com.glitch.knowledge.model.Line
import org.springframework.data.repository.CrudRepository

interface LineRepository : CrudRepository<Line, Int> {}