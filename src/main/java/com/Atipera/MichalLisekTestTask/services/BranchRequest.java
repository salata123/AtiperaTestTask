package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import org.springframework.stereotype.Component;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class BranchRequest {
    private BranchWrapper branchWrapper = new BranchWrapper();
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
                branchList = branchWrapper.createList(connectionBranches);
            } else {
                System.out.println("ERROR: " + responseCodeBranches);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return branchList;
    }
}
