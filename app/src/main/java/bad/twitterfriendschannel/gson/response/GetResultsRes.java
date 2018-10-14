package bad.twitterfriendschannel.gson.response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * Created by Leo on 5/22/2017.
 */

public class GetResultsRes {
    @Expose(deserialize = true)
    private  String status;
    @Expose(deserialize = true)
    private ArrayList<Request> results;

    public GetResultsRes(String status, ArrayList results) {
        this.status = status;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList getResults() {
        return results;
    }

    public void setResults(ArrayList results) {
        this.results = results;
    }
}
