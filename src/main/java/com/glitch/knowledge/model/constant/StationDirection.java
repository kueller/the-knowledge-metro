package com.glitch.knowledge.model.constant;

/**
 * BIDIRECTIONAL: Station has platforms for both directions
 * <br>
 * INCREASING: One way station goes towards higher number terminus
 * <br>
 * DECREASING: One way station goes towards 0 value terminus
 * 
 * @see LineType
 */
public enum StationDirection {
    BIDIRECTIONAL,
    INCREASING,
    DECREASING
}
