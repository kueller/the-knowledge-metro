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
class KnowledgeBasicTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void testStepVerificationSuccess() throws Exception {
		// line 3bis, Gambetta -> Pelleport, direction Pelleport
		mvc.perform(get("/maison/verify/line/4/origin/10/destination/11/direction/11"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(0))
				.andExpect(jsonPath("$.message").isEmpty())
				.andExpect(jsonPath("$.origin_id").value(10));
	}

	@Test
	void testStepVerificationSuccessInternal() throws Exception {
		// line 2, Philippe Auguste -> Avron, direction Nation
		mvc.perform(get("/maison/verify/line/2/origin/8/destination/6/direction/5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(0))
				.andExpect(jsonPath("$.message").isEmpty())
				.andExpect(jsonPath("$.origin_id").value(8));
	}

	@Test
	void testStepVerificationWrongAnswerOriginNotOnLineNoTunnel() throws Exception {
		// line 2, Gare de Lyon[wrong] -> Avron, direction Nation
		mvc.perform(get("/maison/verify/line/2/origin/3/destination/6/direction/5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(-1))
				.andExpect(jsonPath("$.message").value("origin-not-on-line"))
				.andExpect(jsonPath("$.origin_id").isEmpty());
	}

	@Test
	void testStepVerificationWrongAnswerDestinationNotOnLine() throws Exception {
		// line 1, Gare de Lyon -> Avron[wrong], direction Nation
		mvc.perform(get("/maison/verify/line/1/origin/3/destination/6/direction/5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(-1))
				.andExpect(jsonPath("$.message").value("dest-not-on-line"))
				.andExpect(jsonPath("$.origin_id").isEmpty());
	}

	@Test
	void testStepVerificationWrongAnswerDestinationWrongWay() throws Exception {
		// line 1, Gare de Lyon -> Bastille, direction Nation[wrong]
		mvc.perform(get("/maison/verify/line/1/origin/3/destination/2/direction/5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(-1))
				.andExpect(jsonPath("$.message").value("wrong-way"))
				.andExpect(jsonPath("$.origin_id").isEmpty());
	}

}
