package bad.twitterfriendschannel;

import android.*;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.NotificationRes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private List<LatLng> markers = new ArrayList<LatLng>();
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Button messageBtn;
    private FloatingActionButton myLocationBtn;
    private HashMap<String, Point> starbucks = new HashMap<>();
    private HashMap<String, Point> friends = new HashMap<>();
    private Marker Starbuck;
/*

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        IntentFilter iff= new IntentFilter("Notification-Mes");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, iff);
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            NotificationRes notification = (NotificationRes)intent.getSerializableExtra("notification");

            //starbucks = notification.getStarbucks();
            friends = notification.getFriends();



            for (String key : friends.keySet()) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.valueOf(friends.get(key).getLatitude()), Double.valueOf(friends.get(key).getLongtitude())))
                        .title(key)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).showInfoWindow();
            }

            LatLng Steven = new LatLng(40.775251, -125.450631);
            mMap.addMarker(new MarkerOptions()
                    .position(Steven)
                    .title("Your location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Steven));
            //Log.d("receiver", "Got message: " + message);
            //Toast.makeText(MapsActivity.this, "Get Message Already", Toast.LENGTH_SHORT).show();
        }
    }; */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        setSupportActionBar(toolbar);
        messageBtn = (Button) findViewById(R.id.maps_btn_message);
        myLocationBtn = (FloatingActionButton) findViewById(R.id.maps_location_btn);
        myLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng Steven = new LatLng(32.775251, -117.450631);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Steven));
            }
        });
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/messages/compose";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


//        myLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_COARSE); //
//                criteria.setAltitudeRequired(false); //
//                criteria.setBearingRequired(false); //
//                criteria.setCostAllowed(false); //
//                criteria.setPowerRequirement(Criteria.POWER_LOW);
//
//                LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//                String provider=manager.getBestProvider(criteria, true);
//                Location location = manager.getLastKnownLocation(pro)
//                LatLng latLng = new LatLng(mMap.getMyLocation().getLongitude(), mMap.getMyLocation().getLatitude());
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
//                mMap.animateCamera(cameraUpdate);
//            }
//        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
              //  new IntentFilter("Notification-Mes"));Fan



        drawerLayout = (DrawerLayout) findViewById(R.id.activity_maps);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_setting);
        }

        navView = (NavigationView) findViewById(R.id.maps_nav_view);
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
//            Intent intent = new Intent(drawerLayout.getContext(), SubscribeActivity.class);
//            intent.putExtra("Action", "Subscribe");
//            startActivity(intent);

            Intent intent = new Intent(drawerLayout.getContext(), ChannelsActivity.class);
            intent.putExtra("Action", "Subscribe");
            startActivity(intent);
        } else if (id == R.id.nav_followees) {
            Intent intent = new Intent(drawerLayout.getContext(), FollowerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            finish();
        } else if (id == R.id.nav_subscription) {
            Intent intent = new Intent(drawerLayout.getContext(), ChannelsActivity.class);
            intent.putExtra("Action", "Subscription");
            startActivity(intent);
        }
//        } else if (id == R.id.nav_message) {
//            Intent intent = new Intent(drawerLayout.getContext(), MessageActivity.class);
//            startActivity(intent);
//        }
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
//        LatLng starbucks = new LatLng(37.7838092, -122.4523144);
//        LatLng YoungSeok = new LatLng(37.8, -122.3);
//        LatLng James = new LatLng(37.9, -122.2);
//        LatLng Shuvo = new LatLng(37.95, -122.15);
        LatLng Steven = new LatLng(32.9195683,-117.1589951);

        mMap.addMarker(new MarkerOptions()
                .position(Steven)
                .title("Your location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( Steven, 14f));



        /*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(S);
        markerOptions.title("Starbucks");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_starbucks));
        googleMap.addMarker(markerOptions);*/

    }
    public boolean onMarkerClick(Marker marker) {

        LatLng position = marker.getPosition();
        boolean found = false;
        int index = 0;
        for (LatLng ll : markers){
            if (ll.toString().equalsIgnoreCase(position.toString())){
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                found =true;
                break;
            }
            index++;
        }
        if (found){
            markers.remove(index);
            return true;
        }
//        markers.add(marker.getPosition());
//        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//        LatLng YoungSeok = new LatLng(37.8, -122.3);
//        mMap.addMarker(new MarkerOptions().position(YoungSeok).title(
//                "Starbucks")).showInfoWindow();
        return false;
    }





}
