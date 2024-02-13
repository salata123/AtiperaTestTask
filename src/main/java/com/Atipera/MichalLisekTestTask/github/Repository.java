package com.Atipera.MichalLisekTestTask.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Repository {
    private int id;
    private String name;
    private String ownerLogin;
    private List<Branch> branchList;
}
