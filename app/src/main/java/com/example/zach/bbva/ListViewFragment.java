package com.example.zach.bbva;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.zach.bbva.MapsActivity.TAG;
import static com.example.zach.bbva.MapsActivity.place;

/**
 * Created by zhangwenpurdue on 8/15/2017.
 */

public class ListViewFragment extends Fragment {
    TextView textView;
    StringRequest stringRequest;
    Gson gson;
    Adapter adapter;
    static Place place = new Place();
    ArrayList<ResultsItem> list;
    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=MY_LAT,MY_LONG&radius=10000&key=AIzaSyDaaAwaMvkQQVpCzSxnOtMBirFBG6Xzb50";
    RequestQueue mQueue;

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        textView = (TextView) view.findViewById(R.id.text);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        gson = new Gson();
        list = new ArrayList<>();
        fetchData(url);

        textView.setText(list.size() + ":");
        return view;
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

                 
                        recyclerView.setAdapter(new Adapter(getActivity(), new ArrayList<ResultsItem>(place.getResults())));

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




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
                Toast.makeText(getContext(), "Hehe", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(stringRequest);
    }

}
