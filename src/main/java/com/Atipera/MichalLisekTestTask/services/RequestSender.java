package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestSender {
    @Value("${github.api.token}")
    private String githubApiToken;
    private RepositoryRequest repositoryRequest = new RepositoryRequest();

    public List<Repository> send(String username){
        List<Repository> repositories = repositoryRequest.send(username, githubApiToken);
        return repositories;
    }
}
