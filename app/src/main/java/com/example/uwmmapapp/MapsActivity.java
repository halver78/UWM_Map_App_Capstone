package com.example.uwmmapapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.uwmmapapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;

import org.json.*;

import java.util.Arrays;

import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MapsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {
    public Button button;
    public ImageButton currentLocBtn;

    private GoogleMap mMap;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private ActivityMapsBinding binding;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // For directions
    String beginningAddress;
    String destinationAddress;
    Polyline newPolyline;

    // User Location Variables
    Location myLocation = null;
    Location destinationLocation = null;

    // Get location permissions
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

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

        AutoCompleteTextView fromInput = findViewById(R.id.fromInput);
        AutoCompleteTextView toInput = findViewById(R.id.toInput);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, uwmBuildings);


        // Markers
        Coordinates.init();
        Map<String, double[]> coords = Coordinates.getcoords();

        fromInput.setAdapter(adapter);
        fromInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fromInputItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, fromInputItem);
                if (coords.containsKey(fromInputItem)) {
                    double[] val = coords.get(fromInputItem);
                    final LatLng fromInputll = new LatLng(val[0], val[1]);
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .position(fromInputll)
                                    .title(fromInputItem)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }

            }
        });

        toInput.setAdapter(adapter);
        toInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String toInputItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, toInputItem);
                if (coords.containsKey(toInputItem)) {
                    double[] val = coords.get(toInputItem);

                    final LatLng toInputll = new LatLng(val[0], val[1]);
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .position(toInputll)
                                    .title(toInputItem)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }

            }
        });


        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, BuildingList.class);
                startActivity(intent);
            }
        });

        // Adding functionality to the button
        currentLocBtn = findViewById(R.id.currentLoc);
        currentLocBtn.setOnClickListener(this);

    }

    // Use this to test your input from the user
    private void showToast(String text) {
        Toast.makeText(MapsActivity.this, text, Toast.LENGTH_SHORT).show();
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
//        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        MarkerOptions source = new MarkerOptions().position(new LatLng(43.074903160434566, -87.88194941239998)).title("You");
        MarkerOptions destination = new MarkerOptions().position(new LatLng(43.0756846004461, -87.88602947476944)).title("EMS");

        ArrayList<LatLng> coordinates = new ArrayList<>();
        System.out.println("Trying googleAPI now.");
        try {
            coordinates = callGoogleAPI();
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(
                    new LatLng(43.0747349, -87.88195019999999),
                    new LatLng(43.074748, -87.88542919999999),
                    new LatLng(43.0752263, -87.88543609999999),
                    new LatLng(43.07547020000001, -87.8855677),
                    new LatLng(43.07555079999999, -87.8860433)
            ).color(Color.BLUE);
            mMap.addPolyline(polylineOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println("Coordinates at index " + i + " is: " + coordinates.get(i));
        }
        mMap.addMarker(source);
        mMap.addMarker(destination);
    }

    /**
     * Call Google Maps API and return stuff.
     */
    public ArrayList<LatLng> callGoogleAPI() throws IOException {
        ArrayList<LatLng> listofCoordinates = new ArrayList<LatLng>();
        MarkerOptions source = new MarkerOptions().position(new LatLng(43.074903160434566, -87.88194941239998)).title("You");
        MarkerOptions destination = new MarkerOptions().position(new LatLng(43.0756846004461, -87.88602947476944)).title("EMS");

        String origin = "origin=" + source.getPosition().latitude + "," + source.getPosition().longitude;
        String destinationString = "&destination=" + destination.getPosition().latitude + "," + destination.getPosition().longitude;
        String key = "&key=AIzaSyCLhKMFyZBv-aWajXFnEWIFzhRdI7gNHps";
        String mode = "&mode=walking";
        String directionsURL = "https://maps.googleapis.com/maps/api/directions/json?" + origin + destinationString + key + mode;

        System.out.println("DirectionsURL is: " + directionsURL);
        // Create API Client object
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(directionsURL).method("GET", null).build();

        // Create new thread to launch api request.
        client.newCall(request).enqueue(new Callback() {
            ArrayList<LatLng> tmpCoordinates = new ArrayList<LatLng>();

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        JSONObject jObject = new JSONObject(myResponse);
                        JSONArray jArray = jObject.getJSONArray("routes");
                        String routesArray = jArray.getString(0);
                        JSONObject routesObject = new JSONObject(routesArray);
                        String overViewPolyLine = routesObject.getString("overview_polyline");
                        System.out.println("routesObject are " + routesObject);
                        System.out.println("overViewPolyLine is " + overViewPolyLine);
                        JSONObject pointsObject = new JSONObject(overViewPolyLine);
                        String pointValue = pointsObject.getString("points");
                        System.out.println("Final point is: " + pointValue);
                        // Grab legs portion of payload
                        JSONArray legsObject = routesObject.getJSONArray("legs");
                        System.out.println("Legs Object Length: " + legsObject.length());
                        JSONObject overallLegsObject = new JSONObject(legsObject.getString(0));
                        System.out.println("Overall Legs object is: " + overallLegsObject);
                        // Compile distance
                        JSONObject totalDistanceObject = new JSONObject(overallLegsObject.getString("distance"));
                        String totalDistance = totalDistanceObject.getString("text");
                        System.out.println("totalDistance is: " + totalDistance);
                        // Compile time
                        JSONObject totalDurationObject = new JSONObject(overallLegsObject.getString("duration"));
                        String totalDuration = totalDurationObject.getString("text");
                        System.out.println("totalDuration is: " + totalDuration);
                        // Compile LAT/LON Coordinates
                        JSONArray stepsArray = overallLegsObject.getJSONArray("steps");
                        System.out.println("Steps array: " + stepsArray);
                        System.out.println("stepsArray Length is: " + stepsArray.length());
                        // For each point, create a new coordinate
                        String[] latitudePoints = new String[stepsArray.length()];
                        String[] longitudePoints = new String[stepsArray.length()];

                        for (int i = 0; i < stepsArray.length(); i++) {
                            // Grab start location
                            JSONObject currentStep = new JSONObject(stepsArray.getString(i));
                            System.out.println("Current step is: " + currentStep);
                            JSONObject startLocationObject = new JSONObject(currentStep.getString("start_location"));
                            System.out.println("startLocation is: " + startLocationObject);
                            // Grab LAT/LON
                            Double latitude = Double.parseDouble(startLocationObject.getString("lat"));
                            Double longitude = Double.parseDouble(startLocationObject.getString("lng"));
                            LatLng tmpNode = new LatLng(latitude, longitude);
                            System.out.println("Tmp Node is: " + tmpNode);
                            listofCoordinates.add(new LatLng(latitude, longitude));
                            tmpCoordinates.add(tmpNode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return listofCoordinates;
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation()
    {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try
        {
            if (locationPermissionGranted)
            {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Location> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null)
                            {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        }
                        else
                        {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        }
        catch (SecurityException e)
        {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission()
    {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            locationPermissionGranted = true;
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                locationPermissionGranted = true;
            }
        }
        else
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
//        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    private void updateLocationUI()
    {
        if (mMap == null)
        {
            return;
        }
        try
        {
            if (locationPermissionGranted)
            {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else
            {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                //getLocationPermission();
            }
        }
        catch (SecurityException e)
        {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onClick(View view)
    {
        this.getDeviceLocation();
    }
}