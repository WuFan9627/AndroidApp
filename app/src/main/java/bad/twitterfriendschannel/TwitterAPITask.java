package bad.twitterfriendschannel;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.*;

import twitter4j.PagableResponseList;
import twitter4j.TwitterFactory;
import twitter4j.IDs;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPITask extends AsyncTask<String, String, ArrayList<User>> {

    public enum ResponseResult {SUCCESS, FAILURE};
    private static final String TAG = "TwitterAPICallActivity";
    private static final String TWITTER_KEY = "wEyhnNVay68AOJKD7rCxEQqzY";
    private static final String TWITTER_SECRET = "lk9LoIhJORjdy8hxx4h8hg76E0YbEjCsBlwklTigMT6FQTP1MQ";
    private static final String TWITTER_USER_KEY = "981650490533363712-Juwji0emeT5RbYQ2yQ65pbdSW0UgYbt";
    private static final String TWITTER_USER_SECRET = "52NTUrvDGDeIA7RP2nkX49CVEZCpJHvBvOlWPDAVsP4ri";

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<User> doInBackground(String...params) {
        ArrayList<User> res = postCall(params);
        return res;
    }

    private ArrayList<User> postCall (String...params){
        TwitterAPITaskEnum task = TwitterAPITaskEnum.valueOf(params[0]);
        String token = params[1];
        String secret = params[2];
        long userId = Long.valueOf(params[3]);
        //List<Long> ids = new ArrayList<Long>();
        ArrayList<User> users = new ArrayList<>();
        if (task.equals(TwitterAPITaskEnum.CREATE_FRIENDSHIP)){
            ConfigurationBuilder cb = getConfiguration();
            ResponseResult result = createFriendship(userId, cb);
        }
        else if (task.equals(TwitterAPITaskEnum.GET_FRIENDS)){
            ConfigurationBuilder cb = createConfigurationBuilder(token, secret);
            ResponseResult result = getFriends(userId, cb, users);

        }
        else if (task.equals(TwitterAPITaskEnum.GET_FOLLOWERS)){
            ConfigurationBuilder cb = createConfigurationBuilder(token, secret);
            ResponseResult result = getFollowers(userId, cb, users);
        }
        return users;
    }

    private ResponseResult getFollowers (long userId, ConfigurationBuilder cb, List<User> followersIDs){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        IDs ids;
        long cursor = -1L;
        do {
            try {

                ids = twitter.getFollowersIDs(cursor);
                PagableResponseList list = twitter.getFollowersList(userId, cursor);
                followersIDs.addAll(list);
                for(long userID : ids.getIDs()){
                    //followersIDs.add(userID);
                }
            }
            catch(twitter4j.TwitterException ex){
                Log.d(TAG, "Exception: "+ ex.getErrorMessage());
                return ResponseResult.FAILURE;

            }
        } while((cursor = ids.getNextCursor())!=0 );

        return ResponseResult.SUCCESS;
    }
    private ResponseResult getFriends (long userId, ConfigurationBuilder cb, ArrayList<User> friends){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        long cursor = -1L;
        //do {
        try {
            PagableResponseList list = twitter.getFriendsList(userId, cursor);
            friends.addAll(list);
//                friends = twitter.getFriendsList(userId, cursor);

            //cursor = twitter.getFriendsList(userId, cursor).getNextCursor();
//                for(long userID : ids.getIDs()){
//                    friendIDs.add(userID);
//                }
        }
        catch(twitter4j.TwitterException ex){
            Log.d(TAG, "Exception: "+ ex.getErrorMessage());
            return ResponseResult.FAILURE;

        }
        //} while((cursor = ((PagableResponseList<User>)friends).getNextCursor())!=0);

        return ResponseResult.SUCCESS;
    }
    private ResponseResult createFriendship (long userId, ConfigurationBuilder cb){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        try {
            twitter.createFriendship(userId);
        }
        catch(twitter4j.TwitterException ex){
            Log.d(TAG, "Exception: "+ ex.getErrorMessage());
            return ResponseResult.FAILURE;

        }
        return ResponseResult.SUCCESS;
    }
    private ConfigurationBuilder createConfigurationBuilder(String token, String secret){
        Log.d(TAG, token);
        Log.d(TAG, secret);
        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_KEY)
                .setOAuthConsumerSecret(TWITTER_SECRET)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(secret);

        return cb;

    }
    private ConfigurationBuilder getConfiguration(){
        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_KEY)
                .setOAuthConsumerSecret(TWITTER_SECRET)
                .setOAuthAccessToken(TWITTER_USER_KEY)
                .setOAuthAccessTokenSecret(TWITTER_USER_SECRET);

        return cb;

    }
    private ConfigurationBuilder createConfigurationBuilderForFriendship(){

        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_KEY)
                .setOAuthConsumerSecret(TWITTER_SECRET)
                .setOAuthAccessToken(TWITTER_USER_KEY)
                .setOAuthAccessTokenSecret(TWITTER_USER_SECRET);

        return cb;

    }

}
