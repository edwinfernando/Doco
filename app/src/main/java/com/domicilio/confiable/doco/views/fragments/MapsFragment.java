package com.domicilio.confiable.doco.views.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.domain.Marker;
import com.domicilio.confiable.doco.model.DirectionFinder;
import com.domicilio.confiable.doco.model.DirectionFinderListener;
import com.domicilio.confiable.doco.model.Route;
import com.domicilio.confiable.doco.presenters.fragments.IMapsPresenter;
import com.domicilio.confiable.doco.presenters.fragments.MapsPresenter;
import com.domicilio.confiable.doco.util.DataParser;
import com.domicilio.confiable.doco.util.Utilities;
import com.domicilio.confiable.doco.views.activities.CheckPermissionActivityManager;
import com.domicilio.confiable.doco.views.activities.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.domicilio.confiable.doco.util.Utilities.getMarkerBitmapFromView;

public class MapsFragment extends Fragment implements IMapsView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,DirectionFinderListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private List<Polyline> polylinePaths = new ArrayList<>();
    MainActivity mainActivity;
    ArrayList<LatLng> MarkerPoints;
    private GoogleMap nMap;
    private IMapsPresenter presenter;
    private View mapView;
    private String direccionOrigen;
    private String direccionDestino;
    FloatingSearchView objetivo;
    private ProgressDialog progressDialog;

    LatLng latLng;
    com.google.android.gms.maps.model.Marker currLocationMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapView = inflater.inflate(R.layout.fragment_maps, container, false);
        MarkerPoints = new ArrayList<>();

        return mapView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter = new MapsPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMap = googleMap;

        if (CheckPermissionActivityManager.checkPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, null)) {
            nMap.setMyLocationEnabled(true);
            nMap.setBuildingsEnabled(false);

            if (mapView != null &&
                    mapView.findViewById(Integer.parseInt("1")) != null) {
                // Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                // position on right bottom
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 30, 220);
            }

            mainActivity = (MainActivity) getActivity();

            nMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    if (MarkerPoints.size() > 1) {
                        MarkerPoints.clear();
                        nMap.clear();
                    }

                    // Adding new item to the ArrayList
                    MarkerPoints.add(latLng);
                    objetivo= (FloatingSearchView) mainActivity.findViewById(R.id.floating_search_view);
                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(latLng);
                    Location miLocation = nMap.getMyLocation();
                    LatLng ubicacion = new LatLng(miLocation.getLatitude(),miLocation.getLongitude());
                    direccionOrigen = Utilities.obtenerDireccion(getActivity(),ubicacion);
                    MarkerPoints.add(ubicacion);
                    /**
                     * For the start location, the color of marker is GREEN and
                     * for the end location, the color of marker is RED.
                     */
                    if (MarkerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity(),R.drawable.profile)));
                    }
                    if (MarkerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    }


                    // Add new marker to the Google Map Android API V2
                    nMap.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if (MarkerPoints.size() >= 2) {
                        LatLng origin = MarkerPoints.get(1);
                        LatLng dest = MarkerPoints.get(0);

                        direccionDestino = Utilities.obtenerDireccion(getActivity(),dest);
                        // Getting URL to the Google Directions API
                        String url = Utilities.getUrl(origin, dest);
                        Log.d("onMapClick", url.toString());
                        /**
                        FetchUrl FetchUrl = new FetchUrl();
                        // Start downloading json data from Google Directions API
                        FetchUrl.execute(url);*/
                        sendRequest();

                        //move map camera
                       // nMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                        //nMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    }

                    Log.i("Elementos Market",MarkerPoints.size()+"");
                    LatLng destino = new LatLng(MarkerPoints.get(0).latitude,MarkerPoints.get(0).longitude);
                    //Utilities.obtenerDireccion(getActivity(),destino,objetivo);
                    if(mainActivity.FAB_Status){
                        mainActivity.hideFAB();
                        mainActivity.FAB_Status = false;
                    }
                    //MarkerPoints.clear();
                }

            });

            nMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    if(mainActivity.FAB_Status){
                        mainActivity.hideFAB();
                        mainActivity.FAB_Status = false;
                    }
                }
            });

            /** Estas dos lineas se descomentan para usar el api de google*/
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    //Este metodo utiliza para actualizar el mapa utilizando el modelo vista presenter
    @Override
    public void refreshMap(Marker marker) {

/*
        LatLng location = new LatLng(marker.getLat(), marker.getLng());
        //     mMap.addMarker(new MarkerOptions().position(location).title(marker.getName()));
        //MarkerPoints.add(location);
        //nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity(),R.drawable.profile)));
        markerOptions.position(location);
        nMap.addMarker(markerOptions);
        //nMap.clear();*/
    }


    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(getActivity(), "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getActivity(), "onConnected", Toast.LENGTH_SHORT).show();
        if (CheckPermissionActivityManager.checkPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, null)) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                //place marker at current position
                nMap.clear();
                latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity(),R.drawable.profile)));
                currLocationMarker = nMap.addMarker(markerOptions);
            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000); //5 seconds
            mLocationRequest.setFastestInterval(3000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    //Metodo que permite actualizar el mapa con el api de google
    @Override
    public void onLocationChanged(Location location) {
        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
       // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.profile)));
        markerOptions.flat(true);
        currLocationMarker = mGoogleMap.addMarker(markerOptions);*/

        Toast.makeText(getActivity(), "Location Changed", Toast.LENGTH_SHORT).show();

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(16).build();

        nMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        //If you only need one location, unregister the listener
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(getActivity(), "Buscando",
                "Calculando", true);

        if(polylinePaths!=null)
        {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }


    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();

        for (Route route : routes) {
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            /**
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);**/
             Log.i("Duracion","la duracion es de: "+route.duration.text);
            Log.i("Distancia","La distancia es de: "+route.distance.text);
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(nMap.addPolyline(polylineOptions));
        }
    }


    /**
     * Clases asincronas para pintar la ruta dentro del mapa
     */

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = Utilities.downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(getResources().getColor(R.color.colorOrange));
                Log.d("onPostExecute","onPostExecute lineoptions decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                nMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    private void sendRequest() {

        if (direccionOrigen.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (direccionDestino.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, direccionOrigen, direccionDestino).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
