package com.domicilio.confiable.doco.data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Filter;


import com.domicilio.confiable.doco.util.Utilities;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Juan.Cabuyales on 7/01/2017.
 */

public class PlacesDataHelper {

    private static final String TAG = PlacesDataHelper.class.getSimpleName();

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyCQxJew5urn4FKygB8xpQPqA2f-OOCeP7I";


    private static List<PlacesWrapper> sPlacesWrappers = new ArrayList<>();

    private static List<PlacesAutoComplete> sPlacesSuggestions = new ArrayList<>();

    private static ArrayList<String> resultados;


    public interface OnFindColorsListener {
        void onResults(List<PlacesWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<PlacesAutoComplete> results);
    }

    public static List<PlacesAutoComplete> getHistory(Context context, int count) {

        List<PlacesAutoComplete> suggestionList = new ArrayList<>();
        PlacesAutoComplete placesAutoComplete;
        for (int i = 0; i < sPlacesSuggestions.size(); i++) {
            placesAutoComplete = sPlacesSuggestions.get(i);
            placesAutoComplete.setIsHistory(true);
            suggestionList.add(placesAutoComplete);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (PlacesAutoComplete placesAutoComplete : sPlacesSuggestions) {
            placesAutoComplete.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener, final LatLng ubication) {
        new Filter() {


            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {

                try {

                    loadPlacesInformation(constraint.toString(), ubication);
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sPlacesSuggestions.clear();
                int i = 0;
                do {
                    sPlacesSuggestions.add(new PlacesAutoComplete(resultados.get(i)));
                    i++;
                } while (i < resultados.size());

                Log.d("sPlacesSuggestions-->",""+sPlacesSuggestions.size());
                //DataHelper.resetSuggestionsHistory();
                FilterResults results = new FilterResults();
                Collections.sort(sPlacesSuggestions, new Comparator<PlacesAutoComplete>() {
                    @Override
                    public int compare(PlacesAutoComplete lhs, PlacesAutoComplete rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                Log.d("suggestionList-->",""+sPlacesSuggestions.size());
                results.values = sPlacesSuggestions;
                results.count = sPlacesSuggestions.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<PlacesAutoComplete>) results.values);
                }
            }
        }.filter(query);

    }

    public static void findColors(Context context, String query, final PlacesDataHelper.OnFindColorsListener listener) {
        initColorWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<PlacesWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (PlacesWrapper places : sPlacesWrappers) {
                        if (places.getDescripcion().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(places);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<PlacesWrapper>) results.values);
                }
            }
        }.filter(query);

    }

    private static void initColorWrapperList(Context context) {

        if (sPlacesWrappers.isEmpty()) {
            String jsonString = loadJson(context);
            sPlacesWrappers = deserializeColors(jsonString);
        }
    }

    private static List<PlacesWrapper> deserializeColors(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<PlacesWrapper>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }

    private static String loadJson(Context context) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open("color.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonString;
    }


    public static ArrayList<String> autocomplete(String input, LatLng ubicacion) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?input=" + URLEncoder.encode(input, "utf8"));
            sb.append("&types=address");
            sb.append("&location=" + ubicacion.latitude + "," + ubicacion.longitude);
            sb.append("&language=es");
            sb.append("&radius=5000");
            sb.append("&key=" + API_KEY);


            Log.d("URL -->", sb.toString());

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Log.d(TAG, jsonResults.toString());

            // Create a JSON object hierarchy from the results
            Log.d("JSON--->", jsonResults.toString());
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            resultList = new ArrayList<String>(predsJsonArray.length());

            // Extract the Place descriptions from the results
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }

        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        Log.d("Resultados--->", resultList.size() + "");
        return resultList;
    }

    /**
     * @param sequence
     * @author: Juan.Cabuyales
     * @descripcion: Metodo encargado de cargar la informacion de la posibles opciones de autocompletado
     */
    private static void loadPlacesInformation(final String sequence, final LatLng ubication) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultados = autocomplete(sequence, ubication);
            }
        }).start();

    }
}
