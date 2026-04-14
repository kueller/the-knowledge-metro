package com.glitch.knowledge

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

fun runVerificationTestSuccess(
    mvc: MockMvc,
    lineId: Int?,
    originId: Int?,
    destinationId: Int?,
    directionId: Int?,
    expectedOriginId: Int? = null
) {
    val line = lineId ?: throw IllegalArgumentException("No value for line id")
    val origin = originId ?: throw IllegalArgumentException("No value for origin id")
    val destination = destinationId ?: throw IllegalArgumentException("No value for destination id")
    val direction = directionId ?: throw IllegalArgumentException("No value for direction id")
    val expected: Int = expectedOriginId ?: origin

    mvc.get("/maison/verify/line/$line/origin/$origin/destination/$destination/direction/$direction")
        .andExpect {
            status { isOk() }
            jsonPath("$.status") { value(0) }
            jsonPath("$.message") { isEmpty() }
            jsonPath("$.origin_id") { value(expected) }
        }
}


fun runVerificationTestWrongAnswer(
    mvc: MockMvc,
    lineId: Int?,
    originId: Int?,
    destinationId: Int?,
    directionId: Int?,
    expectedMessage: String,
) {
    val line = lineId ?: throw IllegalArgumentException("No value for line id")
    val origin = originId ?: throw IllegalArgumentException("No value for origin id")
    val destination = destinationId ?: throw IllegalArgumentException("No value for destination id")
    val direction = directionId ?: throw IllegalArgumentException("No value for direction id")

    mvc.get("/maison/verify/line/$line/origin/$origin/destination/$destination/direction/$direction")
        .andExpect {
            status { isOk() }
            jsonPath("$.status") { value(-1) }
            jsonPath("$.message") { value(expectedMessage) }
            jsonPath("$.origin_id") { isEmpty() }
        }
}


fun runVerificationTestInvalid(
    mvc: MockMvc,
    lineId: Int?,
    originId: Int?,
    destinationId: Int?,
    directionId: Int?,
) {
    val line = lineId ?: throw IllegalArgumentException("No value for line id")
    val origin = originId ?: throw IllegalArgumentException("No value for origin id")
    val destination = destinationId ?: throw IllegalArgumentException("No value for destination id")
    val direction = directionId ?: throw IllegalArgumentException("No value for direction id")

    mvc.get("/maison/verify/line/$line/origin/$origin/destination/$destination/direction/$direction")
        .andExpect {
            status { isEqualTo(400) }
        }
}