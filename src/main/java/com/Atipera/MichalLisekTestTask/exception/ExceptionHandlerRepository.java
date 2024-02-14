package com.Atipera.MichalLisekTestTask.exception;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ExceptionHandlerRepository extends Exception {
    public ExceptionHandlerRepository(HttpURLConnection connectionRepositories) throws IOException {
        super(handleError(connectionRepositories));
    }

    private static String handleError(HttpURLConnection connectionRepositories) throws IOException {
        String message = read(connectionRepositories);
        int responseCode = connectionRepositories.getResponseCode();

        if (responseCode != 404) {
            return "Unexpected error with status code: " + responseCode;
        } else {
            return "\n" +
                   "{\n" +
                   "\t\"status\": " + responseCode +
                   "\n" +
                   "\t\"message\": " + message +
                   "\n" +
                   "}";
        }
    }

    private static String read(HttpURLConnection connectionRepositories) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionRepositories.getErrorStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);

        }

        JSONObject errorObject = new JSONObject(response.toString());
        return errorObject.getString("message");
    }
}