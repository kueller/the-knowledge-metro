package com.glitch.knowledge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Map of {@link Line} to any {@link Station} that is a terminus for the line.
 * <br>
 * Lines that are {@link LineType} LOOP should have at least one station
 * randomly chosen as a terminus. This is for compatibility and will not affect
 * the code.
 */
@Entity(name = "terminus")
public class Terminus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private Line line;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station terminus;
}
