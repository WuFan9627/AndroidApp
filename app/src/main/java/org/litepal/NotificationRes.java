package org.litepal;


import java.io.Serializable;
import java.util.HashMap;

import bad.twitterfriendschannel.Point;

/**
 * Created by Leo on 5/19/2017.
 */

public class NotificationRes implements Serializable{
    private HashMap<String, Point> starbucks;
    private HashMap<String, Point> friends;

    public NotificationRes() {
        starbucks = new HashMap<>();
        friends = new HashMap<>();
    }

    public HashMap<String, Point> getStarbucks() {
        return starbucks;
    }

    public void setStarbucks(HashMap<String, Point> starbucks) {
        this.starbucks = starbucks;
    }

    public HashMap<String, Point> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<String, Point> friends) {
        this.friends = friends;
    }
}
