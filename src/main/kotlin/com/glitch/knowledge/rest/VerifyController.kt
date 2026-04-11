package com.glitch.knowledge.rest

import com.glitch.knowledge.model.Line
import com.glitch.knowledge.model.Station
import com.glitch.knowledge.model.StationToLine
import com.glitch.knowledge.model.constant.LineType
import com.glitch.knowledge.model.constant.StationDirection
import com.glitch.knowledge.rest.schema.VerifyResponse
import com.glitch.knowledge.util.checkAndUpdateLanguageFromHeader
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.math.abs


@RestController
class VerifyController {
    private val WRONG: Int = -1
    private val CORRECT: Int = 0

    private val INVALID_REQUEST: ResponseEntity<VerifyResponse> = ResponseEntity.badRequest().build()

    private fun answer(status: Int, message: String?, originId: Int? = null): ResponseEntity<VerifyResponse> {
        val response = VerifyResponse(status, message, originId)
        return ResponseEntity.ok(response)
    }

    /**
     * From a starting station, the player chooses a line, a direction of that line,
     * and a destination. The method verifies that this ia valid journey in the
     * transport system.
     * 
     * @param llineId     ID of the [Line] the player chooses to take from the origin
     *                    [Station].
     * @param originId   ID of the [Station] the player starts the leg from.
     * @param destId     ID of the [Station] the player wants to arrive at.
     * @param terminusId ID of the last [Station] on the line in the direction the
     *                    player chooses to travel. Should be a valid [com.glitch.knowledge.modelOld.Terminus].
     * 
     * @return `400` if the data is invalid (will not trigger a game failure), `200` if
     *         the request is ok, in the form of `{"status": int, "message": string, "originId": int|null}`.
     *
     *         If the answer is incorrect, status is `-1` and message is the failure
     *         message key. The frontend maps this key to the full failure message
     *         in the player's chosen langauge. The originId will be null.
     *         If the answer is correct, status is `0`, message is `null`, and `originId`
     *         is the ID of the origin station. If the player tunneled to another
     *         station, the `originId` of the new station is returned, and it will be
     *         different from the `originId` passed in the URL. This is so the frontend
     *         can detect that the user has transferred via a tunnel network and note the
     *         new starting station.
     */
    @GetMapping("/maison/verify/line/{llineId}/origin/{originId}/destination/{destId}/direction/{terminusId}")
    fun verify(
        @PathVariable llineId: Int,
        @PathVariable originId: Int,
        @PathVariable destId: Int,
        @PathVariable terminusId: Int,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<VerifyResponse> {
        checkAndUpdateLanguageFromHeader(request, response)

        if (originId == destId) {
            return INVALID_REQUEST
        }

        /* Database fetch and check */
        var origin: Station = Station.get(originId) ?: return INVALID_REQUEST

        val line: Line = Line.get(llineId) ?: return INVALID_REQUEST
        val destination: Station = Station.get(destId) ?: return INVALID_REQUEST
        val terminus: Station = line.getTerminus(terminusId) ?: return INVALID_REQUEST

        val terminusLineInfo: StationToLine = terminus.lineConnectionInfoFromLine(llineId) ?: return INVALID_REQUEST

        /* Actual game logic checks. Return 200 and -1 on failure. */

        /*
         * Check if origin is on the line, or if a tunnel can connect. If there is a
         * valid tunnel, switch to the tunneled station as origin. If new origin is the
         * destination, we're done.
         */
        var originLineInfo: StationToLine? = origin.lineConnectionInfoFromLine(llineId)
        if (originLineInfo == null) {
            if (!origin.isOnTunnel()) {
                return answer(WRONG, "origin-not-on-line")
            }

            val tunnelStationData: List<Station> = origin.getTunnelStationsConnectingToLine(llineId)
            if (tunnelStationData.isEmpty()) {
                return answer(WRONG, "no-tunnel-to-line")
            }

            tunnelStationData.find { it.id == destId }?.run { 
                return answer(CORRECT, null, destId)
            }

            // Safety check but this should not happen
            origin = tunnelStationData.first()
            originLineInfo = origin.lineConnectionInfoFromLine(llineId) ?: return INVALID_REQUEST
        }

        val destinationLineInfo: StationToLine =
            destination.lineConnectionInfoFromLine(llineId) ?: return answer(WRONG, "dest-not-on-line")

        val originDirection: StationDirection = originLineInfo.direction
        val destDirection: StationDirection = destinationLineInfo.direction
        if (line.type == LineType.STANDARD) {
            if (destinationLineInfo.isBranch()) {
                val brandId: Int? = destinationLineInfo.branchId
                if (brandId != terminusId) {
                    return answer(WRONG, "wrong-branch")
                }
            }

            val positionDifferenceToEnd: Int =
                abs(terminusLineInfo.position - originLineInfo.position) - abs(terminusLineInfo.position - destinationLineInfo.position)

            if (positionDifferenceToEnd < 0) {
                return answer(WRONG, "wrong-way")
            }

            if (originDirection != StationDirection.BIDIRECTIONAL || destDirection != StationDirection.BIDIRECTIONAL) {
                val oneWayDirection: StationDirection =
                    if (originDirection != StationDirection.BIDIRECTIONAL) originDirection else destDirection

                if ((oneWayDirection == StationDirection.INCREASING) && (terminusLineInfo.position == 0)) {
                    return answer(WRONG, "wrong-way-one-way")
                }

                if ((oneWayDirection == StationDirection.DECREASING) && (terminusLineInfo.position > 0)) {
                    return answer(WRONG, "wrong-way-one-way")
                }
            }
        } else if (line.type == LineType.SEMI_LOOP) {
            if (originDirection != StationDirection.BIDIRECTIONAL && destDirection != StationDirection.BIDIRECTIONAL) {
                val positionDifference: Int = destinationLineInfo.position - originLineInfo.position

                if ((positionDifference < 0) && (originDirection == StationDirection.INCREASING)) {
                    return answer(WRONG, "wrong-way-one-way")
                }

                if ((positionDifference > 0) && (originDirection == StationDirection.DECREASING)) {
                    return answer(WRONG, "wrong-way-one-way")
                }
            }
        }
        return answer(CORRECT, null, origin.id)
    }
}