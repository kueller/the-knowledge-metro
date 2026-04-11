package com.glitch.knowledge.model

import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.Random
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


internal object StationTable : IntIdTable("station") {
    val name = varchar("name", 128)
    val tunnelId = optReference("tunnel_id", TunnelNetworkTable.id)
}


internal class StationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : ImmutableEntityClass<Int, StationDAO>(StationTable)

    val name: String by StationTable.name
    val tunnelId by StationTable.tunnelId

    //val connectingLines by LineDAO.via(StationToLineTable.stationId, StationToLineTable.lineId)
    val lineConnectionInfo by StationToLineDAO.referrersOn(StationToLineTable.stationId)
}


/**
 * @property id Database ID.
 * @property name Name of the metro station.
 */
class Station(
    val id: Int,
    val name: String,
    private val tunnelId: EntityID<Int>?,
    private val connectingLinesInfo: List<StationToLine>
) {
    companion object {
        internal fun fromDAO(dao: StationDAO): Station {
            return Station(
                dao.id.value,
                dao.name,
                dao.tunnelId,
                transaction {
                    dao.lineConnectionInfo.with(StationDAO::lineConnectionInfo).map { StationToLine.fromDAO(it) }
                })
        }

        fun get(id: Int): Station? {
            return transaction {
                StationDAO.findById(id)?.let { fromDAO(it) }
            }
        }

        fun getTwoRandom(): List<Station> {
            return transaction {
                StationDAO.wrapRows(
                    StationTable.selectAll()
                        .orderBy(Random())
                        .limit(2)
                ).map { Station.fromDAO(it) }
            }

        }
    }

    fun isOnTunnel(): Boolean {
        return this.tunnelId != null
    }

    /**
     * Get the specific mapped data of the station as it relates to the given line.
     *
     * @param lineId ID of [Line] serviced by station.
     * @return [StationToLine] object if line connects to station, otherwise
     *         null
     */
    fun lineConnectionInfoFromLine(lineId: Int): StationToLine? {
        return this.connectingLinesInfo.find { it.lineId == lineId }
    }

    /**
     * Get the other stations connected to the tunnel network that service the given
     * line.
     *
     * @param lineId ID of [Line] to filter for.
     * @return List of valid [Station]. Empty if there are no matches or if
     *         this station is not connected to a tunnel network.
     */
    fun getTunnelStationsConnectingToLine(lineId: Int): List<Station> {
        val tunnelConnections: MutableList<Station> = mutableListOf()

        this.tunnelId ?: return tunnelConnections

        val stationId = this.id
        transaction {
            StationDAO.wrapRows(
                StationTable.join(
                    StationToLineTable,
                    JoinType.INNER,
                    onColumn = StationTable.id,
                    otherColumn = StationToLineTable.stationId
                ).select(StationTable.columns).andWhere {
                    StationTable.id neq stationId
                }.andWhere {
                    StationToLineTable.lineId eq lineId
                }.andWhere {
                    StationTable.tunnelId eq tunnelId
                }
            ).map { tunnelConnections.add(fromDAO(it)) }
        }

        return tunnelConnections
    }
}