package bad.twitterfriendschannel.gson.response;

/**
 * Created by Leo on 5/22/2017.
 */

public class RegisterRes {
    private String status;
    private String userId;
    private String error;

    public RegisterRes(String status, String userId) {
        this.status = status;
        this.userId = userId;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
