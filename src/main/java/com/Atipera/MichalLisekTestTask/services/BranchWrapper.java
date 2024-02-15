package com.Atipera.MichalLisekTestTask.services;

import com.Atipera.MichalLisekTestTask.github.Branch;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchWrapper {
    private final ResponseReader responseReader;

    public BranchWrapper (ResponseReader responseReader){
        this.responseReader = responseReader;
    }

    public List<Branch> createList (HttpURLConnection connectionBranches) throws IOException {
        List<Branch> branchList = new ArrayList<>();
        JSONArray jsonArrayBranches = responseReader.toJson(connectionBranches);

        for (int j = 0; j < jsonArrayBranches.length(); j++){
            JSONObject branchObject = jsonArrayBranches.getJSONObject(j);
            String branchName = branchObject.getString("name");
            String commitSha = branchObject.getJSONObject("commit").getString("sha");

            branchList.add(new Branch(branchName, commitSha));
        }
        return branchList;
    }
}
