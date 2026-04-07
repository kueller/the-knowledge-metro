package com.glitch.knowledge.model

import jakarta.persistence.*

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
@Entity(name = "terminus")
class Terminus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    var line: Line? = null,

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    var terminus: Station? = null,
)