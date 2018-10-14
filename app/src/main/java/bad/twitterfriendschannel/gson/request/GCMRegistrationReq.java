package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class GCMRegistrationReq {
    private String dataverseName;
    private String userId;
    private String accessToken;
    private String gcmRegistrationToken;

    public GCMRegistrationReq(String dataverseName, String userId,String accessToken,String gcmRegistrationToken) {
        this.dataverseName = dataverseName;
        this.userId = userId;
        this.accessToken = accessToken;
        this.gcmRegistrationToken = gcmRegistrationToken;
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

    public String getGcmRegistrationToken() {
        return gcmRegistrationToken;
    }

    public void setGcmRegistrationToken(String gcmRegistrationToken) {
        this.gcmRegistrationToken = gcmRegistrationToken;
    }
}
