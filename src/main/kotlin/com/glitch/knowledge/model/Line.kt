package com.glitch.knowledge.model

import com.glitch.knowledge.model.constant.LineType
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


internal object LineTable : IntIdTable("line") {
    val name = varchar("name", 16)
    val type = enumerationByName("type", 16, LineType::class)
}


internal class LineDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : ImmutableEntityClass<Int, LineDAO>(LineTable)

    val name: String by LineTable.name
    val type: LineType by LineTable.type

    val termini by StationDAO.via(TerminusTable.lineId, TerminusTable.stationId)
}


/**
 * @property id Database ID.
 * @property name Name of the line.
 * @property type [LineType] either STANDARD, SEMI_LOOP, or LOOP.
 * @property termini Terminus [Station] list of the line.
 */
class Line(val id: Int, val name: String, val type: LineType, val termini: List<Station>) {
    companion object {
        internal fun fromDAO(dao: LineDAO): Line {
            return Line(
                dao.id.value,
                dao.name,
                dao.type,
                transaction { 
                    dao.termini.with(TerminusDAO::line)
                }.map { Station.fromDAO(it) }
            )
        }

        fun get(id: Int): Line? {
            return transaction {
                LineDAO.findById(id)
            }?.let { fromDAO(it) }
        }
    }

    /**
     * Get the terminus Station object from input station ID.
     * @param stationId Station ID of terminus station of line.
     * @return [Station] object if ID is a valid terminus of line, otherwise null
     */
    fun getTerminus(stationId: Int): Station? {
        return this.termini.find { it.id == stationId }
    }
}