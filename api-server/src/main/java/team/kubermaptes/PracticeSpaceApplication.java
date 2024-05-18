package team.kubermaptes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "team.kubermaptes")
public class PracticeSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpaceApplication.class, args);

		System.out.println("Hello World");
	}

}
