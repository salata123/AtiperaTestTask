package com.Atipera.MichalLisekTestTask.services;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ConnectionCreator {
    public HttpURLConnection create(URL url, String githubApiToken) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("Accept", "application/vnd.github+json");
        connection.setRequestProperty("Authorization", "Bearer " + githubApiToken);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");

        return connection;
    }
}
