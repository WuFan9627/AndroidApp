package bad.twitterfriendschannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        Button sendMessage = (Button) findViewById(R.id.btn_sendNotification);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtil client = OkHttpUtil.getInstance(getApplicationContext());
//                //Request request = client.setFCMRegistrationToken("asdf");
//                client.request(request, new Callback() {
//                    @Override
//                    public void onFailure(okhttp3.Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                        Log.v("SendMessage Response", response.toString());
//                        Log.v("SendMessage Body", response.body().string());
//                    }
//                });
            }
        });
    }
}
