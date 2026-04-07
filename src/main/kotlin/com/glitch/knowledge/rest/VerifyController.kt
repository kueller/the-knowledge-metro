package com.glitch.knowledge.rest

import com.glitch.knowledge.model.Line
import com.glitch.knowledge.model.Station
import com.glitch.knowledge.model.StationToLine
import com.glitch.knowledge.model.constant.LineType
import com.glitch.knowledge.model.constant.StationDirection
import com.glitch.knowledge.rest.schema.VerifyResponse
import com.glitch.knowledge.service.LineService
import com.glitch.knowledge.service.StationService
import com.glitch.knowledge.util.checkAndUpdateLanguageFromHeader
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.math.abs

@RestController
class VerifyController(
    private var lineService: LineService,
    private var stationService: StationService,
) {
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
     * @param line_id     ID of the [Line] the player chooses to take from the origin
     *                    [Station].
     * @param origin_id   ID of the [Station] the player starts the leg from.
     * @param dest_id     ID of the [Station] the player wants to arrive at.
     * @param terminus_id ID of the last [Station] on the line in the direction the
     *                    player chooses to travel. Should be a valid [com.glitch.knowledge.model.Terminus].
     * 
     * @return `400` if the data is invalid (will not trigger a game failure), `200` if
     *         the request is ok, in the form of `{"status": int, "message": string, "origin_id": int|null}`.
     *
     *         If the answer is incorrect, status is `-1` and message is the failure
     *         message key. The frontend maps this key to the full failure message
     *         in the player's chosen langauge. The origin_id will be null.
     *         If the answer is correct, status is `0`, message is `null`, and `origin_id`
     *         is the ID of the origin station. If the player tunneled to another
     *         station, the `origin_id` of the new station is returned, and it will be
     *         different from the `origin_id` passed in the URL. This is so the frontend
     *         can detect that the user has transferred via a tunnel network and note the
     *         new starting station.
     */
    @GetMapping("/maison/verify/line/{line_id}/origin/{origin_id}/destination/{dest_id}/direction/{terminus_id}")
    fun verify(
        @PathVariable line_id: Int,
        @PathVariable origin_id: Int,
        @PathVariable dest_id: Int,
        @PathVariable terminus_id: Int,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<VerifyResponse> {
        checkAndUpdateLanguageFromHeader(request, response)

        if (origin_id == dest_id) {
            return INVALID_REQUEST
        }

        /* Database fetch and check */
        var origin: Station = stationService.getById(origin_id) ?: return INVALID_REQUEST

        val line: Line = lineService.get(line_id) ?: return INVALID_REQUEST
        val destination: Station = stationService.getById(dest_id) ?: return INVALID_REQUEST
        val terminus: Station = line.getTerminus(terminus_id) ?: return INVALID_REQUEST

        val terminusLineInfo: StationToLine = terminus.lineConnectionInfoFromLine(line_id) ?: return INVALID_REQUEST

        /* Actual game logic checks. Return 200 and -1 on failure. */

        /*
         * Check if origin is on the line, or if a tunnel can connect. If there is a
         * valid tunnel, switch to the tunneled station as origin. If new origin is the
         * destination, we're done.
         */
        var originLineInfo: StationToLine? = origin.lineConnectionInfoFromLine(line_id)
        if (originLineInfo == null) {
            if (origin.tunnel == null) {
                return answer(WRONG, "origin-not-on-line")
            }

            val tunnelStationData: List<Station?> = origin.getTunnelStationsConnectingToLine(line_id)
            if (tunnelStationData.isEmpty()) {
                return answer(WRONG, "no-tunnel-to-line")
            }

            for (tunneled in tunnelStationData) {
                if (tunneled?.id?.compareTo(dest_id) == 0) {
                    return answer(CORRECT, null, dest_id)
                }
            }

            // Safety check but this should not happen
            origin = tunnelStationData.first() ?: return INVALID_REQUEST;
            originLineInfo = origin.lineConnectionInfoFromLine(line_id) ?: return INVALID_REQUEST;
        }

        val destinationLineInfo: StationToLine =
            destination.lineConnectionInfoFromLine(line_id) ?: return answer(WRONG, "dest-not-on-line")

        val originDirection: StationDirection = originLineInfo.direction ?: return INVALID_REQUEST
        val destDirection: StationDirection = destinationLineInfo.direction ?: return INVALID_REQUEST
        if (line.type == LineType.STANDARD) {
            if (destinationLineInfo.isBranch()) {
                val branch_id: Int? = destinationLineInfo.branchId;
                if (branch_id != terminus_id) {
                    return answer(WRONG, "wrong-branch")
                }
            }

            val positionDifferenceToEnd: Int =
                abs(terminusLineInfo.position!! - originLineInfo.position!!) - abs(terminusLineInfo.position!! - destinationLineInfo.position!!)

            if (positionDifferenceToEnd < 0) {
                return answer(WRONG, "wrong-way")
            }

            if (originDirection != StationDirection.BIDIRECTIONAL || destDirection != StationDirection.BIDIRECTIONAL) {
                val oneWayDirection: StationDirection =
                    if (originDirection != StationDirection.BIDIRECTIONAL) originDirection else destDirection

                if ((oneWayDirection == StationDirection.INCREASING) && (terminusLineInfo.position!! == 0)) {
                    return answer(WRONG, "wrong-way-one-way")
                }

                if ((oneWayDirection == StationDirection.DECREASING) && (terminusLineInfo.position!! > 0)) {
                    return answer(WRONG, "wrong-way-one-way")
                }
            }
        } else if (line.type == LineType.SEMI_LOOP) {
            if (originDirection != StationDirection.BIDIRECTIONAL && destDirection != StationDirection.BIDIRECTIONAL) {
                val positionDifference: Int = destinationLineInfo.position!! - originLineInfo.position!!

                if ((positionDifference < 0) && (originDirection == StationDirection.INCREASING)) {
                    return answer(WRONG, "wrong-way-one-way")
                }

                if ((positionDifference > 0) && (originDirection == StationDirection.DECREASING)) {
                    return answer(WRONG, "wrong-way-one-way")
                }
            }
        }
        return answer(CORRECT, null, origin.id!!.toInt())
    }
}