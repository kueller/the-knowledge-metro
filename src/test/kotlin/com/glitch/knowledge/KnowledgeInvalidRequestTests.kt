package com.glitch.knowledge

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KnowledgeInvalidRequestTests {
    @field:Autowired
    lateinit var mvc: MockMvc

    var stationMap: Map<String, Int> = mapOf()
    var lineMap: Map<String, Int> = mapOf()

    init {
        stationMap = readStationListJson()
        lineMap = lineMap()
    }

    @Test
    fun `invalid request because origin = destination`() {
        runVerificationTestInvalid(
            mvc,
            lineMap["2"],
            stationMap["Avron"],
            stationMap["Avron"],
            stationMap["Porte Dauphine"],
        )
    }

    @Test
    fun `invalid request, the line id does not exist`() {
        runVerificationTestInvalid(
            mvc,
            500,
            stationMap["Nation"],
            stationMap["Avron"],
            stationMap["La Défense"],
        )
    }

    @Test
    fun `invalid request, the origin id does not exist`() {
        runVerificationTestInvalid(
            mvc,
            lineMap["1"],
            500,
            stationMap["Bastille"],
            stationMap["La Défense"],
        )
    }

    @Test
    fun `invalid request, the destination id does not exist`() {
        runVerificationTestInvalid(
            mvc,
            lineMap["1"],
            stationMap["Gare de Lyon"],
            500,
            stationMap["La Défense"],
        )
    }

    @Test
    fun `invalid request, the direction id does not exist`() {
        runVerificationTestInvalid(
            mvc,
            lineMap["1"],
            stationMap["Gare de Lyon"],
            stationMap["Bastille"],
            500,
        )
    }

    @Test
    fun `invalid request, the direction is not a terminus of the line`() {
        runVerificationTestInvalid(
            mvc,
            lineMap["1"],
            stationMap["Gare de Lyon"],
            stationMap["Bastille"],
            stationMap["Porte Dauphine"],
        )
    }
}