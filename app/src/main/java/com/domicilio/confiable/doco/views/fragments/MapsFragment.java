package com.domicilio.confiable.doco.views.fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.domicilio.confiable.doco.R;

import com.domicilio.confiable.doco.data.PlacesAutoComplete;
import com.domicilio.confiable.doco.data.PlacesDataHelper;
import com.domicilio.confiable.doco.domain.Marker;
import com.domicilio.confiable.doco.presenters.fragments.IMapsPresenter;
import com.domicilio.confiable.doco.presenters.fragments.MapsPresenter;
import com.domicilio.confiable.doco.util.DataParser;
import com.domicilio.confiable.doco.util.Utilities;
import com.domicilio.confiable.doco.views.activities.CheckPermissionActivityManager;
import com.domicilio.confiable.doco.views.activities.ListDriverActivity;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.domicilio.confiable.doco.util.Utilities.getMarkerBitmapFromView;

public class MapsFragment extends BaseExampleFragment implements IMapsView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private final String TAG = "BlankFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 2500;

    private static final long ANIM_DURATION = 350;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    MainActivity mainActivity;


    ArrayList<LatLng> MarkerPoints;
    private GoogleMap nMap;
    private IMapsPresenter presenter;
    private View mapView;
    LatLng origin;
    LatLng target;
    FloatingSearchView mSearchView;

    private String mLastQuery = "";
    private boolean mIsDarkSearchTheme = false;
    private ColorDrawable mDimDrawable;
    private View mDimSearchViewBackground;
    private String date_route;

    LatLng latLng;
    com.google.android.gms.maps.model.Marker currLocationMarker;

    ArrayList<MarkerOptions> markers;

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

        mainActivity = (MainActivity) getActivity();
        mSearchView = (FloatingSearchView) mainActivity.findViewById(R.id.floating_search_view);
        //mSearchResultsList = (RecyclerView) mainActivity.findViewById(R.id.search_results_list);

        mDimSearchViewBackground = mainActivity.findViewById(R.id.dim_background);
        mDimDrawable = new ColorDrawable(Color.BLACK);
        mDimDrawable.setAlpha(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDimSearchViewBackground.setBackground(mDimDrawable);
        } else {
            mDimSearchViewBackground.setBackgroundDrawable(mDimDrawable);
        }

        setupFloatingSearch();
        setupDrawer();
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
        nMap.setOnMarkerClickListener(this);

        String date = Utilities.getDate();
        boolean nigth = Utilities.isNigth(date);
        Log.d("isNigth--->", nigth + "");
        if (nigth) {
            nMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mapView.getContext(), R.raw.style_json));
        }

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


            mainActivity.getDrivers(nMap);
            nMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {


                    if (MarkerPoints.size() > 1) {
                        MarkerPoints.clear();
                        nMap.clear();
                    }

                    // Adding new item to the ArrayList
                    MarkerPoints.add(latLng);
                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(latLng);


                    MarkerPoints.add(origin);
                    /**
                     * For the start location, the color of marker is GREEN and
                     * for the end location, the color of marker is RED.
                     */
                    if (MarkerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity(), R.drawable.profile)));
                    }
                    if (MarkerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        options.title("target");

                    }


                    // Add new marker to the Google Map Android API V2
                    nMap.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if (MarkerPoints.size() >= 2) {
                        LatLng origin = MarkerPoints.get(1);
                        LatLng dest = MarkerPoints.get(0);


                        // Getting URL to the Google Directions API
                        String url = Utilities.getUrl(origin, dest);
                        Log.d("onMapClick", url.toString());

                        FetchUrl FetchUrl = new FetchUrl();
                        // Start downloading json data from Google Directions API
                        FetchUrl.execute(url);

                        //move map camera
                        // nMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                        //nMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    }

                    Log.i("Elementos Market", MarkerPoints.size() + "");
                    if (mainActivity.FAB_Status) {
                        mainActivity.hideFAB();
                        mainActivity.FAB_Status = false;
                    }
                    //MarkerPoints.clear();
                }

            });

            nMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    if (mainActivity.FAB_Status) {
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

    @Override
    public void onClickTarget() {


        /**
         if (MarkerPoints.size() >= 2) {
         LatLng origin = MarkerPoints.get(1);
         LatLng dest = MarkerPoints.get(0);

         addressTarget = Utilities.obtenerDireccion(getActivity(), dest);

         String url = Utilities.getUrl(origin, dest);
         Log.d("onMapClick", url.toString());
         FetchUrl FetchUrl = new FetchUrl();
         FetchUrl.execute(url);
         presenter.onClickTarget();
         }*/
    }

    @Override
    public void goToDriverComeFragment() {
        Intent intent = new Intent(getActivity(), ListDriverActivity.class);
        startActivity(intent);
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
                mainActivity.addDrivers(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity(), R.drawable.profile)));
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

        origin = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(getActivity(), "Location Changed", Toast.LENGTH_SHORT).show();
        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(origin).zoom(16).build();
        nMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        //If you only need one location, unregister the listener
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

        markers = mainActivity.getMarkersDriver();
        int i = 0;
        boolean one = false;
        do {
            if (markers.get(i).getTitle().equalsIgnoreCase(marker.getTitle())) {
                Toast.makeText(getActivity(), "Hola " + markers.get(i).getTitle(), Toast.LENGTH_LONG).show();
                one = true;
            }
            i++;
        } while (i < markers.size() && !one);
        return true;
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
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());
                fragmentJson(jsonData[0]);
            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            LatLng position = null;

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
                    position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(getResources().getColor(R.color.colorOrange));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(position);
                markerOptions.title("target");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                if (MarkerPoints.size() > 1) {
                    MarkerPoints.clear();
                    nMap.clear();
                }
                nMap.addMarker(markerOptions);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                nMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
            InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        }

    }


    /**************************************************************************************
     *
     **************************************************************************************/

    private void setupFloatingSearch() {
        mSearchView.setFocusable(true);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    PlacesDataHelper.findSuggestions(getActivity(), newQuery, 5, FIND_SUGGESTION_SIMULATED_DELAY, new PlacesDataHelper.OnFindSuggestionsListener() {
                        @Override
                        public void onResults(List<PlacesAutoComplete> results) {
                            if (results != null) {
                                Log.d("List results---->", results.size() + "");
                                mSearchView.swapSuggestions(results);
                                mSearchView.hideProgress();
                            } else {
                                Log.d("results-->", "Null");
                            }
                        }
                    }, latLng);
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                Log.d(TAG, "onSearchAction()");
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                int headerHeight = 0;
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                        headerHeight, 0);
                anim.setDuration(350);
                fadeDimBackground(0, 150, null);
                anim.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //show suggestions when search bar gains focus (typically history suggestions)
                        mSearchView.swapSuggestions(PlacesDataHelper.getHistory(getActivity(), 3));

                    }
                });
                anim.start();
                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
                int headerHeight = 0;
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                        0, headerHeight);
                anim.setDuration(350);
                anim.start();
                fadeDimBackground(150, 0, null);

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }
        });


        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                //just print action
                if (item.getItemId() == R.id.action_location) {
                    if (!mSearchView.getQuery().equalsIgnoreCase("")) {
                        target = Utilities.getLocationFromAddress(getActivity().getApplicationContext(), mSearchView.getQuery());
                        nMap.clear();
                        String url = Utilities.getUrl(origin, target);
                        FetchUrl FetchUrl = new FetchUrl();
                        FetchUrl.execute(url);
                        fadeDimBackground(150, 0, null);
                    } else {
                        focusToSearchView();
                    }
                }
            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {

                Log.d(TAG, "onHomeClicked()");
            }
        });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item after as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load the left icon images using your favorite image loading library, or change text color.
         *
         *
         * Important:
         * Keep in mind that the suggestion list is a RecyclerView, so views are reused for different
         * items in the list.
         */


        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                PlacesAutoComplete placesAutoComplete = (PlacesAutoComplete) item;

                String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
                String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

                if (placesAutoComplete.getIsHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));
                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }
                textView.setTextColor(Color.parseColor(textColor));
                String text = placesAutoComplete.getBody()
                        .replaceFirst(mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }
        });
    }


    @Override
    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void fadeDimBackground(int from, int to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (Integer) animation.getAnimatedValue();
                mDimDrawable.setAlpha(value);
            }
        });
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }


    public String fragmentJson(String data) throws JSONException {
        if (data == null) {
            return null;
        }
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
        JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
        JSONObject jsonLeg = jsonLegs.getJSONObject(0);
        JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
        JSONObject jsonTime = jsonLeg.getJSONObject("duration");
        date_route = jsonDistance.getString("text") + "," + jsonTime.getString("text");
        Log.d("New Fragment", "Distance: " + jsonDistance.getString("text") + " Time: " + jsonTime.getString("text"));
        //IDriverComeView driverComeView = new DriverComeFragment();
        //driverComeView.setDateRoute(jsonDistance.getString("text"),jsonTime.getString("text"));
        return date_route;
    }

    public void focusToSearchView(){
        if (!mSearchView.isSearchBarFocused()){
            int headerHeight = 0;
            ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                    headerHeight, 0);
            anim.setDuration(350);
            fadeDimBackground(0, 150, null);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!mSearchView.isSearchBarFocused()) {
                        mSearchView.setSearchFocused(true);
                        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(mSearchView, InputMethodManager.SHOW_FORCED);
                    }
                }
            });
            anim.start();
        }
    }

}
