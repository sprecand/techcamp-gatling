package ch.techcamp.gatling_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GatlingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatlingBackendApplication.class, args);
	}
}
