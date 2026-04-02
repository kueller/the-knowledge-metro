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
		// line 2, Avron -> Avron, direction Père Lachaise
		mvc.perform(get("/maison/verify/line/2/origin/6/destination/6/direction/9"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidLine() throws Exception {
		// line [invalid], Nation -> Avron, direction Père Lachaise
		mvc.perform(get("/maison/verify/line/20/origin/5/destination/6/direction/9"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidOrigin() throws Exception {
		// line 1, [invalid] -> Bastille, direction Saint-Paul
		mvc.perform(get("/maison/verify/line/1/origin/50/destination/2/direction/1"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidDestination() throws Exception {
		// line 1, Gare De Lyon -> [Invalid], direction Saint-Paul
		mvc.perform(get("/maison/verify/line/1/origin/3/destination/50/direction/1"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationInvalidDirection() throws Exception {
		// line 1, Gare De Lyon -> Bastille, direction [Invalid]
		mvc.perform(get("/maison/verify/line/1/origin/3/destination/2/direction/50"))
				.andExpect(status().is(400));
	}

	@Test
	void testStepVerificationDirectionNotConnectedToLine() throws Exception {
		// line 1, Gare De Lyon -> Bastille, direction Père Lachaise[wrong]
		mvc.perform(get("/maison/verify/line/1/origin/3/destination/2/direction/9"))
				.andExpect(status().is(400));
	}

}
