package team.kubermaptes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import team.kubermaptes.config.ContextConfig;

@SpringBootApplication(scanBasePackageClasses = ContextConfig.class)
public class PracticeSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpaceApplication.class, args);
	}

}
