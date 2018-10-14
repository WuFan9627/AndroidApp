package bad.twitterfriendschannel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by wufan on 23/05/2018.
 */

public class Main extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Wufan3333"+FirebaseInstanceId.getInstance().getToken());
    }
}
