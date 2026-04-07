package com.glitch.knowledge.model.constant

/**
 * BIDIRECTIONAL: Station has platforms for both directions
 *
 * INCREASING: One way station goes towards higher number terminus
 *
 * DECREASING: One way station goes towards 0 value terminus
 * 
 * @see JLineType
 */
enum class StationDirection {
    BIDIRECTIONAL,
    INCREASING,
    DECREASING,
}