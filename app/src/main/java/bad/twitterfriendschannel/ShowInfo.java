package bad.twitterfriendschannel;

/**
 * Created by wufan on 2018/9/30.
 */

import android.app.Dialog;
import android.net.Uri;
import android.support.v7.widget.Toolbar;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.litepal.NotificationRes;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by wufan on 2018/9/30.
 */
public class ShowInfo extends AppCompatActivity implements OnMapReadyCallback {
    private HashMap<String, Point> friends = new HashMap<>();
    private HashMap<String, Point> starbucks = new HashMap<>();
    //Point Star = new Point(32.8732152,-117.229295);
    private Marker Starbuck;
    private GoogleMap mMap;
    String TAG = "wufan:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);
        MapFragment myfra = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment2);
        myfra.getMapAsync(this);

        Toast.makeText(this, "Your friend is nearby!", Toast.LENGTH_SHORT).show();
        NotificationRes notification = (NotificationRes) getIntent().getSerializableExtra("notification");
        friends = notification.getFriends();
        starbucks = notification.getStarbucks();


    }
    private void gotoLocation(HashMap<String,Point> friends,HashMap<String,Point> starbucks){
        String key = friends.keySet().iterator().next();
        Point S = friends.get(key);
        LatLng L = new LatLng(S.getLatitude(),S.getLongtitude());
        System.out.println(L);

        mMap.addMarker(new MarkerOptions()
                .position(L)
                .title(key)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( L, 14f));

        String key_2 = starbucks.keySet().iterator().next();
        Point S_2 = starbucks.get(key_2);
        LatLng L_2 = new LatLng(S_2.getLatitude(),S_2.getLongtitude());
        System.out.println(L_2);

        mMap.addMarker(new MarkerOptions()
                .position(L_2)
                .title("Starbucks")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_starbucks)));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gotoLocation(friends,starbucks);

    }


}