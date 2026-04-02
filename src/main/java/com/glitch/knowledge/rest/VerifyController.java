package com.glitch.knowledge.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.glitch.knowledge.model.Line;
import com.glitch.knowledge.model.Station;
import com.glitch.knowledge.model.StationToLine;
import com.glitch.knowledge.model.constant.LineType;
import com.glitch.knowledge.model.constant.StationDirection;
import com.glitch.knowledge.rest.model.VerifyResponse;
import com.glitch.knowledge.service.LineService;
import com.glitch.knowledge.service.StationService;
import com.glitch.knowledge.util.LanguageUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class VerifyController {

    @Autowired
    private LineService lineService;

    @Autowired
    private StationService stationService;

    @Value("${application.domain.url}")
    private String appUrl;

    private final int WRONG = -1;
    private final int CORRECT = 0;

    private final ResponseEntity<VerifyResponse> INVALID_REQUEST = ResponseEntity.badRequest().build();

    private ResponseEntity<VerifyResponse> answer(int status, String message) {
        return ResponseEntity.ok(new VerifyResponse(status, message));
    }

    private ResponseEntity<VerifyResponse> answer(int status, String message, int origin_id) {
        return ResponseEntity.ok(new VerifyResponse(status, message, origin_id));
    }

    /**
     * From a starting station, the player chooses a line, a direction of that line,
     * and a destination. The method verifies that this ia valid journey in the
     * transport system.
     * 
     * @param line_id     ID of the line the player chooses to take from the origin
     *                    station.
     * @param origin_id   ID of the station the player starts the leg from.
     * @param dest_id     ID of the station the player wants to arrive at.
     * @param terminus_id ID of the last station on the line in the direction the
     *                    player chooses to travel.
     * 
     * @return 400 if the data is invalid (will not trigger a game failure), 200 if
     *         the request is ok, in the form of {"status": int, "message": string,
     *         "origin_id": int|null}. <br>
     *         If the answer is incorrect, status is -1 and message is the failure
     *         message key. The frontend maps this key to the full failure message
     *         in the player's chosen langauge. The origin_id will be null. <br>
     *         If the answer is correct, status is 0, message is null, and origin_id
     *         is the ID of the origin station. If the player tunneled to another
     *         station, the origin_id of the new station is returned and it will be
     *         different from the origin_id passed in the URL.
     */
    @GetMapping("/maison/verify/line/{line_id}/origin/{origin_id}/destination/{dest_id}/direction/{terminus_id}")
    public ResponseEntity<VerifyResponse> verify(@PathVariable int line_id, @PathVariable int origin_id,
            @PathVariable int dest_id, @PathVariable int terminus_id, HttpServletRequest request,
            HttpServletResponse response) {
        LanguageUtil.checkAndUpdateLanguageFromHeader(request, response);

        Line line;
        Station origin;
        Station destination;
        Station terminus;

        if (origin_id == dest_id)
            return INVALID_REQUEST;

        /* Database fetch and check */
        line = lineService.get(line_id);
        if (line == null)
            return INVALID_REQUEST;

        origin = stationService.getById(origin_id);
        if (origin == null)
            return INVALID_REQUEST;

        destination = stationService.getById(dest_id);
        if (destination == null)
            return INVALID_REQUEST;

        /* Sanity checks. These should be game constants and therefore never wrong. */
        terminus = line.getTerminus(terminus_id);
        if (terminus == null)
            return INVALID_REQUEST;

        StationToLine terminusLineInfo = terminus.getLineConnectionInfo(line_id);
        if (terminusLineInfo == null)
            return INVALID_REQUEST;

        /* Actual game logic checks. Return 200 and -1 on failure. */

        /*
         * Check if origin is on the line, or if a tunnel can connect. If there is a
         * valid tunnel, switch to the tunneled station as origin. If new origin is the
         * destination, we're done.
         */
        StationToLine originLineInfo = origin.getLineConnectionInfo(line_id);
        if (originLineInfo == null) {
            if (origin.getTunnelNetwork() == null)
                return answer(WRONG, "origin-not-on-line");

            List<Station> tunnelStations = origin.getTunnelStationsConnectingToLine(line_id);
            if (tunnelStations.isEmpty())
                return answer(WRONG, "no-tunnel-to-line");

            for (Station tunneled : tunnelStations) {
                if (tunneled.getId() == dest_id)
                    return answer(CORRECT, null, dest_id);
            }

            origin = tunnelStations.getFirst();
            originLineInfo = origin.getLineConnectionInfo(line_id);

            // Safety check but this should not happen.
            if (originLineInfo == null)
                return INVALID_REQUEST;
        }

        StationToLine destinationLineInfo = destination.getLineConnectionInfo(line_id);
        if (destinationLineInfo == null)
            return answer(WRONG, "dest-not-on-line");

        StationDirection originDirection = originLineInfo.getDirection();
        StationDirection destDirection = destinationLineInfo.getDirection();
        if (line.getType() == LineType.STANDARD) {
            if (destinationLineInfo.isBranch()) {
                Integer branch_id = destinationLineInfo.getBranchId();
                if (branch_id == null || branch_id != terminus_id)
                    return answer(WRONG, "wrong-branch");
            }

            int positionDifferenceToEnd = Math.abs(terminusLineInfo.getPosition() - originLineInfo.getPosition())
                    - Math.abs(terminusLineInfo.getPosition() - destinationLineInfo.getPosition());

            if (positionDifferenceToEnd < 0)
                return answer(WRONG, "wrong-way");

            if (originDirection != StationDirection.BIDIRECTIONAL || destDirection != StationDirection.BIDIRECTIONAL) {
                StationDirection oneWayDirection = originDirection != StationDirection.BIDIRECTIONAL ? originDirection
                        : destDirection;

                if ((oneWayDirection == StationDirection.INCREASING) && (terminusLineInfo.getPosition() == 0))
                    return answer(WRONG, "wrong-way-one-way");

                if ((oneWayDirection == StationDirection.DECREASING) && (terminusLineInfo.getPosition() > 0))
                    return answer(WRONG, "wrong-way-one-way");
            }
        } else if (line.getType() == LineType.SEMI_LOOP) {
            if (originDirection != StationDirection.BIDIRECTIONAL && destDirection != StationDirection.BIDIRECTIONAL) {
                int positionDifference = destinationLineInfo.getPosition() - originLineInfo.getPosition();

                if ((positionDifference < 0) && (originDirection == StationDirection.INCREASING))
                    return answer(WRONG, "wrong-way-one-way");

                if ((positionDifference > 0) && (originDirection == StationDirection.DECREASING))
                    return answer(WRONG, "wrong-way-one-way");
            }
        }
        return answer(CORRECT, null, origin.getId());
    }
}
