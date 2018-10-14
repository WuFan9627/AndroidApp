package bad.twitterfriendschannel.gson.response;

/**
 * Created by Leo on 5/22/2017.
 */

public class SubscribeRes {
    private String status;
    private String userSubscriptionId;
    private String timestamp;

    public SubscribeRes(String status, String userSubscriptionId, String timestamp) {
        this.status = status;
        this.userSubscriptionId = userSubscriptionId;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserSubscriptionId() {
        return userSubscriptionId;
    }

    public void setUserSubscriptionId(String userSubscriptionId) {
        this.userSubscriptionId = userSubscriptionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
