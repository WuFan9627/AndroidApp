package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class LoginReq {
    private String dataverseName;
    private String userName;
    private String password;
    private String platform;

    public LoginReq(String dataverseName, String userName, String password, String platform) {
        this.dataverseName = dataverseName;
        this.userName = userName;
        this.password = password;
        this.platform = platform;
    }

    public String getDataverseName() {
        return dataverseName;
    }

    public void setDataverseName(String dataverseName) {
        this.dataverseName = dataverseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
