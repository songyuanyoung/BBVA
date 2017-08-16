package com.example.zach.bbva;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.zach.bbva.MapsActivity.TAG;

/**
 * Created by zhangwenpurdue on 8/15/2017.
 */

public class DetailsFragment extends Fragment {
    StringRequest stringRequest;
    Gson gson;
    TextView locationTx;
    ImageView profileImg;

    static Place place = new Place();
    Bundle bundle;
    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=MY_LAT,MY_LONG&radius=10000&key=AIzaSyDaaAwaMvkQQVpCzSxnOtMBirFBG6Xzb50";
    RequestQueue mQueue;

    int position;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        gson = new Gson();
        bundle = new Bundle();
        locationTx = (TextView) view.findViewById(R.id.location);
        profileImg = (ImageView) view.findViewById(R.id.profile);

        fetchData(url);
        return view;
    }



    void fetchData(String URL) {
        position = bundle.getInt("position");
        mQueue = Volley.newRequestQueue(getContext());

        stringRequest = new StringRequest(URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, response);

                        place = gson.fromJson(response, Place.class);
                        display(place.getResults(), position);



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
    void display(List<ResultsItem> list, int pos) {
        locationTx.setText("Details info: " + list.get(pos).toString());


    }

    static class ResultHolder extends RecyclerView.ViewHolder {
        TextView mID, mName, mAddress;
        Button mDetails;


        public ResultHolder(View itemView) {
            super(itemView);

            mID = (TextView) itemView.findViewById(R.id.id);
            mName = (TextView) itemView.findViewById(R.id.name);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mDetails = (Button) itemView.findViewById(R.id.details);
        }
    }
}
