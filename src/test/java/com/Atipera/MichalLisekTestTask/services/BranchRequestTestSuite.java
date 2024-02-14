package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
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
public class BranchRequestTestSuite {
    @Mock
    private BranchWrapper branchWrapper;
    @Mock
    private ConnectionCreator connectionCreator;
    @InjectMocks
    private BranchRequest branchRequest;
    private String mockGithubApiToken;
    private List<Branch> mockBranches;
    private String mockBranchName;
    private String mockBrachCommitSha;
    private Branch mockBranch;
    private String mockRepositoryName;
    private String mockRepositoryOwnerLogin;
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

        mockRepositoryName = "mockRepositoryName";
        mockRepositoryOwnerLogin = "mockRepositoryOwnerLogin";

        githubRepositoriesUrl = "https://api.github.com/repos/" + mockRepositoryOwnerLogin + "/" + mockRepositoryName + "/branches";
    }

    @Test
    void repositoryRequestTest() throws IOException {
        //Given
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        URL mockUrl = new URL(githubRepositoriesUrl);

        //When
        when(connectionCreator.create(mockUrl, mockGithubApiToken)).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(branchWrapper.createList(connectionCreator.create(mockUrl, mockGithubApiToken))).thenReturn(mockBranches);

        //Then
        List<Branch> returnedBranchList = branchRequest.send(mockRepositoryOwnerLogin, mockRepositoryName, mockGithubApiToken);
        Assertions.assertEquals(returnedBranchList, mockBranches);
    }
}
