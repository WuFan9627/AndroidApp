package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class ListChannelsReq {
    private String dataverseName;
    private String userId;
    private String accessToken;

    public ListChannelsReq(String dataverseName, String userId, String accessToken) {
        this.dataverseName = dataverseName;
        this.userId = userId;
        this.accessToken = accessToken;
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
}
