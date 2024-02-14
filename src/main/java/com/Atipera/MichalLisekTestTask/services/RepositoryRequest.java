package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.exception.ExceptionHandlerRepository;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.springframework.stereotype.Component;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class RepositoryRequest {
    private RepositoryWrapper repositoryWrapper;
    private ConnectionCreator connectionCreator;

    public RepositoryRequest(RepositoryWrapper repositoryWrapper, ConnectionCreator connectionCreator){
        this.repositoryWrapper = repositoryWrapper;
        this.connectionCreator = connectionCreator;
    }

    public List<Repository> send(String username, String githubApiToken) throws ExceptionHandlerRepository{
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
                throw new ExceptionHandlerRepository(connectionRepositories);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
