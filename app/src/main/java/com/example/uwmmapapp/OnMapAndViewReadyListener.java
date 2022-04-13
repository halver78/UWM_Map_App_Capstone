package com.example.uwmmapapp;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 *  HELPER CLASS FOR DIRECTIONS API
 */

public class OnMapAndViewReadyListener implements OnMapReadyCallback, ViewTreeObserver.OnGlobalLayoutListener
{
    /** A listener that needs to wait for both the GoogleMap and the View to be initialized. */
    public interface OnGlobalLayoutAndMapReadyListener
    {
        void onMapReady(GoogleMap googleMap);
    }
    private final SupportMapFragment mapFragment;
    private final View mapView;
    private final OnGlobalLayoutAndMapReadyListener devCallback;
    private boolean isViewReady;
    private boolean isMapReady;
    private GoogleMap googleMap;
    /** Constructor. */
    public OnMapAndViewReadyListener(
            SupportMapFragment mapFragment, OnGlobalLayoutAndMapReadyListener devCallback)
    {
        this.mapFragment = mapFragment;
        mapView = mapFragment.getView();
        this.devCallback = devCallback;
        isViewReady = false;
        isMapReady = false;
        googleMap = null;
        // Register listener when creating an OnMapAndViewReadyListener object.
        registerListeners();
    }
    /**
     * Method to help register a Global Layout Listener for mapView.
     * This is necessary to determining if map view has completed layout.
     **/
    private void registerListeners()
    {
        // View layout.
        if ((mapView.getWidth() != 0) && (mapView.getHeight() != 0))
        {
            // View has already completed layout.
            isViewReady = true;
        }
        else
        {
            // Map has not undergone layout, register a View observer.
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
        // GoogleMap. Note if the GoogleMap is already ready it will still fire the callback later.
        mapFragment.getMapAsync(this);
    }
    /**
     * Use the onMapReady(GoogleMap) callback method to get a handle to the GoogleMap object.
     **/
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // NOTE: The GoogleMap API specifies the listener is removed just prior to invocation.
        this.googleMap = googleMap;
        isMapReady = true;
        fireCallbackIfReady();
    }
    /**
     * Callback method to be invoked when the global layout state or the visibility of views within
     * the view tree changes.
     **/
    @SuppressWarnings("deprecation")  // We use the new method when supported
    @SuppressLint("NewApi")  // We check which build version we are using.
    @Override
    public void onGlobalLayout()
    {
         mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        isViewReady = true;
        fireCallbackIfReady();
    }
    /**
     * If map view is ready, trigger the callback.
     **/
    private void fireCallbackIfReady()
    {
        if (isViewReady && isMapReady)
        {
            devCallback.onMapReady(googleMap);
        }
    }
}