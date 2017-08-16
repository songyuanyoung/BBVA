package com.example.zach.bbva;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    ArrayList<ResultsItem> places;
    private GoogleMap mMap;
    public static String TAG = MapsActivity.class.getSimpleName();
    StringRequest stringRequest;
    Gson gson;
    static Place place = new Place();

    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=MY_LAT,MY_LONG&radius=10000&key=AIzaSyDaaAwaMvkQQVpCzSxnOtMBirFBG6Xzb50";
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // setContentView(R.layout.main_page);
        places = new ArrayList<>();
        gson = new Gson();
        places = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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
        fetchData(url);
    }
    void fetchData(String URL) {
        mQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                   {
                       Log.d(TAG, response);

                       place = gson.fromJson(response, Place.class);
                       List<ResultsItem> list = place.getResults();
                       markLocation(list);
                   }
               },         new Response.ErrorListener()
               {
                   @Override         public void onErrorResponse(VolleyError error)         {
                       Log.e("TAG", error.getMessage(), error);
                   }

               });

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Hehe", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(stringRequest);
    }
    void markLocation(List<ResultsItem> locations) {
        System.out.println("-------------------------------------");

        System.out.println("status="+ locations.size() + ":");
        for (int i = 0; i < locations.size(); i++) {
            Location l = locations.get(i).getGeometry().getLocation();
            LatLng latLng = new LatLng(l.getLat(), l.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
        }
    }
}
