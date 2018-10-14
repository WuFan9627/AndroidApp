package bad.twitterfriendschannel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import twitter4j.User;

public class FollowerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "FollowersActivity";


    private ArrayList<User> followers = null;
    private FollowerAdapter adapter = null;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //broker client

    private List<String> channels;
    private String twitterPref = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        twitterPref = getString(R.string.twitterPref);

        TwitterAPITask task = new TwitterAPITask() {

            @Override
            protected void onPostExecute(ArrayList<User> users) {
                Log.d(TAG, "Main/Aysn/");
                for (User user : users) {
                    Log.d(TAG, "Name: " + user.getName());
                    Log.d(TAG, "Location: " + user.getLocation());
                }
                followers = users;

                FollowerAdapter adapter = new FollowerAdapter(followers);
                recyclerView.setAdapter(adapter);


                recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView, new ItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(recyclerView.getContext(), FollowerDetailActivity.class);
                        intent.putExtra("name", followers.get(position).getName());
                        intent.putExtra("image", followers.get(position).getBiggerProfileImageURL());
                        intent.putExtra("description", followers.get(position).getDescription());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Intent intent = new Intent(recyclerView.getContext(), FollowerDetailActivity.class);
                        intent.putExtra("name", followers.get(position).getName());
                        intent.putExtra("image", followers.get(position).getBiggerProfileImageURL());
                        intent.putExtra("description", followers.get(position).getDescription());
                        startActivity(intent);
                    }
                }));
            }
        };
        SharedPreferences preferences = getSharedPreferences(twitterPref, MODE_PRIVATE);
        String token = preferences.getString("token", "");
        String secret = preferences.getString("secret", "");
        Long twUserId = preferences.getLong("twitterUserId", 0);
        task.execute(TwitterAPITaskEnum.GET_FOLLOWERS.name(), token, secret, String.valueOf(twUserId));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_follower);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_setting);
        }

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_subscribe) {
            //Toast.makeText(this, "subscrible", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(drawerLayout.getContext(), ChannelsActivity.class);
            intent.putExtra("Action", "Subscribe");
            startActivity(intent);
        } else if (id == R.id.nav_followees) {
            Intent intent = new Intent(drawerLayout.getContext(), MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            finish();
        } else if (id == R.id.nav_subscription) {
            Intent intent = new Intent(drawerLayout.getContext(), SubscriptionActivity.class);
            intent.putExtra("Action", "Subscription");
            startActivity(intent);
        }
//        } else if (id == R.id.nav_message) {
//            Intent intent = new Intent(drawerLayout.getContext(), MessageActivity.class);
//            startActivity(intent);
//        }
        return false;
    }

}
