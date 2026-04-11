package com.glitch.knowledge.model

import com.glitch.knowledge.model.constant.StationDirection
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity


internal object StationToLineTable : IntIdTable("station_line") {
    val stationId = reference("station_id", StationTable.id)
    val lineId = reference("line_id", LineTable.id)
    val position = integer("position")
    val branchId = integer("branch_id").nullable()
    val direction = enumerationByName("direction", 16, StationDirection::class)
}


internal class StationToLineDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : ImmutableEntityClass<Int, StationToLineDAO>(StationToLineTable)

    val stationId by StationToLineTable.stationId
    val lineId by StationToLineTable.lineId

    val station by StationDAO referencedOn StationToLineTable.stationId
    val line by LineDAO referencedOn StationToLineTable.lineId

    val position: Int by StationToLineTable.position
    val branchId: Int? by StationToLineTable.branchId
    val direction: StationDirection by StationToLineTable.direction
}


/**
 * Map of [Station] to [Line].
 *
 * There should be one entry for every line a station might service. At least
 * one entry per station.
 *
 * Also includes specific information of the station for any associated line.
 * @property stationId ID of the [Station] being mapped.
 * @property lineId ID of the [Line] being mapped.
 * @property position Arbitrarily chosen [Station] weight on the [Line]. Starting station has position 0 and increases
 * to the end.
 * @property branchId [Station.id] of the [Terminus] of the [Line], if the mapped station is on a branch of that line.
 * @property direction [StationDirection] of the mapped [Station] on the associated [Line].
 */
class StationToLine(
    val stationId: Int,
    val lineId: Int,
    val position: Int,
    val branchId: Int?,
    val direction: StationDirection
) {
    companion object {
        internal fun fromDAO(dao: StationToLineDAO): StationToLine {
            return StationToLine(
                dao.stationId.value, 
                dao.lineId.value, 
                dao.position, 
                dao.branchId, 
                dao.direction
            )
        }
    }

    /**
     * If the station on this line is on a branch of the line
     */
    fun isBranch(): Boolean {
        return this.branchId != null
    }
}
