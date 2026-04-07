package com.glitch.knowledge.model.constant

/**
 * STANDARD: Line with start and end, including branches.
 *
 * SEMI_LOOP: Line has a start and turns into a loop.
 *
 * LOOP: Perfect loop. No start nor end. Any station directions are ignored.
 * 
 * @see [StationDirection]
 */
enum class LineType {
    STANDARD,
    SEMI_LOOP,
    LOOP,
}