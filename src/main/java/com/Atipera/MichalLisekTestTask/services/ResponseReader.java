package com.Atipera.MichalLisekTestTask.services;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Component
public class ResponseReader {
    public JSONArray toJson(HttpURLConnection connection) throws IOException {
        BufferedReader readerBranches = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLineBranches;
        StringBuilder responseBranches = new StringBuilder();

        while ((inputLineBranches = readerBranches.readLine()) != null){
            responseBranches.append(inputLineBranches);
        }
        readerBranches.close();

        return new JSONArray(responseBranches.toString());
    }
}
