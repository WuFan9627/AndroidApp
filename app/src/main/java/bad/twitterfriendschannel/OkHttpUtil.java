package bad.twitterfriendschannel;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bad.twitterfriendschannel.gson.request.GCMRegistrationReq;
import bad.twitterfriendschannel.gson.request.GetResultsReq;
import bad.twitterfriendschannel.gson.request.ListChannelsReq;
import bad.twitterfriendschannel.gson.request.ListSubscriptionsReq;
import bad.twitterfriendschannel.gson.request.LoginReq;
import bad.twitterfriendschannel.gson.request.LogoutReq;
import bad.twitterfriendschannel.gson.request.RegisterReq;
import bad.twitterfriendschannel.gson.request.SubscribeReq;
import bad.twitterfriendschannel.gson.request.UnsubscribeReq;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by Leo on 5/4/2017.
 */

public class OkHttpUtil {

    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "OkHttpUtil";
    public final static String Preference_TAG = "Badclient.Preference";
    private Gson gson;

    private String brokerUrl = "";
    private String dataverseName = null;
    private String userName, email, password, userId, accessToken;
    private String gcmRegistrationToken = null;
    private String platform = "android";
    private Context context;
    private TextView mTextViewResult;

    private OkHttpUtil(Context context) {
        this.context = context;
        brokerUrl = context.getString(R.string.broker_URL);
        gson = new Gson();
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil(context);
                }
            }
        }
        return mInstance;
    }

    public Call request(Request request, Callback callback) {
        mOkHttpClient.newCall(request).enqueue(callback);
        return null;
    }

//    public Request sendToFirebase() {
//        JSONObject postData = new JSONObject();
//        JSONObject notification = new JSONObject();
//        JSONObject data = new JSONObject();
//        try {
//            notification.put("title", "Test");
//            notification.put("text", "This is a test");
//
//            data.put("value", "Hello");
//
//
//            postData.put("to", "f8LL3rF-0ZU:APA91bFWBcDopa_y8fJkAbBqcuLCqJ0a1R6jAUQOeruINXAf-2WbdbJqNZw8O7jNsPaBM1T-z3IkKatiIXte0O--4vg_Nn7RwYYGbeh6cCDUaKT_OxYpqEzb56NbqrmTtUZQLSAeTAKG");
//            postData.put("notification", notification);
//            postData.put("data", data);
//            postData.put("priority", "high");
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON, postData.toString());
//        return new Request.Builder()
//                .url("https://fcm.googleapis.com/fcm/send")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "key=AAAAD_tR9oM:APA91bEqlZ8nL7Z4gmYCB_iRI4dFqSEZvGENik6XESexoN9Wo82tTfvH2pjEfpAzMro5vplnJv9UC-PaDLf5nb_LyrF5qlaAXbIy_S_XKs9YQiWnsXgCa8kwZ5_OTnnFjiLE3VT1cO3F")
//                .post(body)
//                .build();
//    };

    public Request register(String dataverseName, String userName, String password, String email) {
        RegisterReq register = new RegisterReq(dataverseName, userName, password,email);
        String postData = gson.toJson(register, RegisterReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/register")
                .post(body)
                .build();
    }

    public Request login(String dataverseName, String userName, String password) {
        LoginReq login = new LoginReq(dataverseName, userName, password, platform);
        String postData = gson.toJson(login, LoginReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/login")
                .post(body)
                .build();
    }

    public Request logout(String dataverseName, String userId, String accessToken) {
        LogoutReq logout = new LogoutReq(dataverseName, userId, accessToken);
        String postData = gson.toJson(logout, LogoutReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/logout")
                .post(body)
                .build();
    }

    public Request subscribe(String dataverseName, String userId, String channelName,  String accessToken,ArrayList<String> parameters) {

        SubscribeReq subscribeReq = new SubscribeReq(dataverseName, userId, accessToken, channelName, parameters);
        String postData = gson.toJson(subscribeReq, SubscribeReq.class);

        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/subscribe")
                .post(body)
                .build();
    }

    public Request unSubscribe (String dataverseName, String userId, String accessToken, String userSubscriptionId) {

        UnsubscribeReq unsubscribeReq = new UnsubscribeReq(dataverseName, userId, accessToken, userSubscriptionId);
        String postData = gson.toJson(unsubscribeReq, UnsubscribeReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/unsubscribe")
                .post(body)
                .build();
    }

    public Request listChannels (String dataverseName, String userId, String accessToken) {

        ListChannelsReq listChannelsReq = new ListChannelsReq(dataverseName, userId, accessToken);
        String postData = gson.toJson(listChannelsReq, ListChannelsReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/listchannels")
                .post(body)
                .build();
    }
    public Request listSubscriptions (String dataverseName, String userId, String accessToken) {

        ListSubscriptionsReq listSubscriptionsReq = new ListSubscriptionsReq(dataverseName, userId, accessToken);
        String postData = gson.toJson(listSubscriptionsReq, ListSubscriptionsReq.class);
        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/listsubscriptions")
                .post(body)
                .build();
    }

    public Request setFCMRegistrationToken(String dataverseName, String userId, String accessToken, String token) {
        GCMRegistrationReq gcmRegistrationReq = new GCMRegistrationReq(dataverseName, userId, accessToken, token);
        String postData = gson.toJson(gcmRegistrationReq, GCMRegistrationReq.class);

        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/gcmregistration")
                .post(body)
                .build();
    }

    public Request fetchResults(String dataverseName, String userId, String accessToken, String channelName, String userSubscriptionId, String channelExecutionTime) {

        GetResultsReq getResultsReq = new GetResultsReq(dataverseName, userId, accessToken, channelName, userSubscriptionId, channelExecutionTime);
        String postData = gson.toJson(getResultsReq, GetResultsReq.class);

        RequestBody body = RequestBody.create(JSON, postData);
        return new Request.Builder()
                .url(brokerUrl + "/getresults")
                .post(body)
                .build();
    }
}
