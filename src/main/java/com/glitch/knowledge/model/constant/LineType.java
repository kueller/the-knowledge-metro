package com.glitch.knowledge.model.constant;

/**
 * STANDARD: Line with start and end, including branches.
 * <br>
 * SEMI_LOOP: Line has a start and turns into a loop.
 * <br>
 * LOOP: Perfect loop. No start nor end. Any station directions are ignored.
 * 
 * @see StationDirection
 */
public enum LineType {
    STANDARD,
    SEMI_LOOP,
    LOOP,
}
