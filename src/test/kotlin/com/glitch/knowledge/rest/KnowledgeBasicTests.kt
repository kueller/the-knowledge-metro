package com.glitch.knowledge.rest

import com.glitch.knowledge.KnowledgeApplication
import com.glitch.knowledge.testutil.lineMap
import com.glitch.knowledge.testutil.readStationListJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(classes = [KnowledgeApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KnowledgeBasicTests {
    @field:Autowired
    lateinit var mvc: MockMvc

    var stationMap: Map<String, Int> = mapOf()
    var lineMap: Map<String, Int> = mapOf()

    init {
        stationMap = readStationListJson()
        lineMap = lineMap()
    }

    @Test
    fun `simple step verification that succeeds`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["3bis"],
            stationMap["Gambetta"],
            stationMap["Pelleport"],
            stationMap["Porte des Lilas"],
        )

    }

    @Test
    fun `wrong answer, origin is not serviced by the line`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["2"],
            stationMap["Gare de Lyon"],
            stationMap["Avron"],
            stationMap["Nation"],
            "origin-not-on-line"
        )
    }

    @Test
    fun `wrong answer, destination is not serviced by the line`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["1"],
            stationMap["Gare de Lyon"],
            stationMap["Avron"],
            stationMap["Château de Vincennes"],
            "dest-not-on-line"
        )
    }

    @Test
    fun `wrong answer, need to take other direction to reach destination`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["1"],
            stationMap["Gare de Lyon"],
            stationMap["Bastille"],
            stationMap["Château de Vincennes"],
            "wrong-way"
        )
    }
}