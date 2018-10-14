package bad.twitterfriendschannel.gson.request;

import java.util.ArrayList;

/**
 * Created by Leo on 5/22/2017. edited by fan
 */

public class SubscribeReq {
    private String dataverseName;
    private String userId;//should be unique id
    //private String appId;
    private String accessToken;
    private String channelName;
    private ArrayList<String> parameters = new ArrayList<String>();

    //private String parameters;

    public SubscribeReq(String dataverseName, String userId, String channelName, String accessToken, ArrayList<String> parameters) {
        this.dataverseName = dataverseName;
        this.userId = userId;
        //this.appId = appId;
        //String tmp = parameters.get(0);
        //this.parameters.add(tmp);
        //this.parameters = parameters;

        this.accessToken = accessToken;
        this.channelName = channelName;
        for(int i = 0;i<parameters.size();i++){
            this.parameters.add(i,parameters.get(i));
        }


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

    //public String getAppId() {
       // return appId;
    //}

   // public void setAppId(String appId) {
        //this.appId = appId;
    //}

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

    public ArrayList<String> getParameters() {
        return parameters;
    }
    /*public void setParameters(ArrayList<String> parameters){
        this.parameters = parameters;
    } */

    public void setParameters(ArrayList<String> parameters) {
        for(int i = 0;i<parameters.size();i++){
            this.parameters.add(i,parameters.get(i));
        }
    }
}
