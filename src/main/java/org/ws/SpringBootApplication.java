package org.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(encoding = "UTF-8", value = {"classpath:mqtt.properties"})
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	} 
}
