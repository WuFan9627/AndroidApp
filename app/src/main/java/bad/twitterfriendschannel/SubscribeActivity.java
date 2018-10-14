package bad.twitterfriendschannel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import twitter4j.User;

public class SubscribeActivity extends AppCompatActivity {

    public Switch subscribeSwitch;
    public TextView subscribeTextView;

    private MyBADClient broker = null;
    private String brokerURL = "http://10.0.2.2:8989";
    private String dataverseName = "Starbucks";
    private String adminToken = "981650490533363712-Juwji0emeT5RbYQ2yQ65pbdSW0UgYbt";//"862145008710819841-sILKYDzXxUCjYxwlxPJY1TF8iDQ0hWo";
    private String adminSecret ="52NTUrvDGDeIA7RP2nkX49CVEZCpJHvBvOlWPDAVsP4ri";
                              //"VwuBTqqe88tpd7qknU5yvCBmL2BaNBxhqO7jPDrj6xfm3";
    private String adminUserId = "862145008710819841";
    private static final String TAG = "SubscribeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        subscribeSwitch = (Switch) findViewById(R.id.switch_channel);
        //subscribeTextView = (TextView) findViewById(R.id.textView_channel);

        /*subscribeSwitch2 = (Switch) findViewById(R.id.switch_subscribe2);
        subscribeTextView2 = (TextView) findViewById(R.id.textView_subscribe2); */

        brokerURL = this.getString(R.string.broker_URL);
        //broker = new MyBADClient(getApplicationContext(), brokerURL, dataverseName);

        /*boolean checked = getSharedPreferences("ChannelSubscription", MODE_PRIVATE).getBoolean("Starbucks", false);
        if (checked) {
            subscribeSwitch.setChecked(true);
        } else {
            subscribeSwitch.setChecked(false);
        }*/


        subscribeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences preferences = getSharedPreferences("twitterSession", MODE_PRIVATE);
                final String token = preferences.getString("token", "");
                final String secret = preferences.getString("secret", "");
                final Long twUserId = preferences.getLong("twitterUserId", 0);
                if (isChecked) {
                    final TwitterAPITask followTask = new TwitterAPITask();
                    TwitterAPITask task = new TwitterAPITask() {
                        @Override
                        protected void onPostExecute(ArrayList<User> users) {
                            JSONObject postData = new JSONObject();
                            JSONArray array = new JSONArray();

                            array.put("0002");
                            try {
                                postData.put("user_id", String.valueOf(twUserId));
                                postData.put("followers", array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject l = new JSONObject();
                            try {
                                l.put("user_id", adminUserId);
                                JSONArray a = new JSONArray();
                                a.put(31.34234);
                                a.put(-115.21312);
                                l.put("location", a);
                                l.put("timeStamp", "2017-05-19T03:51:39.110Z");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            for (User u : users) {
                                TwitterAPITask followTask = new TwitterAPITask();
                                followTask.execute(TwitterAPITaskEnum.CREATE_FRIENDSHIP.name(), adminToken, adminSecret, String.valueOf(u.getId()));
                            }

                            //broker.subscribe(dataverseName, array);
                           // broker.feedRecords(dataverseName, postData);
                            SharedPreferences.Editor editor = getSharedPreferences("ChannelSubscription", MODE_PRIVATE).edit();
                            editor.putBoolean("Starbucks", true);
                            editor.apply();
                        }
                    };

                    task.execute(TwitterAPITaskEnum.GET_FOLLOWERS.name(), token, secret, String.valueOf(twUserId));
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SubscribeActivity.this).create();
                    alertDialog.setTitle("Unsubscribe");
                    alertDialog.setMessage("Unsubscribe this channel?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    SharedPreferences pref = getSharedPreferences("subscribleSession", MODE_PRIVATE);
                                    String userSubscriptionId = pref.getString("userSubscriptionId", "");
                                    broker.unSubscribe(userSubscriptionId);
                                    Toast.makeText(getApplicationContext(), "Unsubscribe Success!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    subscribeSwitch.setChecked(true);
                                }
                            });
                    alertDialog.show();
                    SharedPreferences.Editor editor = getSharedPreferences("ChannelSubscription", MODE_PRIVATE).edit();
                    editor.putBoolean("Starbucks", false);
                    editor.apply();
                }
            }
        });

        /*subscribeSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    final TwitterAPITask followTask = new TwitterAPITask();
                    TwitterAPITask task = new TwitterAPITask() {
                        @Override
                        protected void onPostExecute(ArrayList<User> users) {
                            JSONObject postData = new JSONObject();
                            JSONArray array = new JSONArray();

                            array.put("0001");
                            try {
                                postData.put("user_id", String.valueOf(twUserId));
                                postData.put("followers", array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (User u : users) {
                                TwitterAPITask followTask = new TwitterAPITask();
                                followTask.execute(TwitterAPITaskEnum.CREATE_FRIENDSHIP.name(), adminToken, adminSecret, String.valueOf(u.getId()));
                            }

                            //broker.subscribe("Starbucks3m", array);
                            broker.feedRecords(dataverseName, postData);
                            SharedPreferences.Editor editor = getSharedPreferences("ChannelSubscription", MODE_PRIVATE).edit();
                            editor.putBoolean("Starbucks2", true);
                            editor.apply();
                        }
                    };

                    task.execute(TwitterAPITaskEnum.GET_FOLLOWERS.name(), token, secret, String.valueOf(twUserId));
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SubscribeActivity.this).create();
                    alertDialog.setTitle("Unsubscribe");
                    alertDialog.setMessage("Unsubscribe this xiongxiong?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    SharedPreferences pref = getSharedPreferences("subscribleSession", MODE_PRIVATE);
                                    String userSubscriptionId = pref.getString("userSubscriptionId","");
                                    broker.unSubscribe(userSubscriptionId);
                                    Toast.makeText(getApplicationContext(), "Unsubscribe Success!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    subscribeSwitch.setChecked(true);
                                }
                            });
                    alertDialog.show();
                    SharedPreferences.Editor editor = getSharedPreferences("ChannelSubscription", MODE_PRIVATE).edit();
                    editor.putBoolean("Starbucks2", false);
                    editor.apply();
                }
            }
        }); */


    }


       class MyBADClient extends BADAndroidClient {

        MyBADClient(Context context, String brokerUrl, String dataverseName, String userid, String accessToken) {
            super(context, brokerUrl, dataverseName,userid,accessToken);
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
                        Toast.makeText(getApplicationContext(), "Subscribe successful", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("subscribleSession", MODE_PRIVATE).edit();
                        editor.putString("userSubscriptionId", result.getString("userSubscriptionId"));
                        editor.apply();
                    } else {
                        Toast.makeText(getApplicationContext(), "Subscribe Failed", Toast.LENGTH_SHORT).show();
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