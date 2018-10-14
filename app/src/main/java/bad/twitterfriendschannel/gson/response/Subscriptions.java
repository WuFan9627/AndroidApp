package bad.twitterfriendschannel.gson.response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Leo on 5/29/2017.
 */

public class Subscriptions {
    @Expose(deserialize = true)
    private String channelName;
    @Expose(deserialize = true)
    private String userSubscriptionId;
    @Expose(deserialize = true)
    private ArrayList<String> Parameters;

    public Subscriptions(String channel, String userSubscriptionId, ArrayList<String> parameters) {
        this.channelName = channel;
        this.userSubscriptionId = userSubscriptionId;
        for (int i =0;i<parameters.size();++i){
            Parameters.set(i,parameters.get(i));
        }

    }

    public String getChannel() {
        return channelName;
    }

    public void setChannel(String channel) {
        this.channelName = channel;
    }

    public String getSubscriptionId() {
        return userSubscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.userSubscriptionId = subscriptionId;
    }

    public ArrayList<String> getParameters() {
        return Parameters;
    }

    public void setParameters(ArrayList<String> parameters){
        for (int i =0;i<parameters.size();++i){
            Parameters.set(i,parameters.get(i));
        }
    }

}
