package caguilera.assessment.nhs.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * A SIMPLE rest service to search and point the user to the most appropriate links
 * for requests like “what are the symptoms of cancer?” “treatments for
 * headaches”
 * 
 * @author Cesar Aguilera
 *
 */
@SpringBootApplication(scanBasePackages = { "caguilera.assessment.nhs.impl",
		"caguilera.assessment.nhs.rest" }, exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class NhsConditionsRestService {
	public static void main(String[] args) {
		SpringApplication.run(NhsConditionsRestService.class, args);
	}
}