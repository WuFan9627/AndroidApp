package bad.twitterfriendschannel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.Subscription;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import bad.twitterfriendschannel.gson.response.SubscribeRes;
import bad.twitterfriendschannel.gson.response.UnsubscribeRes;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.IDs;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Leo on 3/27/2017.
 */

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ViewHolder> {
    private List<String> channels;
    private HashSet<String> subscriptions;
    private Context context;
    private String dataverseName = "";
    private String userId = "";
    private String accessToken = "";
    private String brokerURL;
    private Gson gson;
    private final String TAG = "Channel Adapter";
    private MyBAD broker = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        Switch channelSwitch;
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            channelSwitch = (Switch) view.findViewById(R.id.switch_channel);
            textView = (TextView) view.findViewById(R.id.textView_channel);

        }


    }
    ChannelsAdapter(List<String> list, Context context, String brokerURL, String accessToken, String userId) {
        this.context = context;
        this.channels = list;
        this.subscriptions = new HashSet<>();
        this.brokerURL = brokerURL;
        this.accessToken = accessToken;
        this.userId = userId;
        this.dataverseName = context.getString(R.string.dataverse);
        gson = new Gson();
        List<Subscription> subs = DataSupport.where("userId = ?", userId).find(Subscription.class);
        for (Subscription sub : subs) {
            this.subscriptions.add(sub.getChannelName());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
        ChannelsAdapter.ViewHolder holder = new ChannelsAdapter.ViewHolder(view);

        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String name = channels.get(position);
        holder.textView.setText(name);
        if (subscriptions.contains(name)) {
            holder.channelSwitch.setChecked(true);
        } else {
            holder.channelSwitch.setChecked(false);
        }
        holder.channelSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences preferences = context.getSharedPreferences("TwitterPref", 0);
                    String twitteruserId2= Long.toString(preferences.getLong("twitterUserId", 0));
                    final ArrayList<String> parameter2 = new ArrayList<String>();
                    parameter2.add(twitteruserId2);
                    Request request = OkHttpUtil.getInstance(context).subscribe(dataverseName, userId, accessToken, name, parameter2);
                    OkHttpUtil.getInstance(context).request(request, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            Log.v(TAG, e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String res = response.body().string();

                            boolean success = buttonView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (res != null && !res.equals("")) {
                                        SubscribeRes subscribeRes = gson.fromJson(res, SubscribeRes.class);
                                        if (subscribeRes.getStatus().equals("success")) {
                                            Subscription subscription = new Subscription();
                                            subscription.setUserId(userId);
                                            subscription.setChannelName(name);
                                            subscription.setTimestamp(subscribeRes.getTimestamp());
                                            subscription.setUserSubscriptionId(subscribeRes.getUserSubscriptionId());
                                            subscription.save();
                                        }
                                        Toast.makeText(buttonView.getContext(), "Subscribe Success", Toast.LENGTH_SHORT).show();
                                        /*feedrecord
                                        final String adminToken = "981650490533363712-iSLyBGQD6aZYYEQwwqgOljSNrXyttU2";
                                        final String adminSecret = "S2KtmLNFWYINb8sMc39AQzt00PbVeemblhTzmm7GAzDZ6";*/
                                        //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                        //SharedPreferences pref = LoginActivity.class.getSharedPreferences(twitterPref, MODE_PRIVATE);
                                        //Long twitteruserId = pref.getLong("twitterUserId", -1);
                                        SharedPreferences preferences = context.getSharedPreferences("TwitterPref", 0);
                                        Long twitteruserId= preferences.getLong("twitterUserId", 0);
                                        SharedPreferences preferences2 = context.getSharedPreferences("AccountPref", 0);
                                        String accessToken = preferences2.getString("accessToken","");
                                        //Long twitteruserId = 990302026805342209L;

                                        /*TwitterAPITask task = new TwitterAPITask(); {

                                            protected void onPostExecute(ArrayList<User> users) {

                                                for (User u : users) {
                                                    TwitterAPITask followTask = new TwitterAPITask();
                                                    followTask.execute(TwitterAPITaskEnum.CREATE_FRIENDSHIP.name(), adminToken, adminSecret, twitteruserId[0]);
                                                }
                                            }


                                        };
                                        task.execute(TwitterAPITaskEnum.CREATE_FRIENDSHIP.name(), adminToken, adminSecret, String.valueOf(twitteruserId)); */
                                        ConfigurationBuilder cb = new ConfigurationBuilder();
                                        cb.setDebugEnabled(true)
                                                .setOAuthConsumerKey("wEyhnNVay68AOJKD7rCxEQqzY")
                                                .setOAuthConsumerSecret("lk9LoIhJORjdy8hxx4h8hg76E0YbEjCsBlwklTigMT6FQTP1MQ")
                                                .setOAuthAccessToken("981650490533363712-Juwji0emeT5RbYQ2yQ65pbdSW0UgYbt")
                                                .setOAuthAccessTokenSecret("52NTUrvDGDeIA7RP2nkX49CVEZCpJHvBvOlWPDAVsP4ri");
                                        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
                                        twitter4j.Twitter twitter = twitterFactory.getInstance();

                                        if (android.os.Build.VERSION.SDK_INT > 9)
                                        {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                        }
                                        try {
                                            twitter.createFriendship(twitteruserId);
                                        } catch (TwitterException e) {
                                            e.printStackTrace();
                                        }
                                        long cursor = -1L;
                                        JSONArray array = new JSONArray();


                                        //ArrayList<String> temp = new ArrayList<String>();
                                        try {
                                            IDs list = twitter.getFriendsIDs(twitteruserId, cursor);

                                            for (Long id: list.getIDs()){
                                                try {
                                                    array.put(String.valueOf(id));//twitterid
                                                    twitter.createFriendship(id);
                                                } catch (TwitterException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (TwitterException e) {
                                            e.printStackTrace();
                                        }
                                        JSONArray itemArray = new JSONArray();
                                        JSONObject item = new JSONObject();
                                        try {
                                            item.put("user_id", userId);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            item.put("twitter_id", String.valueOf(twitteruserId)); //item object
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            item.put("followers", array);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //itemArray.put(item);

                                        broker = new MyBAD(context.getApplicationContext(), brokerURL, dataverseName,userId,accessToken);
                                        broker.feedRecords(dataverseName, item);




                                        //---------------------------edited-------------------------------

                                    } else {
                                        Toast.makeText(buttonView.getContext(), "Subscribe Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Unsubscribe");
                    alertDialog.setMessage("Unsubscribe this channel?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dialog.dismiss();
                                    Handler handler = new Handler();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<Subscription> list = DataSupport.findAll(Subscription.class);
                                            Cursor c = DataSupport.findBySQL("select * from subscription where channelname = ? and userid = ?", name, userId);
                                            if (c.moveToFirst()) {
                                                int index = c.getColumnIndex("usersubscriptionid");
                                                String subId = c.getString(index);
                                                //String subId = c.getString(c.getColumnIndex("userSubscriptionId"));
                                                Request resquest = OkHttpUtil.getInstance(context).unSubscribe(dataverseName, userId, accessToken, subId);
                                                OkHttpUtil.getInstance(context).request(resquest, new Callback() {
                                                    @Override
                                                    public void onFailure(Call call, IOException e) {

                                                    }

                                                    @Override
                                                    public void onResponse(Call call, Response response) throws IOException {
                                                        String res = response.body().string();
                                                        final UnsubscribeRes unsubscribeRes = gson.fromJson(res, UnsubscribeRes.class);

//                                                        if (unsubscribeRes.getStatus().equals("success")) {
//                                                            Toast.makeText(context, "Unsubscribe Success", Toast.LENGTH_SHORT).show();
//                                                        } else {
//                                                            Toast.makeText(context, unsubscribeRes.getError(), Toast.LENGTH_SHORT).show();
//
//                                                        }
                                                        Log.v("Response", res);
                                                    }
                                                });
                                                String index2 = c.getString(0);
                                                DataSupport.deleteAll(Subscription  .class, "id = ?", index2);
                                            }
                                        }
                                    });
//                                    buttonView.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//
//                                        }
//                                    });
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    holder.channelSwitch.setChecked(true);
                                }
                            });
                    alertDialog.show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return channels.size();
    }






    class MyBAD extends BADAndroidClient {
        private String brokerUrl ="http://10.0.2.2:8989";
        private String dataverseName = null;
        private String  userId, accessToken;
        MyBAD(Context context, String brokerUrl, String dataverseName, String userId,String accessToken) {
            super(context, brokerUrl, dataverseName, userId,accessToken);
        }

        @Override
        public void onRegistration(JSONObject result) {

        }

        @Override
        public void onLogin(JSONObject result) {

        }

        @Override
        public void onSubscription(JSONObject result) {
            if (result != null) {
                try {
                    Log.v("ResultsRetrieved", result.toString());
                    if (result.getString("status").equals("success")) {
                        Toast.makeText(context.getApplicationContext(), "Subscribe successful", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = context.getSharedPreferences("subscribleSession", MODE_PRIVATE).edit();
                        editor.putString("userSubscriptionId", result.getString("userSubscriptionId"));
                        editor.apply();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Subscribe Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onNewResultsRetrieved(JSONObject result) {

        }

        @Override
        public void onListChannels(JSONObject result) {
            return;
        }
    }











}
