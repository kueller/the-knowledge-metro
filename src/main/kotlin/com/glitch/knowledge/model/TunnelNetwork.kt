package com.glitch.knowledge.model

import jakarta.persistence.*

/**
 * Represents tunnels (or bridges, etc) that connect one or more different
 * stations. A rider would be able to use this network to walk to a different
 * station without ever leaving th ticketed area. Any station on the network
 * links using [Station.tunnel].
 *
 * @property id Database ID.
 * @property name Internal name of the network. This is purely for tagging and is not intended to ever be shown
 * publicly.
 * @property connectingStations List of [Station] that connects to this tunnel network. All connected stations
 * can have valid transfers between them, even if the stations have different names.
 */
@Entity(name = "tunnel_network")
class TunnelNetwork(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 100, nullable = false)
    var name: String? = null,

    @OneToMany(mappedBy = "tunnel")
    var connectingStations: Set<Station?> = setOf(),
)

