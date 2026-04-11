package com.glitch.knowledge.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity


internal object TunnelNetworkTable : IntIdTable("tunnel_network") {
    val name = varchar("name", 100)
}


/**
 * Represents tunnels (or bridges, etc) that connect one or more different
 * stations. A rider would be able to use this network to walk to a different
 * station without ever leaving th ticketed area. Any station on the network
 * links using [Station.tunnel].
 *
 * @property id Database ID.
 * @property name Internal name of the network. This is purely for tagging and is not intended to ever be shown
 * publicly.
 * can have valid transfers between them, even if the stations have different names.
 */
internal class TunnelNetworkDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : ImmutableEntityClass<Int, TunnelNetworkDAO>(TunnelNetworkTable)

    val name: String by TunnelNetworkTable.name
}
