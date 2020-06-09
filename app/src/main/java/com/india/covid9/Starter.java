package com.india.covid9;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Starter extends Fragment {
NavController navController;
ScrollView scroll;
MediaPlayer mp;
int position;
    TextView TotalCases,active,recovered,deaths,todayrecovered,todaydeaths,trecovered,India;
    Button Explore;
    String Countryname;
    VideoView videoView;
    ImageView search,stack;
    FloatingActionButton states;
     int stat=0;
    public Starter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        India=view.findViewById(R.id.india);
        Explore=view.findViewById(R.id.Explore);
        trecovered=view.findViewById(R.id.todayrecovered);
        states=view.findViewById(R.id.states);

        search=view.findViewById(R.id.search11);
        stack=view.findViewById(R.id.stack);
        TotalCases=view.findViewById(R.id.ttlcases);
        active=view.findViewById(R.id.cases1);
        recovered=view.findViewById(R.id.RecoveredActive);
        deaths=view.findViewById(R.id.Deathsactive);
        todaydeaths=view.findViewById(R.id.todaydeathsactive);
        todayrecovered=view.findViewById(R.id.recoveredtodayactive);
        mp = MediaPlayer.create(getContext(), R.raw.beep);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();

                if(stat==1){
                    Explore.setEnabled(true);
                    Explore.setText("World Stats");
                    India.setText("Covid-19 Stats \n India");
                    fetchdataindia();
                    stat=0;

                }else if(stat==0) {
                    Explore.setText("India Stats");
                    India.setText("Covid-19 Stats \n World");
                    fetchdata();
                    stat=1;
                }
            }
        });
        states.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                navController.navigate(R.id.action_starter_to_indiaDetails);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                navController.navigate(R.id.action_starter_to_affected);
            }
        });
        stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
          navController.navigate(R.id.action_starter_to_symbtoms2);
            }
        });
  fetchdataindia();

    }
    private void fetchdata() {
        trecovered.setText("Today Cases");
        String url="https://corona.lmao.ninja/v2/all";
        StringRequest request=new StringRequest(Request.Method.GET,url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject((response.toString()));
                            TotalCases.setText(jsonObject.getString("cases"));
                            recovered.setText(jsonObject.getString("recovered"));
                            active.setText(jsonObject.getString("active"));
                            todaydeaths.setText(jsonObject.getString("todayDeaths"));
                            deaths.setText(jsonObject.getString("deaths"));
                            todayrecovered.setText(jsonObject.getString("todayCases"));





                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    private void fetchdataindia() {
        String url = "https://api.covid19india.org/data.json";

//        loader.start();
        try {
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                                   active.setText(jsonObject2.getString("active"));
                                    recovered.setText(jsonObject2.getString("recovered"));
                                    deaths.setText(jsonObject2.getString("deaths"));
                                    TotalCases.setText(jsonObject2.getString("confirmed"));
                                    todaydeaths.setText(jsonObject2.getString("deltadeaths"));
                                    todayrecovered.setText(jsonObject2.getString("deltarecovered"));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}
