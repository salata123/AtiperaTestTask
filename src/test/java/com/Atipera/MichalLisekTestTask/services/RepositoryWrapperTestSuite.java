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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RepositoryWrapperTestSuite {
    @Mock
    private BranchRequest branchRequest;
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

        mockGithubApiToken = "mockGithubApiToken";

        mockBranches = new ArrayList<>();
        mockBranchName = "mockBranchName";
        mockBranchCommitSha = "1234testcommitsha";
        mockBranch = new Branch(mockBranchName, mockBranchCommitSha);
        mockBranches.add(mockBranch);

        mockRepositoryId = 123;
        mockRepositoryName = "mockRepositoryName";
        mockRepositoryOwnerLogin = "mockRepositoryOwnerLogin";
        mockRepository = new Repository(mockRepositoryId, mockRepositoryName, mockRepositoryOwnerLogin, mockBranches);
        mockRepositories = new ArrayList<>();
        mockRepositories.add(mockRepository);
    }

    @Test
    void repositoryRequestTest() throws IOException {
        //Given
        String responseMock = "[{\"id\": " + mockRepositoryId + ", \"name\": \"" + mockRepositoryName +
                "\", \"owner\": {\"login\": \"" + mockRepositoryOwnerLogin + "\"}, \"fork\": false}]";

        byte[] mockResponseBytes = responseMock.getBytes();
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponseBytes));

        // When
        when(branchRequest.send(anyString(), anyString(), anyString())).thenReturn(mockBranches);
        List<Repository> result = repositoryWrapper.createList(mockRepositoryOwnerLogin, mockGithubApiToken, mockConnection);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        Repository repository = result.get(0);
        assertEquals(mockRepositoryId, repository.getId());
        assertEquals(mockRepositoryName, repository.getName());
        assertEquals(mockRepositoryOwnerLogin, repository.getOwnerLogin());
        assertEquals(mockRepositories, result);
    }
}