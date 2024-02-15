package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RepositoryWrapperTestSuite {
    @Mock
    private BranchRequest branchRequest;
    @Mock
    private ResponseReader responseReader;
    @Mock
    private HttpURLConnection mockConnection;
    @InjectMocks
    private RepositoryWrapper repositoryWrapper;
    private String mockGithubApiToken;
    private List<Branch> mockBranches;
    private String mockBranchName;
    private String mockBranchCommitSha;
    private Branch mockBranch;
    private int mockRepositoryId;
    private String mockRepositoryName;
    private String mockRepositoryOwnerLogin;
    private Repository mockRepository;
    private List<Repository> mockRepositories;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        responseReader = new ResponseReader();
        repositoryWrapper = new RepositoryWrapper(branchRequest, responseReader);

        mockGithubApiToken = "mockGithubApiToken";

        mockBranches = new ArrayList<>();
        mockBranchName = "mockBranchName";
        mockBranchCommitSha = "1234testcommitsha";
        mockBranch = new Branch(mockBranchName, mockBranchCommitSha);
        mockBranches.add(mockBranch);

        mockRepositories = new ArrayList<>();
        mockRepositoryId = 123;
        mockRepositoryName = "mockRepositoryName";
        mockRepositoryOwnerLogin = "mockRepositoryOwnerLogin";
        mockRepository = new Repository(mockRepositoryId, mockRepositoryName, mockRepositoryOwnerLogin, mockBranches);
        mockRepositories.add(mockRepository);
    }

    @Test
    void repositoryRequestTest() throws IOException {
        //Given
        String responseMock = "[{\"id\": " + mockRepositoryId + ", \"name\": \"" + mockRepositoryName +
                "\", \"owner\": {\"login\": \"" + mockRepositoryOwnerLogin + "\"}, \"fork\": false}]";

        String responseMockForked = "[{\"id\": " + mockRepositoryId + ", \"name\": \"" + mockRepositoryName +
                "\", \"owner\": {\"login\": \"" + mockRepositoryOwnerLogin + "\"}, \"fork\": true}]";

        //When
        byte[] mockResponseBytes = responseMock.getBytes();
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponseBytes));

        when(branchRequest.send(anyString(), anyString(), anyString())).thenReturn(mockBranches);
        List<Repository> result = repositoryWrapper.createList(mockRepositoryOwnerLogin, mockGithubApiToken, mockConnection);

        byte[] mockResponseBytesForked = responseMockForked.getBytes();
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponseBytesForked));
        List<Repository> resultForked = repositoryWrapper.createList(mockRepositoryOwnerLogin, mockGithubApiToken, mockConnection);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        Repository repository = result.get(0);
        assertEquals(mockRepositoryId, repository.getId());
        assertEquals(mockRepositoryName, repository.getName());
        assertEquals(mockRepositoryOwnerLogin, repository.getOwnerLogin());
        assertEquals(mockRepositories, result);

        assertNotNull(resultForked);
        assertEquals(0, resultForked.size());
    }
}