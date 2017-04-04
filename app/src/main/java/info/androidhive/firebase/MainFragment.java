package info.androidhive.firebase;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button B1,B2;
    private GoogleApiClient googleApiClient;


    double latitude;
    double longitude;
    double latitudeT;
    double longitudeT;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Child Tracker");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        B1 = (Button) view.findViewById(R.id.button2);
    B2 = (Button) view.findViewById(R.id.button90);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUserData();

            }


        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BL();
            }


        });

        return view;
    }


    void starty()
    {
        Intent i = new Intent(this.getActivity(), MapsActivity.class);
        i.putExtra("latitude", String.valueOf(latitudeT));
        i.putExtra("longitude", String.valueOf(longitudeT));
        startActivity(i);

    }
    void BL()
    {
        Intent i = new Intent(this.getActivity(), BL.class);
        startActivity(i);
    }
    void SetUserData() {

            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                Location userCurrentLocation1 = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (userCurrentLocation1 != null) {

                    final double temp_latitude = userCurrentLocation1.getLatitude();
                    final double temp_longitude = userCurrentLocation1.getLongitude();
                    latitudeT = temp_latitude;
                    longitudeT = temp_longitude;
                    /*Intent i = new Intent(getActivity(),  MapsActivity.class);
                    i.putExtra("latitude",String.valueOf(latitude));
                    i.putExtra("longitude",String.valueOf(longitude));

                    startActivityForResult(i, 1);*/
                    starty();
                }
            } else {
                Log.d("no premison", "WOORKKKKing");
                Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (userCurrentLocation != null) {
                    Log.d("userCurrentLocation", "WOORKKKKing");
                    final double temp_latitude = userCurrentLocation.getLatitude();
                    final double temp_longitude = userCurrentLocation.getLongitude();
                    latitudeT = temp_latitude;
                    longitudeT = temp_longitude;
                    Log.d("latitude", String.valueOf(latitudeT));
               /*     Intent i = new Intent(getActivity(),  MapsActivity.class);
                    i.putExtra("latitude",String.valueOf(latitude));
                    i.putExtra("longitude",String.valueOf(longitude));
                    startActivityForResult(i, 1);
*/
                    starty();
            }
        }
    }
    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onResume(){
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showGPSDisabledAlertToUser() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(R.string.gps_dis)
                .setCancelable(false)
                .setPositiveButton(R.string.goto_en_gps,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
