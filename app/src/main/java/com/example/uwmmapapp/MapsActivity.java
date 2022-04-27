package com.example.uwmmapapp;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.uwmmapapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {

    public Button button;
    public ImageButton currentLocBtn;
    public ImageButton mapLayersViewBtn;
    public AutoCompleteTextView fromInput;
    public AutoCompleteTextView toInput;

    private GoogleMap mMap;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private ActivityMapsBinding binding;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // A default location (UWM Campus) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(43.079596, -87.882810);
    private static final float DEFAULT_ZOOM = 15.1F;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //AutoComplete Inputs
        String[] uwmBuildings = getResources().getStringArray(R.array.uwmBuildings);

        fromInput = findViewById(R.id.fromInputId);
        toInput = findViewById(R.id.toInputId);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, uwmBuildings);


        fromInput.setAdapter(adapter);
        toInput.setAdapter(adapter);

        createToFromMarkers(fromInput, toInput);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MapsActivity.this,BuildingList.class);
                startActivity(intent);
            }
        });

        // Adding functionality to the button
        currentLocBtn = findViewById(R.id.currentLoc);
        currentLocBtn.setOnClickListener(this);

    }

    /**
     * Creates a Marker at the specified location user has selected for their to&from locations
     */
    public void createToFromMarkers(AutoCompleteTextView fromInput, AutoCompleteTextView toInput)
    {
        Coordinates.init();
        Map<String,double[]> coords = Coordinates.getcoords();

        //fromInput will respond once the user selects one of the options from the autocomplete options
        fromInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fromInputItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, fromInputItem);
                if(coords.containsKey(fromInputItem)){
                    double[] val = coords.get(fromInputItem);
                    final LatLng fromInputll = new LatLng(val[0],val[1]);
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .position(fromInputll)
                                    .title(fromInputItem)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }

            }
        });


        //toInput will respond once the user selects one of the options from the autocomplete options
        toInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String toInputItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, toInputItem);
                if(coords.containsKey(toInputItem)){
                    double[] val = coords.get(toInputItem);

                    final LatLng toInputll = new LatLng(val[0],val[1]);
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .position(toInputll)
                                    .title(toInputItem)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }

            }
        });

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

        // Add a marker in UWM Campus and move the camera

//        LatLng uwm = new LatLng(43.075231, -87.881425);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(uwm));

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        

        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(43.081929, -87.882696),
                        new LatLng(43.081918, -87.877730 ),
                        new LatLng(43.074692, -87.877918),
                        new LatLng(43.074736, -87.886634),
                        new LatLng(43.077628, -87.886551),
                        new LatLng(43.077645, -87.885884),
                        new LatLng(43.079447, -87.885839),
                        new LatLng(43.079442, -87.882763));

        Polygon polygon = mMap.addPolygon(polygonOptions
                .strokeColor(Color.RED));

    }

    /**
     * Switches the view of the map from normal to sattlite view and vise versa with a click of a button
     * @param view
     */
    public void mapView(View view)
    {
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if(mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        this.getDeviceLocation();
    }
}