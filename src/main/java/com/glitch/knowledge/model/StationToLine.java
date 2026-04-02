package com.glitch.knowledge.model;

import com.glitch.knowledge.model.constant.StationDirection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Map of {@link Station} to {@link Line}.
 * <br>
 * There should be one entry for every line a station might service. At least one entry per station.
 * <br>
 * Also includes specific information of the station for any associated line.
 */
@Entity(name = "station_line")
public class StationToLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private Line line;

    @Column(nullable = false)
    private int position;

    @Column(nullable = true)
    private Integer branch_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StationDirection direction;

    /**
     * @return Mapped {@link Line} object
     */
    public Line getLine() {
        return this.line;
    }

    /**
     * @return Arbitrarily chosen station weight on the line. Starting station is 0 and increases to the end.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * @return If the station on this line is on a branch of the line
     */
    public boolean isBranch() {
        return this.branch_id != null;
    }

    /**
     * @return {@link Station} ID of the branch (terminus of the branch). Is null if the station is not on a branch.
     */
    public Integer getBranchId() {
        return this.branch_id;
    }

    /**
     * @return {@link StationDirection} of the station on the associated line
     */
    public StationDirection getDirection() {
        return this.direction;
    }
}
