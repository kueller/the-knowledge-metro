package com.glitch.knowledge.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Represents tunnels (or bridges, etc) that connect one or more different
 * stations. A rider would be able to use this network to walk to a different
 * station without ever leaving th ticketed area. Any station on the network
 * links using {@link Station}.tunnel_id.
 */
@Entity(name = "tunnel_network")
public class TunnetNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tunnel")
    private Set<Station> connectingStations;

    public Set<Station> getConnectingStations() {
        return this.connectingStations;
    }

}
