package info.androidhive.firebase;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    SharedPreferences sharedPref;
    Intent dirIntent;

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Double lon;
    Double lat;
    String added_location;
    TextView TVnav_name;

    /*@Override
    public void onItemSelected(NotificationItem movie) {
        Log.d("Tap Details",movie.getBloodgp().toString());
        Log.d("Tap Details",movie.getName().toString());
    }*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("EXIT")) {
            Boolean x = getIntent().getBooleanExtra("EXIT", false);
            if (x) {
                Log.d("askjsakj","finissssssssssssssssssssssh");
                finish();
                x = false;
            }
        }


        //Location purpose
        lon = null;
        lat = null;
        mLastLocation = null;
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Initially Load MainFFragment xml
        setContentView(R.layout.activity_main);
        Fragment fragment = new MainFragment();
        //Apply fragment
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header =navigationView.getHeaderView(0);
        TVnav_name= (TextView)header.findViewById(R.id.nav_name);
        navigationView.setNavigationItemSelectedListener(this);
        TVnav_name.setText("Ehab");
    }




    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {


    }



   /* @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

      else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
         }*/
   @Override
   public void onBackPressed() {
       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       if (drawer.isDrawerOpen(GravityCompat.START)) {
           drawer.closeDrawer(GravityCompat.START);
       }

  /*     Fragment myFragment = new MainFragment().getFragmentManager().findFragmentByTag("MY_FRAGMENT");

       if (myFragment != null && myFragment.isVisible()) {
           super.onBackPressed();
       }*/

       else
           super.onBackPressed();
   }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

      if (id == R.id.nav_edit_info) {

          fragment = new BasicInfoFragment();
          if (fragment != null) {
              //Apply fragment
              FragmentTransaction fragmentTransaction =
                      getSupportFragmentManager().beginTransaction();
              fragmentTransaction.replace(R.id.fragment_container, fragment);
              fragmentTransaction.commit();
          }

      }  else if(id == R.id.nav_add_Device)
            {
                fragment= new Fragment_AddDevice();
                if (fragment != null) {
                    //Apply fragment
                    FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }

            }
      else if(id == R.id.nav_edit_notif)
      {
          fragment= new Fragment_Config();
          if (fragment != null) {
              //Apply fragment
              FragmentTransaction fragmentTransaction =
                      getSupportFragmentManager().beginTransaction();
              fragmentTransaction.replace(R.id.fragment_container, fragment);
              fragmentTransaction.commit();
          }

      }
           else if (id == R.id.nav_home){
            fragment = new MainFragment();

            if(fragment!=null) {
                //Apply fragment
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "MY_FRAGMENT");
                fragmentTransaction.commit();
            }
         }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    /* Adding Settings menu
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */




}

