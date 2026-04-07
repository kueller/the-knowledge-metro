package com.glitch.knowledge.model

import com.glitch.knowledge.model.constant.StationDirection
import jakarta.persistence.*

/**
 * Map of [Station] to [Line].
 *
 * There should be one entry for every line a station might service. At least
 * one entry per station.
 *
 * Also includes specific information of the station for any associated line.
 * @property id Database ID.
 * @property station [Station] being mapped.
 * @property line [Line] being mapped.
 * @property position Arbitrarily chosen [Station] weight on the [Line]. Starting station has position 0 and increases
 * to the end.
 * @property branchId [Station.id] of the [Terminus] of the [Line], if the mapped station is on a branch of that line.
 * @property direction [StationDirection] of the mapped [Station] on the associated [Line].
 */
@Entity(name = "station_line")
class StationToLine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    var station: Station? = null,

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    var line: Line? = null,

    @Column(nullable = false)
    var position: Int? = null,

    @Column(name = "branch_id", nullable = true)
    var branchId: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var direction: StationDirection? = StationDirection.BIDIRECTIONAL,
) {
    /**
     * @return If the station on this line is on a branch of the line
     */
    fun isBranch(): Boolean {
        return this.branchId != null
    }
}
