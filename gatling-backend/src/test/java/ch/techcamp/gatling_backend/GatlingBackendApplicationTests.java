package ch.techcamp.gatling_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GatlingBackendApplication.class)
class GatlingBackendApplicationTests {

	@Test
	void contextLoads() {
		assertEquals(2,1+1);
	}

}
