package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
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
public class BranchWrapper {
    public List<Branch> createList (HttpURLConnection connectionBranches) throws IOException {
        List<Branch> branchList = new ArrayList<>();
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
        return branchList;
    }
}
