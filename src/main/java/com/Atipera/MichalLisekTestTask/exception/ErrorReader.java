package com.Atipera.MichalLisekTestTask.exception;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ErrorReader {
    public String read(HttpURLConnection connectionRepositories) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionRepositories.getErrorStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        JSONObject errorObject = new JSONObject(response.toString());
        return errorObject.getString("message");
    }
}
