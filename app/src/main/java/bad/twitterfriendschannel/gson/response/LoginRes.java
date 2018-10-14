package bad.twitterfriendschannel.gson.response;

import java.util.ArrayList;

/**
 * Created by Leo on 5/22/2017.
 */

public class LoginRes {
    private String status;
    private String userId;
    private String accessToken;
    private String error;

    public LoginRes(String status, String userId, String accessToken) {
        this.status = status;
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
