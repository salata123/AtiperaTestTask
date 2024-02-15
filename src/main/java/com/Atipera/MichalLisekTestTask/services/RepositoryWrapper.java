package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryWrapper {
    private final BranchRequest branchRequest;
    private final ResponseReader responseReader;

    public RepositoryWrapper(BranchRequest branchRequest, ResponseReader responseReader){
        this.branchRequest = branchRequest;
        this.responseReader = responseReader;
    }

    public List<Repository> createList (String username, String githubApiToken, HttpURLConnection connectionRepositories) throws IOException {
        List<Repository> repositories = new ArrayList<>();
        JSONArray jsonArray = responseReader.toJson(connectionRepositories);

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject repositoryObject = jsonArray.getJSONObject(i);
            if(!repositoryObject.getBoolean("fork")){
                int id = repositoryObject.getInt("id");
                String name = repositoryObject.getString("name");
                String ownerLogin = repositoryObject.getJSONObject("owner").getString("login");

                List<Branch> branchList = branchRequest.send(username, name, githubApiToken);

                repositories.add(new Repository(id, name, ownerLogin, branchList));
            }
        }
        return repositories;
    }
}
