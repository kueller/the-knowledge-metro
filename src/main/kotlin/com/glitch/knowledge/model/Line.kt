package com.glitch.knowledge.model

import com.glitch.knowledge.model.constant.LineType
import jakarta.persistence.*

/**
 * @property id Database ID.
 * @property name Name of the line.
 * @property type [LineType] either STANDARD, SEMI_LOOP, or LOOP.
 * @property termini All the terminus stations of the line.
 */
@Entity(name = "line")
class Line(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 16, nullable = false)
    var name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: LineType? = LineType.STANDARD,

    @ManyToMany
    @JoinTable(
        name = "terminus",
        joinColumns = [JoinColumn(name = "line_id")],
        inverseJoinColumns = [JoinColumn(name = "station_id")]
    )
    val termini: Set<Station?> = setOf(),
) {
    /**
     * Get the terminus Station object from input station ID.
     * @param station_id Station ID of terminus station of line.
     * @return [Station] object if ID is a valid terminus of line, otherwise null
     */
    fun getTerminus(station_id: Int): Station? {
        return this.termini.find { it?.id?.compareTo(station_id) == 0 }
    }
}