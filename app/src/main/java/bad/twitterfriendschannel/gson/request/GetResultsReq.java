package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class GetResultsReq {
    private String dataverseName;
    private String userId;
    private String accessToken;
    private String channelName;
    private String userSubscriptionId;
    private String channelExecutionTime;

    public GetResultsReq(String dataverseName, String userId, String accessToken, String channelName, String userSubscriptionId, String channelExecutionTime) {
        this.dataverseName = dataverseName;
        this.userId = userId;
        this.accessToken = accessToken;
        this.channelName = channelName;
        this.userSubscriptionId = userSubscriptionId;
        this.channelExecutionTime = channelExecutionTime;
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserSubscriptionId() {
        return userSubscriptionId;
    }

    public void setUserSubscriptionId(String userSubscriptionId) {
        this.userSubscriptionId = userSubscriptionId;
    }

    public String getChannelExecutionTime() {
        return channelExecutionTime;
    }

    public void setChannelExecutionTime(String channelExecutionTime) {
        this.channelExecutionTime = channelExecutionTime;
    }
}
