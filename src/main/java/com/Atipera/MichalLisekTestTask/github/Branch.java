package com.Atipera.MichalLisekTestTask.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Branch {
    private String name;
    private String commitSha;
}
