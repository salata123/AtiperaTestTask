package com.Atipera.MichalLisekTestTask;

import com.Atipera.MichalLisekTestTask.services.RequestSender;
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
		try (Scanner scanner = new Scanner(System.in)) {
			String username = "";

			while (!username.equals("exit")) {
				System.out.print("Please provide a username to search users' repositories or type 'exit' to close the app: ");
				username = scanner.nextLine();

				if (!username.equals("exit")) {
					requestSender.send(username);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
