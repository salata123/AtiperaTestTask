package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BranchWrapperTestSuite {
    @Mock
    HttpURLConnection mockConnection;
    @InjectMocks
    private BranchWrapper branchWrapper;

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
        String responseMock = "[{\"name\":\"" + mockBranchName + "\",\"commit\":{\"sha\":\"" + mockBranchCommitSha + "\"}}]";

        byte[] mockResponseBytes = responseMock.getBytes();

        // When
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponseBytes));
        List<Branch> result = branchWrapper.createList(mockConnection);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockBranchName, result.get(0).getName());
        assertEquals(mockBranchCommitSha, result.get(0).getCommitSha());
    }
}