package com.glitch.knowledge.model;

import java.util.Set;

import com.glitch.knowledge.model.constant.LineType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LineType type;

    @ManyToMany
    @JoinTable(
        name = "terminus", 
        joinColumns = @JoinColumn(name = "line_id"), 
        inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    private Set<Station> termini;

    /**
     * @return database ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return {@link LineType} STANDARD, SEMI_LOOP, or LOOP
     */
    public LineType getType() {
        return this.type;
    }

    /**
     * Get the terminus Station object from input station ID.
     * @param station_id Station ID of terminus station of line.
     * @return {@link Station} object if ID is a valid terminus of line, otherwise null
     */
    public Station getTerminus(int station_id) {
        for (Station terminus : this.termini) {
            if (terminus.getId() == station_id)
                return terminus;
        }
        return null;
    }
}
