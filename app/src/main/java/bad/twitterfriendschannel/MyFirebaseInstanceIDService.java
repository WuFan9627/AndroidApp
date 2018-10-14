package bad.twitterfriendschannel;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Waleed on 10/5/2016.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstance";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = "";
        try {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.v(TAG, refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer (refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }


}





