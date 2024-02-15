package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.exception.ExceptionHandlerRepository;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EndToEndTestSuite {
    private final RequestSender requestSender;

    @Autowired
    public EndToEndTestSuite(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Test
    void EndToEndTest() throws ExceptionHandlerRepository, IOException {
        //Given
        String existingUsername = "salata123";

        //When
        List<Repository> resultRepositoryList = requestSender.send(existingUsername);

        //Then
        assertNotNull(resultRepositoryList);
        assertEquals(640577563, resultRepositoryList.get(0).getId());
        assertEquals("4.6repo-michal-lisek", resultRepositoryList.get(0).getName());
        assertEquals("salata123", resultRepositoryList.get(0).getOwnerLogin());
        assertEquals("main", resultRepositoryList.get(0).getBranchList().get(0).getName());
        assertEquals("b4c3a0b448e71adea1f10126d5e585fd2b0cc130", resultRepositoryList.get(0).getBranchList().get(0).getCommitSha());
    }
}
