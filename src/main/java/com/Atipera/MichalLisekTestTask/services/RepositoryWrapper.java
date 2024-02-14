package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryWrapper {
    private BranchRequest branchRequest;

    public RepositoryWrapper(BranchRequest branchRequest){
        this.branchRequest = branchRequest;
    }

    public List<Repository> createList (String username, String githubApiToken, HttpURLConnection connectionRepositories) throws IOException {
        List<Repository> repositories = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionRepositories.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null){
            response.append(inputLine);
        }
        reader.close();

        JSONArray jsonArray = new JSONArray(response.toString());
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
