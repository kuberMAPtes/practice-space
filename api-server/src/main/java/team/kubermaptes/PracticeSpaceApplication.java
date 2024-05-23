package team.kubermaptes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class PracticeSpaceApplication {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/test_db";
	private static final String USER = "scott";
	private static final String PW = "tiger";

	public static void main(String[] args) throws Exception{

		SpringApplication.run(PracticeSpaceApplication.class, args);

		try(Connection con = DriverManager.getConnection(URL, USER, PW)){

			System.out.println("안녕하세용가리?"+con);

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("desc testTable;");

			while(rs.next()){
				System.out.println(rs.getString("Type"));
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
		}
	}



}
