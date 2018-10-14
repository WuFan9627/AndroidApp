package bad.twitterfriendschannel;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.*;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.NotificationRes;

import bad.twitterfriendschannel.gson.response.GetResultsRes;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessaging";
    private String dataverseName = "Starbucks";
    private NotificationRes notification = new NotificationRes();
    private Gson gson;
    private String accountPref = "";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        gson = new Gson();
        Map<String, String> result = remoteMessage.getData();
        //JSONObject message = new JSONObject(remoteMessage.getNotification().getBody());
        String channelName = result.get("channelName");
        String userSubscriptionId = result.get("userSubscriptionId");
        String channelExecutionTime = result.get("channelExecutionTime");

        accountPref = getString(R.string.accountPref);
        dataverseName = getString(R.string.dataverse);
        SharedPreferences preferences = getSharedPreferences(accountPref, MODE_PRIVATE);
        String userId = preferences.getString("userId", "");
        String token = preferences.getString("accessToken", "");

        Request request = OkHttpUtil.getInstance(getApplicationContext()).fetchResults(dataverseName, userId, token, channelName, userSubscriptionId, channelExecutionTime);
        OkHttpUtil.getInstance(getApplicationContext()).request(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                parseResults(res, notification);
                sendNotification("New Message", notification);
                //LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                //Intent intent = new Intent("Notification-Mes");
                //intent.putExtra("notification", notification);
                //broadcaster.sendBroadcast(intent);
                //newly deleted
            }
        });




//        sendNotification("New Message", null);
//        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
//        Intent intent = new Intent("Notification-Mes");
//        //intent.putExtra("message", result[0].toString());
//        broadcaster.sendBroadcast(intent);

        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //Log.d(TAG, "Notificaiton Data: " + remoteMessage.getData());


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, NotificationRes notification) {
        Intent intent = new Intent(this,ShowInfo.class);//LoginActivity, this, NotificationBroadcastReceiver.class
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("notification", notification);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification2 (String messageBody, Map<String, String> data) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Bundle bundle = new Bundle();
//        for (Map.Entry<String, String> e: data.entrySet())
//            bundle.putString(e.getKey(), e.getValue());

        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void parseResults(String results, NotificationRes notification) {
        if (results == null || results.equals("")) {
            Log.v("Parse Notification", "Null results");
            return;
        }

        try {
            JSONObject res = new JSONObject(results);
            JSONArray data = res.getJSONArray("results");
            for (int i = 0; i < data.length(); i++) {
                JSONObject near = data.getJSONObject(i).getJSONObject("result");
                JSONArray sb = near.getJSONArray("sb");
                JSONArray coor = near.getJSONObject("t").getJSONObject("place").getJSONObject("bounding_box").getJSONArray("coordinates");
                JSONObject user_name = near.getJSONObject("t").getJSONObject("user");

                String name_ = user_name.getString("screen_name");


                if (sb.length()!=0){

                        JSONArray s = sb.getJSONArray(0);
                        Point p = new Point(s.getDouble(0), s.getDouble(1));
                        notification.getStarbucks().put("starbucks", p);


                }

                JSONArray place = coor.optJSONArray(0).optJSONArray(0);
                Point u = new Point(place.getDouble(0), place.getDouble(1));
                notification.getFriends().put(name_, u);


                //JSONArray star = near.getJSONArray("starbucks");
                //JSONArray fri = near.getJSONArray("dumb");//dumb.f.place.bounding_box.coordinates[0][0][0] edited! it was dumb before, now result.t.place.bounding_box.coordinates[0][0][0]
                /*JSONArray t  = temp.getJSONArray("t");
                JSONArray sb  = temp.getJSONArray("sb");
                JSONArray place;
                place = t.getJSONArray("place");
                JSONArray u  = t.getJSONArray("user");
                JSONArray bounding_box  = place.getJSONArray("bounding_box");
                ArrayList co  = bounding_box.getJSONArray("coordinates");
                Point p = new Point(co[0][0][0].getDouble(0), co[0][0][0].getDouble(1));
                notification.getFriends().put(u.getString("screen_name"), new Point(co[0][0][0].getDouble(0), co[0][0][0].getDouble(1)));
                */

                /*for (int j = 0; j < star.length(); j++) {
                    JSONObject s = star.getJSONObject(j);
                    String id = s.getString("id");
                    // if (!starbucks.containsKey(s.getString("id"))) {
                    JSONArray l = s.getJSONArray("location");
                    Point p = new Point(l.getDouble(0), l.getDouble(1));
                    notification.getStarbucks().put(s.getString("id"), p);
                    // }
                }*/

                /* good one:
                for (int j = 0; j < fri.length(); j++) {
                    JSONObject f = fri.getJSONObject(j);
                    if (!notification.getFriends().containsKey(f.getString("place"))) { //name
                        JSONArray l = f.getJSONArray("place");//location

                        notification.getFriends().put(f.getString("bounding_box"), new Point(l.getDouble(0), l.getDouble(1)));
                    }
                }

                */
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
