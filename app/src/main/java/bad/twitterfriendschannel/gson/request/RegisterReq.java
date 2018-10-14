package bad.twitterfriendschannel.gson.request;

/**
 * Created by Leo on 5/22/2017.
 */

public class RegisterReq {

    private String dataverseName;
    private String userName;
    private String email;
    private String password;

    public RegisterReq(String dataverseName, String userName, String password,String email) {
        this.dataverseName = dataverseName;
        this.userName = userName;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
