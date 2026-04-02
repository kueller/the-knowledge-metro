package com.glitch.knowledge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class KnowledgeUniqueCasesTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void testStepVerificationWrongBranchLine() throws Exception {
        // line 7, Maison Blanche -> Villejuif-Paul Vailiant, direction Ivry[wrong]
        mvc.perform(get("/maison/verify/line/7/origin/20/destination/23/direction/29"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("wrong-branch"));
    }

    @Test
    void testStepVerificationSuccessNoLoopDirection() throws Exception {
        // line 7bis, Jaurès -> Botzaris, direction Louis Blanc [irrelevant]
        // This path goes away from Louis Blanc, but should not count since there is a
        // loop.
        mvc.perform(get("/maison/verify/line/5/origin/13/destination/16/direction/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.origin_id").value(13));
    }

    @Test
    void testStepVerificationSuccessLoopOneWay() throws Exception {
        // line 7bis, Places des Fêtes -> Botzaris, direction Louis Blanc [irrelevant]
        // Place des Fêtes is INCREASING and Botzaris has a lower position.
        // But in a loop rider could wait until loop returns to Botzaris.
        mvc.perform(get("/maison/verify/line/5/origin/17/destination/16/direction/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.origin_id").value(17));
    }

    @Test
    void testStepVerificationSemiLoopOneWay() throws Exception {
        // line 7bis, Danube -> Places des Fêtes, direction Louis Blanc [irrelevant]
        // Wrong way. Needs to go to a BIDIRECTIONAL station first to restart the loop.
        mvc.perform(get("/maison/verify/line/5/origin/19/destination/17/direction/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("wrong-way-one-way"));
    }

    @Test
    void testStepVerificationSuccessOneWay() throws Exception {
        // line 10, Boulogne -> Mirabeau, direction Javel
        mvc.perform(get("/maison/verify/line/10/origin/34/destination/37/direction/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.origin_id").value(34));
    }

    @Test
    void testStepVerificationOneWayWrongDirection() throws Exception {
        // line 10: Boulogne -> Église d'Auteuil, direction Javel
        mvc.perform(get("/maison/verify/line/10/origin/34/destination/31/direction/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("wrong-way-one-way"));
    }

    @Test
    void testStepVerificationSuccessTunnel() throws Exception {
        // line 12: Opéra -> Madeleine, direction Madeleine
        // Rider can connect to 12 via tunnels through Havre Caumartin to Saint-Lazare
        // Return value is station ID for Saint-Lazare
        mvc.perform(get("/maison/verify/line/12/origin/38/destination/43/direction/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.origin_id").value(42));
    }

    @Test
    void testStepVerificationWrongLineWithTunnel() throws Exception {
        // line 2: Opéra -> Madeleine, direction Nation
        // Opéra is connected to a tunnel network but not to line 2
        mvc.perform(get("/maison/verify/line/2/origin/38/destination/43/direction/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("no-tunnel-to-line"));
    }

    @Test
    void testStepVerificationSuccessTunnelToDestination() throws Exception {
        // line 9: Opéra -> Havre Caumartin, direction Saint-Augustin
        // User tunnels from Opéra and arrives at Havre Caumartin
        mvc.perform(get("/maison/verify/line/9/origin/38/destination/40/direction/41"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.origin_id").value(40));
    }
}
