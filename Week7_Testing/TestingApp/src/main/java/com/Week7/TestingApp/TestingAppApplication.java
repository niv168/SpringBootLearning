package com.Week7.TestingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// Source - https://stackoverflow.com/a/31199853
// Posted by Jason Warner, modified by community. See post 'Timeline' for change history
// Retrieved 2026-07-16, License - CC BY-SA 4.0

@SpringBootApplication

public class TestingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingAppApplication.class, args);
	}

}
