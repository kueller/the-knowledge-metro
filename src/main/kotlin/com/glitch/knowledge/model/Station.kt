package com.glitch.knowledge.model

import jakarta.persistence.*

/**
 * @property id Database ID.
 * @property name Name of the metro station.
 * @property tunnel The [TunnelNetwork] if the station is part of one.
 * @property connectingLines List of [Line] serviced by this station.
 * @property lineConnectionInfo Map of the station to a serviced [Line].
 */
@Entity(name = "station")
class Station(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 128, nullable = false)
    var name: String = "",

    @ManyToOne
    @JoinColumn(name = "tunnel_id", nullable = true)
    var tunnel: TunnelNetwork? = null,

    @ManyToMany
    @JoinTable(
        name = "station_line",
        joinColumns = [JoinColumn(name = "station_id")],
        inverseJoinColumns = [JoinColumn(name = "line_id")]
    )
    var connectingLines: Set<Line?> = setOf(),

    @OneToMany(mappedBy = "station")
    var lineConnectionInfo: Set<StationToLine?> = setOf(),
) {
    /**
     * Get the specific mapped data of the station as it relates to the given line.
     *
     * @param line_id ID of [Line] serviced by station.
     * @return [StationToLine] object if line connects to station, otherwise
     *         null
     */
    fun lineConnectionInfoFromLine(line_id: Int): StationToLine? {
        return this.lineConnectionInfo.find { it?.line?.id?.compareTo(line_id) == 0 }
    }

    /**
     * Get the other stations connected to the tunnel network that service the given
     * line.
     *
     * @param line_id ID of [Line] to filter for.
     * @return List of valid [Station]. Empty if there are no matches or if
     *         this station is not connected to a tunnel network.
     */
    fun getTunnelStationsConnectingToLine(line_id: Int): List<Station?> {
        val tunnelConnections: MutableList<Station?> = mutableListOf()
        if (this.tunnel == null) {
            return tunnelConnections
        }

        val tunnelStations = this.tunnel?.connectingStations ?: setOf()
        for (tunnelStationData: Station? in tunnelStations) {
            if (tunnelStationData?.id == this.id) {
                continue
            }

            val line: Line? = tunnelStationData?.connectingLines?.find { it?.id?.compareTo(line_id) == 0 }
            line?.let { tunnelConnections.add(tunnelStationData) }
        }

        return tunnelConnections
    }
}
