package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.exception.ExceptionHandlerRepository;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RequestSender {
    @Value("${github.api.token}")
    private String githubApiToken;
    private RepositoryRequest repositoryRequest;
    public RequestSender(RepositoryRequest repositoryRequest){
        this.repositoryRequest = repositoryRequest;
    }

    public List<Repository> send(String username) throws ExceptionHandlerRepository, IOException {
        List<Repository> repositories = repositoryRequest.send(username, githubApiToken);
        return repositories;
    }
}
