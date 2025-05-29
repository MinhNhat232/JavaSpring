package mn7.spring.mn7_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)

public class Mn7SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mn7SpringbootApplication.class, args);
	}

}
