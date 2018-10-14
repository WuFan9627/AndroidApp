package bad.twitterfriendschannel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.Subscription;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubscriptionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public final static String Preference_TAG = "Badclient.Preference";

    //broker client
    private SubscriptionActivity.MyBADClient broker = null;
    private String brokerURL = "";
    private String dataverseName = "Starbucks";
    private String userId;
    private String accessToken;
    private List<String> subscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        subscriptions = new ArrayList<>();

        brokerURL = getString(R.string.broker_URL);
        SharedPreferences preferences = getSharedPreferences("brokerSession", MODE_PRIVATE);
        this.userId = preferences.getString("userId", "");
        this.accessToken = preferences.getString("accessToken", "");
        Log.v("userId", userId);
        Log.v("accessToken", accessToken);

        recyclerView = (RecyclerView) findViewById(R.id.recview_subscription);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String action = intent.getStringExtra("Action");
        Log.v("I am here", "before new broker");
        broker = new SubscriptionActivity.MyBADClient(getApplicationContext(), brokerURL, dataverseName,userId,accessToken);

        if (action.equals("Subscription")) {
            if (userId != null && accessToken != null && !userId.equals("") && !accessToken.equals("")) {
                Log.v("Subscrition", userId);
                Log.v("accessToken", accessToken);

                broker.listSubscriptions(dataverseName, userId, accessToken);

            }
        }
    }
    class MyBADClient extends BADAndroidClient {

        MyBADClient(Context context, String brokerUrl, String dataverseName, String userid,String accessToken) {
            super(context, brokerUrl, dataverseName, userid,accessToken);
        }

        @Override
        public void onRegistration(JSONObject result) {
            if (result != null)
                Toast.makeText(SubscriptionActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SubscriptionActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLogin(JSONObject result) {
            if (result != null) {
                Toast.makeText(SubscriptionActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                try {
                    if (result.getString("status").equals("success")) {
                        Toast.makeText(SubscriptionActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(SubscriptionActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException jex) {
                    jex.printStackTrace();
                }
            }
            else {
                Toast.makeText(SubscriptionActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onSubscription(JSONObject result) {
            if (result != null) {
                try {
                    Log.v("Subscriptions", result.toString());
                    JSONArray array = result.getJSONArray("subscriptions");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject sub = array.getJSONObject(i);
                        Log.v("SubscriptionsObject", sub.toString());
                        subscriptions.add(sub.getString("channelName"));
                    }

                    recyclerView.setAdapter(new SubscriptionsAdapter(subscriptions, SubscriptionActivity.this, brokerURL, accessToken, userId));
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
        }
    }

}
