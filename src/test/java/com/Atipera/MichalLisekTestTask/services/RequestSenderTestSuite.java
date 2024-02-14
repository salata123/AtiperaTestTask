package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.exception.ExceptionHandlerRepository;
import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
public class RequestSenderTestSuite {
    @Mock
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
    }

    @Test
    void requestSenderTest() throws ExceptionHandlerRepository {
        //When
        when(repositoryRequest.send(mockRepositoryName, mockGithubApiToken)).thenReturn(mockRepositories);

        //Then
        Assertions.assertEquals(repositoryRequest.send(mockRepositoryName, mockGithubApiToken), mockRepositories);
    }
}
