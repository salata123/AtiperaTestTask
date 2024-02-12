package com.Atipera.MichalLisekTestTask;

import com.Atipera.MichalLisekTestTask.github.Branch;
import com.Atipera.MichalLisekTestTask.github.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestSender {
    @Value("${github.api.token}")
    private String githubApiToken;

    public List<Repository> send(String username){
        List<Branch> branchList = new ArrayList<>();
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
                    int id = repositoryObject.getInt("id");
                    String name = repositoryObject.getString("name");
                    String ownerLogin = repositoryObject.getJSONObject("owner").getString("login");


                    String githubBranchesUrl = "https://api.github.com/repos/" + username + "/" + name + "/branches";
                    System.out.println(githubBranchesUrl);

                    URL urlBranches = new URL(githubBranchesUrl);

                    HttpURLConnection connectionBranches = (HttpURLConnection) urlBranches.openConnection();

                    connectionBranches.setRequestMethod("GET");

                    connectionBranches.setRequestProperty("Accept", "application/vnd.github+json");
                    connectionBranches.setRequestProperty("Authorization", "Bearer " + githubApiToken);
                    connectionBranches.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");

                    int responseCodeBranches = connectionBranches.getResponseCode();

                    if(responseCodeBranches == HttpURLConnection.HTTP_OK){
                        BufferedReader readerBranches = new BufferedReader(new InputStreamReader(connectionBranches.getInputStream()));
                        String inputLineBranches;
                        StringBuilder responseBranches = new StringBuilder();

                        while ((inputLineBranches = readerBranches.readLine()) != null){
                            responseBranches.append(inputLineBranches);
                        }
                        readerBranches.close();
                        System.out.println(responseBranches);

                        JSONArray jsonArrayBranches = new JSONArray(responseBranches.toString());
                        for (int j = 0; j < jsonArrayBranches.length()-1; j++){
                            System.out.println(repositoryObject.length());
                            JSONObject branchObject = jsonArrayBranches.getJSONObject(j);
                            String branchName = branchObject.getString("name");
                            System.out.println(branchName);
                            String commitSha = branchObject.getJSONObject("commit").getString("sha");
                            System.out.println(commitSha);

                            branchList.add(new Branch(branchName, commitSha));
                            System.out.println(branchList);
                        }
                    } else {
                        System.out.println("ERROR: " + responseCodeBranches);
                    }

                    repositories.add(new Repository(id, name, ownerLogin, branchList));
                    System.out.println(repositories);
                }
            } else {
                System.out.println("Error:" + responseCodeRepositories);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(repositories);
        return repositories;
    }
}
