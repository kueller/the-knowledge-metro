package com.glitch.knowledge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class KnowledgeInvalidRequestTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void testStepVerificationSameOriginDestination() throws Exception {
		// line 2, Avron -> Avron, direction Porte Dauphine
		mvc.perform(get("/maison/verify/line/2/origin/14/destination/14/direction/223"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidLine() throws Exception {
		// line [invalid], Nation -> Avron, direction Porte Dauphine
		mvc.perform(get("/maison/verify/line/50/origin/189/destination/14/direction/223"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidOrigin() throws Exception {
		// line 1, [invalid] -> Bastille, direction La Defense
		mvc.perform(get("/maison/verify/line/1/origin/500/destination/20/direction/135"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidDestination() throws Exception {
		// line 1, Gare De Lyon -> [Invalid], direction La Defense
		mvc.perform(get("/maison/verify/line/1/origin/111/destination/500/direction/135"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidDirection() throws Exception {
		// line 1, Gare De Lyon -> Bastille, direction [Invalid]
		mvc.perform(get("/maison/verify/line/1/origin/111/destination/20/direction/500"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationDirectionNotConnectedToLine() throws Exception {
		// line 1, Gare De Lyon -> Bastille, direction Porte Dauphine[wrong]
		mvc.perform(get("/maison/verify/line/1/origin/111/destination/20/direction/223"))
				.andExpect(status().is(400));
	}

}
