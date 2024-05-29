package team.kubermaptes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class PracticeSpaceApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(PracticeSpaceApplication.class, args);

	}

}
