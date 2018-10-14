package bad.twitterfriendschannel;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FollowerDetailActivity extends AppCompatActivity {
    private ImageView image;
    private TextView name;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_detail);
        image = (ImageView)findViewById(R.id.follower_image_detail);
        name = (TextView) findViewById(R.id.follower_name_detail);
        description = (TextView) findViewById(R.id.follower_description);
        Intent intent = getIntent();
        String strName = intent.getStringExtra("name");
        String strDes = intent.getStringExtra("description");
        String url = intent.getStringExtra("image");
        Log.v("URL", url);
        Picasso.with(image.getContext()).load(Uri.parse(url)).into(image);
        name.setText(strName);
        description.setText(strDes);
    }
}
