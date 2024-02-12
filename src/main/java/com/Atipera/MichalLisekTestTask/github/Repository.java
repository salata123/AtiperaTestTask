package com.Atipera.MichalLisekTestTask.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private int id;
    private String name;
    private String ownerLogin;
    private List<Branch> branchList;
}
