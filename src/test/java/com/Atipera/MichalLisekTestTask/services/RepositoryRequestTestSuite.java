package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RepositoryRequestTestSuite {
    @Mock
    private RepositoryWrapper repositoryWrapper;
    @Mock
    private ConnectionCreator connectionCreator;
    @InjectMocks
    private RepositoryRequest repositoryRequest;

    private String mockGithubApiToken;
    private List<Branch> mockBranches;
    private String mockBranchName;
    private String mockBrachCommitSha;
    private Branch mockBranch;
    private int mockRepositoryId;
    private String mockRepositoryName;
    private String mockRepositoryOwnerLogin;
    private Repository mockRepository;
    private List<Repository> mockRepositories;
    private String githubRepositoriesUrl;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        mockGithubApiToken = "mockGithubApiToken";

        mockBranches = new ArrayList<>();
        mockBranchName = "mockBranchName";
        mockBrachCommitSha = "1234testcommitsha";
        mockBranch = new Branch(mockBranchName, mockBrachCommitSha);
        mockBranches.add(mockBranch);

        mockRepositoryId = 123;
        mockRepositoryName = "mockRepositoryName";
        mockRepositoryOwnerLogin = "mockRepositoryOwnerLogin";
        mockRepository = new Repository(mockRepositoryId, mockRepositoryName, mockRepositoryOwnerLogin, mockBranches);
        mockRepositories = new ArrayList<>();
        mockRepositories.add(mockRepository);

        githubRepositoriesUrl = "https://api.github.com/users/" + mockRepositoryOwnerLogin + "/repos";
    }

    @Test
    void repositoryRequestTest() throws IOException {
        //Given
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        URL mockUrl = new URL(githubRepositoriesUrl);

        //When
        when(connectionCreator.create(mockUrl, mockGithubApiToken)).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(repositoryWrapper.createList(mockRepositoryOwnerLogin, mockGithubApiToken, connectionCreator.create(mockUrl, mockGithubApiToken))).thenReturn(mockRepositories);

        //Then
        List<Repository> returnedRepositoryList = repositoryRequest.send(mockRepositoryOwnerLogin, mockGithubApiToken);
        Assertions.assertEquals(returnedRepositoryList, mockRepositories);
    }
}
