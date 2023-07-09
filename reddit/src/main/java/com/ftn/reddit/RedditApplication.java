package com.ftn.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@EntityScan("com.ftn.reddit.*")
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


