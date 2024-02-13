package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.exception.ErrorReader;
import com.Atipera.MichalLisekTestTask.exception.ExceptionHandlerRepository;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class RepositoryRequest {
    private RepositoryWrapper repositoryWrapper;
    private ConnectionCreator connectionCreator;
    public RepositoryRequest(RepositoryWrapper repositoryWrapper, ConnectionCreator connectionCreator){
        this.repositoryWrapper = repositoryWrapper;
        this.connectionCreator = connectionCreator;
    }
    private ErrorReader errorReader = new ErrorReader();

    public List<Repository> send(String username, String githubApiToken){
        List<Repository> repositories;

        try {
            String githubRepositoriesUrl = "https://api.github.com/users/" + username + "/repos";
            URL urlRepositories = new URL(githubRepositoriesUrl);

            HttpURLConnection connectionRepositories = connectionCreator.create(urlRepositories, githubApiToken);

            int responseCodeRepositories = connectionRepositories.getResponseCode();

            if (responseCodeRepositories == HttpURLConnection.HTTP_OK){
                repositories = repositoryWrapper.createList(username, githubApiToken, connectionRepositories);
                System.out.println(repositories);
                return repositories;

            } else {
                String message = errorReader.read(connectionRepositories);
                throw ExceptionHandlerRepository.handleError(responseCodeRepositories, message);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
