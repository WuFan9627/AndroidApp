package bad.twitterfriendschannel.gson.response;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Leo on 5/22/2017.
 */

public class ListSubscriptionsRes {
    @Expose(deserialize = true)
    private String status;
    @Expose(deserialize = true)
    private ArrayList<Subscriptions> subscriptions;

    public ListSubscriptionsRes(String status, ArrayList<Subscriptions> subscriptions) {
        this.status = status;
        for (int i =0;i<subscriptions.size();++i){
            this.subscriptions.set(i,subscriptions.get(i));
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Subscriptions> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(ArrayList<Subscriptions> subscriptions) {
        for (int i =0;i<subscriptions.size();++i){
            this.subscriptions.set(i,subscriptions.get(i));
        }
    }
}
