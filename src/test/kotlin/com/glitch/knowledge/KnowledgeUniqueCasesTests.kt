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
class KnowledgeUniqueCasesTests {
    @field:Autowired
    lateinit var mvc: MockMvc

    var stationMap: Map<String, Int> = mapOf()
    var lineMap: Map<String, Int> = mapOf()

    init {
        stationMap = readStationListJson()
        lineMap = lineMap()
    }

    @Test
    fun `wrong answer, took the wrong branch`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["7"],
            stationMap["Maison Blanche"],
            stationMap["Villejuif - Paul Vaillant-Couturier"],
            stationMap["Mairie d’Ivry"],
            "wrong-branch",
        )
    }

    @Test
    fun `success, can go 'backwards' on a semi-loop by taking the loop around`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["7bis"],
            stationMap["Jaurès"],
            stationMap["Botzaris"],
            stationMap["Louis Blanc"],
        )
    }

    @Test
    fun `success, went the right way on a one-way semi-loop`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["7bis"],
            stationMap["Place des Fêtes"],
            stationMap["Botzaris"],
            stationMap["Louis Blanc"],

            )
    }

    @Test
    fun `wrong answer, went the WRNOG way on a one-way semi-loop`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["7bis"],
            stationMap["Danube"],
            stationMap["Place des Fêtes"],
            stationMap["Louis Blanc"],
            "wrong-way-one-way",
        )
    }

    @Test
    fun `success, went the right way on a one-way spur`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["10"],
            stationMap["Boulogne - Jean Jaurès"],
            stationMap["Mirabeau"],
            stationMap["Gare d’Austerlitz"],
        )
    }

    @Test
    fun `wrong answer, went the WRONG way on a one-way spur`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["10"],
            stationMap["Boulogne - Jean Jaurès"],
            stationMap["Église d’Auteuil"],
            stationMap["Gare d’Austerlitz"],
            "wrong-way-one-way",
        )
    }

    @Test
    fun `success, connected to seemingly 'wrong' line via a tunnel`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["12"],
            stationMap["Opéra"],
            stationMap["Madeleine"],
            stationMap["Mairie d’Issy"],
            expectedOriginId = stationMap["Saint-Lazare"],
        )
    }

    @Test
    fun `wrong answer, stations connect via tunnel but incorrect direction`() {
        runVerificationTestWrongAnswer(
            mvc,
            lineMap["2"],
            stationMap["Opéra"],
            stationMap["Madeleine"],
            stationMap["Nation"],
            "no-tunnel-to-line",
        )
    }

    @Test
    fun `success, reached step destination using only the tunnel system`() {
        runVerificationTestSuccess(
            mvc,
            lineMap["9"],
            stationMap["Opéra"],
            stationMap["Havre - Caumartin"],
            stationMap["Pont de Sèvres"],
            expectedOriginId = stationMap["Havre - Caumartin"]
        )
    }
}
