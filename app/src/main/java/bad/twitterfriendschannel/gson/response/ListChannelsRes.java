package bad.twitterfriendschannel.gson.response;

import java.util.ArrayList;

/**
 * Created by Leo on 5/22/2017.
 */

public class ListChannelsRes {
    private String status;
    private ArrayList<Channels> channels;


    public ListChannelsRes(String status, ArrayList<Channels> channels) {
        this.status = status;
        this.channels = channels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Channels> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channels> channels) {
        this.channels = channels;
    }
}
