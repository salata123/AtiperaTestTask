package com.Atipera.MichalLisekTestTask;

import com.Atipera.MichalLisekTestTask.services.RequestSender;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Run {
    private final RequestSender requestSender;

    public Run(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a Username to search their repositories:");
        String username = scanner.nextLine();

        requestSender.send(username);
    }
}
