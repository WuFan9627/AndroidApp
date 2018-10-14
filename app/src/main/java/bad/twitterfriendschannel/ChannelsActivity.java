package bad.twitterfriendschannel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.Subscription;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import bad.twitterfriendschannel.gson.response.Channels;
import bad.twitterfriendschannel.gson.response.ListChannelsRes;
import bad.twitterfriendschannel.gson.response.ListSubscriptionsRes;
import bad.twitterfriendschannel.gson.response.Subscriptions;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ChannelsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    //broker client
    private String brokerURL = "";
    private String dataverseName = "";
    private String userId;
    private String accessToken;
    private List<String> subscriptions;

    private List<String> channels;

    private String accountPref = "";
    private Gson gson;

    //used temporarily
    //private String adminToken = "862145008710819841-sILKYDzXxUCjYxwlxPJY1TF8iDQ0hWo";
    //private String adminSecret = "VwuBTqqe88tpd7qknU5yvCBmL2BaNBxhqO7jPDrj6xfm3";
    //private String adminUserId = "862145008710819841";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gson = new Gson();

        subscriptions = new ArrayList<>();
        channels = new ArrayList<>();
        accountPref = getString(R.string.accountPref);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        brokerURL = getString(R.string.broker_URL);
        dataverseName = getString(R.string.dataverse);
        SharedPreferences preferences = getSharedPreferences(accountPref, MODE_PRIVATE);

        this.userId = preferences.getString("userId", "");
        this.accessToken = preferences.getString("accessToken", "");
        Log.v("userId", userId);
        Log.v("accessToken", accessToken);

        recyclerView = (RecyclerView) findViewById(R.id.recview_channel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String action = intent.getStringExtra("Action");
        if (action.equals("Subscribe")) {
            if (userId != null && accessToken != null && !userId.equals("") && !accessToken.equals("")) {
                Log.v("ListChannels", userId);
                Log.v("accessToken", accessToken);
                Request request = OkHttpUtil.getInstance(getApplicationContext()).listChannels(dataverseName, userId, accessToken);
                OkHttpUtil.getInstance(getApplicationContext()).request(request, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (channels.size() > 0) {
                            channels.clear();
                        }


                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ( res != null && !res.equals("")) {
                                    ListChannelsRes listChannelsRes = gson.fromJson(res, ListChannelsRes.class);
                                    ArrayList<Channels> chan = listChannelsRes.getChannels();
                                    for (int i = 0; i < chan.size(); i++) {
                                        channels.add(chan.get(i).getChannelName());
                                    }
                                    recyclerView.setAdapter(new ChannelsAdapter(channels, ChannelsActivity.this, brokerURL, accessToken, userId));
                                }
                            }
                        });
                    }
                });
            }
        } else if (action.equals("Subscription")) {
            if (userId != null && accessToken != null && !userId.equals("") && !accessToken.equals("")) {
                Log.v("Subscrition", userId);
                Log.v("accessToken", accessToken);
                Request request = OkHttpUtil.getInstance(getApplicationContext()).listSubscriptions(dataverseName, userId, accessToken);
                OkHttpUtil.getInstance(getApplicationContext()).request(request, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson2 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                ListSubscriptionsRes listSubscriptionsRes = gson2.fromJson(res, ListSubscriptionsRes.class);
                                ArrayList<Subscriptions> array = listSubscriptionsRes.getSubscriptions();
                                for (int i = 0; i < array.size(); i++) {
                                    subscriptions.add(array.get(i).getChannel());
                                }

                                recyclerView.setAdapter(new SubscriptionsAdapter(subscriptions, ChannelsActivity.this, brokerURL, accessToken, userId));
                            }
                        });
                    }
                });
            }
        }

    }
}
