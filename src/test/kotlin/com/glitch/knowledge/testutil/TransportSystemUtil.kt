package com.glitch.knowledge.testutil

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File


@Serializable
data class StationEntry(val id: Int, val name: String, val tags: List<String>)


fun readStationListJson(): Map<String, Int> {
    val lines = File("./src/main/resources/static/js/const/stations.js")
        .readLines(Charsets.UTF_8)

    (lines as ArrayList<String>)[0] = "["
    lines[lines.size - 1] = "]"

    val jsonstr = lines.joinToString(separator = " ")
    val data: List<StationEntry> = Json.decodeFromString(jsonstr)

    return data.associate { it.name to it.id }
}


fun lineMap(): Map<String, Int> {
    return mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "3bis" to 21,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "7bis" to 22,
        "8" to 8,
        "9" to 9,
        "10" to 10,
        "11" to 11,
        "12" to 12,
        "13" to 13,
        "14" to 14,
    )
}