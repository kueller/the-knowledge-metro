package com.glitch.knowledge.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity


internal object TerminusTable : IntIdTable("terminus") {
    val lineId = reference("line_id", LineTable.id)
    val stationId = reference("station_id", StationTable.id)
}


/**
 * Map of [Line] to any [Station] that is a terminus for the line.
 *
 * Lines that are [com.glitch.knowledge.model.constant.LineType] LOOP should have at least one station
 * randomly chosen as a terminus. This is for compatibility and will not affect
 * the code.
 *
 * @property id Database id.
 * @property line [Line] that this station terminates.
 * @property terminus The [Station] object for this terminus.
 */
internal class TerminusDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : ImmutableEntityClass<Int, TerminusDAO>(TerminusTable)

    val line by LineDAO.referencedOn(TerminusTable.lineId)
    val terminus by StationDAO.referencedOn(TerminusTable.stationId)
}