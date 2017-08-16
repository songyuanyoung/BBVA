package com.example.zach.bbva;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwenpurdue on 8/15/2017.
 */




public class MapsFragment extends  Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static GoogleMap map;
    private static LatLng myLocation=new LatLng(40.748817,-73.968285);

    private List<Place> list;

    ArrayList<ResultsItem> places;
    private GoogleMap mMap;
    public static String TAG = MapsActivity.class.getSimpleName();
    StringRequest stringRequest;
    Gson gson;
    static Place place = new Place();

    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=MY_LAT,MY_LONG&radius=10000&key=AIzaSyDaaAwaMvkQQVpCzSxnOtMBirFBG6Xzb50";
    RequestQueue mQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.my_map);
        mapFragment.getMapAsync(this);
        list=new ArrayList<>();
        places = new ArrayList<>();
        gson = new Gson();
        places = new ArrayList<>();

        locationManager= (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {


            @Override
            public void onLocationChanged(android.location.Location location) {
                map.clear();

                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(myLocation)
                        .title("myLocation"));
                Log.d("test"," "+list.size());
                for (int i=0;i<list.size();i++) {
                    LatLng temp =new LatLng(Double.valueOf(list.get(i).getResults().get(i).getGeometry().getLocation().getLat()),Double.valueOf(list.get(i).getResults().get(i).getGeometry().getLocation().getLng()));
                    Log.d("test"," "+temp.toString());
                    map.addMarker(new MarkerOptions().position(temp).
                            title("BBVA"));
                }
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
        };
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
        } else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }

        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        fetchData(url);
    }

    void fetchData(String URL) {
        mQueue = Volley.newRequestQueue(getContext());

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

        mQueue.add(stringRequest);
    }
void markLocation(List<ResultsItem> locations) {
    System.out.println("-------------------------------------");

    System.out.println("status="+ locations.size() + ":");
    for (int i = 0; i < locations.size(); i++) {
        ResultList.getmInstance().add(locations.get(i));
        Location l = locations.get(i).getGeometry().getLocation();
        LatLng latLng = new LatLng(l.getLat(), l.getLng());
        map.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
    }
}
}
