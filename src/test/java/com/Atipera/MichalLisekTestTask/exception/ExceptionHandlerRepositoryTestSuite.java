package com.Atipera.MichalLisekTestTask.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExceptionHandlerRepositoryTestSuite {
    @Mock
    private HttpURLConnection mockConnection;

    @BeforeEach
    void setup(){
        mockConnection = mock(HttpURLConnection.class);
    }

    @Test
    void testHandleError404() throws IOException {
        //Given
        String mockErrorMessage = "{\"message\": \"Not Found\"," +
                "\"documentation_url\": \"https://docs.github.com/rest/repos/repos#list-repositories-for-a-user\"\n" +
                "}";
        InputStream mockInputStream = new ByteArrayInputStream(mockErrorMessage.getBytes());

        //When
        when(mockConnection.getResponseCode()).thenReturn(404);
        when(mockConnection.getErrorStream()).thenReturn(mockInputStream);

        ExceptionHandlerRepository exceptionHandlerRepository = new ExceptionHandlerRepository(mockConnection);

        //Then
        String errorMessage = exceptionHandlerRepository.getMessage();

        assertEquals("\n{\n\t\"status\": 404\n\t\"message\": Not Found\n}", errorMessage);
    }

    @Test
    void testHandleErrorUnkownCode() throws IOException {
        //Given
        String mockErrorMessage = "{\"message\": \"test\"}";
        InputStream mockInputStream = new ByteArrayInputStream(mockErrorMessage.getBytes());

        //When
        when(mockConnection.getResponseCode()).thenReturn(567756);
        when(mockConnection.getErrorStream()).thenReturn(mockInputStream);

        ExceptionHandlerRepository exceptionHandlerRepository = new ExceptionHandlerRepository(mockConnection);

        //Then
        String errorMessage = exceptionHandlerRepository.getMessage();

        assertEquals("Unexpected error with status code: 567756", errorMessage);
    }
}
