package info.androidhive.firebase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback {
    Marker now;
    Circle a;
    private GoogleMap mMap;
    private String lat, lon;
    Button b;
    String check=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = getIntent().getStringExtra("latitude");
        lon = getIntent().getStringExtra("longitude");
        check=getIntent().getStringExtra("Reqinfo");
        b= (Button) findViewById(R.id.send_back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendback();
            }
        });
        if(check!=null)
            b.setVisibility(View.GONE);
    }

    void sendback()
    {
        if(check!=null){

        }
        else{
            Intent intent = new Intent();
            intent.putExtra("latitude",String.valueOf(lat));
            intent.putExtra("longitude",String.valueOf(lon));
            setResult(RESULT_OK, intent);
            finish();}
    }

    @Override
    public void onMarkerDragEnd(Marker arg0) {
        // TODO Auto-generated method stub
        if(check!=null)
        {

        }
        else{
            LatLng dragPosition = arg0.getPosition();
            double dragLat = dragPosition.latitude;
            double dragLong = dragPosition.longitude;
            Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
            Toast.makeText(getApplicationContext(),R.string.marker_msg, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMarkerDrag(Marker arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub
//
//  mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));

        if(check!=null){

        }
        else{
            if (now != null) {
                now.remove();
                a.remove();

            }
            now = mMap.addMarker(new MarkerOptions()
                    .position(arg0)
                    .draggable(true));

//        LatLng dragPosition = arg0.;
            lat = String.valueOf(arg0.latitude);
            lon = String.valueOf(arg0.longitude);
            Log.i("info", "on drag end :" + lat + " dragLong :" + lon);
            Toast.makeText(getApplicationContext(), R.string.marker_msg, Toast.LENGTH_LONG).show();
            a= mMap.addCircle(new CircleOptions().center(new LatLng(Double.valueOf(lat), Double.valueOf(lon))).radius(200).strokeColor(R.color.colorPrimary).fillColor(Color.GREEN));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
     //   mMap.setMyLocationEnabled(true);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);



        LatLng ACU = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        // use map to move camera into position
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ACU));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
  now = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(lat), Double.valueOf(lon))));
   a=     mMap.addCircle(new CircleOptions().center(new LatLng(Double.valueOf(lat), Double.valueOf(lon))).radius(200).strokeColor(R.color.colorPrimary).fillColor(Color.GREEN));

    }


    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}
