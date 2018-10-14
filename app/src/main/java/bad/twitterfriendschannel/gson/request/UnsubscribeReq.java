package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class UnsubscribeReq {
    private String dataverseName;
    private String userId;
    private String accessToken;
    private String userSubscriptionId;

    public UnsubscribeReq(String dataverseName, String userId, String accessToken, String userSubscriptionId) {
        this.dataverseName = dataverseName;
        this.userId = userId;
        this.accessToken = accessToken;
        this.userSubscriptionId = userSubscriptionId;
    }

    public String getDataverseName() {
        return dataverseName;
    }

    public void setDataverseName(String dataverseName) {
        this.dataverseName = dataverseName;
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

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserSubscriptionId() {
        return userSubscriptionId;
    }

    public void setUserSubscriptionId(String userSubscriptionId) {
        this.userSubscriptionId = userSubscriptionId;
    }
}
