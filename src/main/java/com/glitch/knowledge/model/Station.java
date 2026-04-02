package com.glitch.knowledge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 128, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "tunnel_id", nullable = true)
    private TunnetNetwork tunnel;

    @ManyToMany()
    @JoinTable(name = "station_line", joinColumns = @JoinColumn(name = "station_id"), inverseJoinColumns = @JoinColumn(name = "line_id"))
    private Set<Line> connectingLines;

    @OneToMany(mappedBy = "station")
    private Set<StationToLine> lineConnectionInfo;

    /**
     * @return database ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return Metro station name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The {@link TunnelNetwork} if the station is part of one, otherwise
     *         null
     */
    public TunnetNetwork getTunnelNetwork() {
        return this.tunnel;
    }

    /**
     * @return {@link Line}s serviced by this station.
     */
    public Set<Line> getConnectingLines() {
        return this.connectingLines;
    }

    /**
     * Get the specific mapped data of the station as it relates to the given line.
     * 
     * @param line_id ID of {@link Line} serviced by station.
     * @return {@link StationToLine} object if line connects to station, otherwise
     *         null
     */
    public StationToLine getLineConnectionInfo(int line_id) {
        for (StationToLine connectionInfo : this.lineConnectionInfo) {
            if (connectionInfo.getLine().getId() == line_id)
                return connectionInfo;
        }
        return null;
    }

    /**
     * Get the other stations connected to the tunnel network that service the given
     * line.
     * 
     * @param line_id ID of {@link Line} to filter for.
     * @return List of valid {@link Station}s. Empty if there are no matches or if
     *         this station is not connected to a tunnel network.
     */
    public List<Station> getTunnelStationsConnectingToLine(int line_id) {
        List<Station> tunnelConnections = new ArrayList<Station>();

        if (this.tunnel == null)
            return tunnelConnections;

        for (Station tunnelStation : this.tunnel.getConnectingStations()) {
            if (tunnelStation.getId() == this.id)
                continue;

            for (Line line : tunnelStation.getConnectingLines()) {
                if (line.getId() == line_id) {
                    tunnelConnections.add(tunnelStation);
                    break;
                }
            }
        }

        return tunnelConnections;
    }
}
