package ftn.com.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaRepositories("ftn.com.reddit.*")
@ComponentScan(basePackages = { "ftn.com.reddit.*" })
@EntityScan("ftn.com.reddit.*")
public class RedditApplication {

	public static void main(String[] args){
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/reddit?autoReconnect=true&useSSL=false", "root", "root")) {

			if (conn != null) {
				System.out.println("Connected to the database!");
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
			SpringApplication.run(RedditApplication.class, args);
		}

	}


