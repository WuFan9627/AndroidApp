package org.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by Leo on 4/2/2017.
 */

public class Subscription extends DataSupport{

    @Override
    public boolean equals(Object obj) {
        boolean same = false;
        if (obj != null && obj instanceof Subscription) {
            same = (this.channelName.equals(((Subscription) obj).channelName) && this.userId.equals(((Subscription)obj).userId));
        }
        return same;
    }

    private String channelName;
    private String userSubscriptionId;
    private String timestamp;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
