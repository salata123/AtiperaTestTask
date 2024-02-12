package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepositoryRequest {
    private BranchRequest branchRequest = new BranchRequest();

    public List<Repository> send(String username, String githubApiToken){
        List<Repository> repositories = new ArrayList<>();

        try {
            String githubRepositoriesUrl = "https://api.github.com/users/" + username + "/repos";
            URL urlRepositories = new URL(githubRepositoriesUrl);

            HttpURLConnection connectionRepositories = (HttpURLConnection) urlRepositories.openConnection();

            connectionRepositories.setRequestMethod("GET");

            connectionRepositories.setRequestProperty("Accept", "application/vnd.github+json");
            connectionRepositories.setRequestProperty("Authorization", "Bearer " + githubApiToken);
            connectionRepositories.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
            System.out.println(githubApiToken);

            int responseCodeRepositories = connectionRepositories.getResponseCode();

            if (responseCodeRepositories == HttpURLConnection.HTTP_OK){
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
            } else {
                System.out.println("{\n" +
                        "\t\"status\": " + responseCodeRepositories +
                        "\n" +
                        "\t\"message\": " + "No username found in Github database" +
                        "\n" +
                        "}");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(repositories);

        return repositories;
    }
}
