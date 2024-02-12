package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
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
public class BranchRequest {
    public List<Branch> send(String username, String name, String githubApiToken){
        List<Branch> branchList = new ArrayList<>();

        try{
            String githubBranchesUrl = "https://api.github.com/repos/" + username + "/" + name + "/branches";

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

                JSONArray jsonArrayBranches = new JSONArray(responseBranches.toString());
                for (int j = 0; j < jsonArrayBranches.length(); j++){
                    JSONObject branchObject = jsonArrayBranches.getJSONObject(j);
                    String branchName = branchObject.getString("name");
                    String commitSha = branchObject.getJSONObject("commit").getString("sha");

                    branchList.add(new Branch(branchName, commitSha));
                }
            } else {
                System.out.println("ERROR: " + responseCodeBranches);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return branchList;
    }
}
