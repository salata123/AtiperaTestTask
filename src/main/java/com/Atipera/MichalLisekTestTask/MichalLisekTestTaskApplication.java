package com.Atipera.MichalLisekTestTask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MichalLisekTestTaskApplication implements CommandLineRunner {
	private final RequestSender requestSender;

	public MichalLisekTestTaskApplication(RequestSender requestSender) {
		this.requestSender = requestSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(MichalLisekTestTaskApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please provide a Username to search their repositories:");
		String username = scanner.nextLine();

		requestSender.send(username);
	}
}
