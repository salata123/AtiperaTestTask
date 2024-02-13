package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchRequest {
    private BranchWrapper branchWrapper;
    private ConnectionCreator connectionCreator;

    public BranchRequest(BranchWrapper branchWrapper, ConnectionCreator connectionCreator){
        this.branchWrapper = branchWrapper;
        this.connectionCreator = connectionCreator;
    }

    public List<Branch> send(String username, String name, String githubApiToken){
        List<Branch> branchList = new ArrayList<>();

        try{
            String githubBranchesUrl = "https://api.github.com/repos/" + username + "/" + name + "/branches";
            URL urlBranches = new URL(githubBranchesUrl);

            HttpURLConnection connectionBranches = connectionCreator.create(urlBranches, githubApiToken);

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
