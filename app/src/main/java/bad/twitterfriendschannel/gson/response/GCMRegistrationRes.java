package bad.twitterfriendschannel.gson.response;

/**
 * Created by Leo on 5/22/2017.
 */

public class GCMRegistrationRes {
    private String status;
    private String error;

    public GCMRegistrationRes(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
