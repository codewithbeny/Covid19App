package com.india.covid9;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndiaDetails extends Fragment {
    private ListView listView;
    Bundle bundle;
    String lastupdated;
    TextView LastUpdatedtv,cnf,act,rec,death;
    StateModel statemodel;
    IndiaAdapter adapter;
    NavController navController;
    List<StateModel> stateModelList=new ArrayList<>();

    public IndiaDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
       listView=view.findViewById(R.id.list);
//
       rec=view.findViewById(R.id.recoveredTv);
       act=view.findViewById(R.id.activeTv);
       cnf=view.findViewById(R.id.confirmedTv);
       death=view.findViewById(R.id.deceasedTv);
      fetchdata();
    }

    private void fetchdata() {
        String url  = "https://api.covid19india.org/data.json";

//        loader.start();
try {
    StringRequest request = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                             JSONObject jsonObject2=jsonArray.getJSONObject(0);
                          //  String statename = jsonObject2.getString("state");
                            act.setText(jsonObject2.getString("active"));
                            rec.setText(jsonObject2.getString("recovered"));
                            death.setText(jsonObject2.getString("deaths"));
                            cnf.setText(jsonObject2.getString("confirmed"));
                            lastupdated = jsonObject2.getString("lastupdatedtime");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String statename = jsonObject1.getString("state");
                            String active = jsonObject1.getString("active");
                            String recovered = jsonObject1.getString("recovered");
                            String deaths = jsonObject1.getString("deaths");
                            String confirmed = jsonObject1.getString("confirmed");
                          //   lastupdated = jsonObject1.getString("lastupdatedtime");


                             statemodel = new StateModel(active, confirmed, deaths, lastupdated, recovered, statename);
                            stateModelList.add(statemodel);



                        }


                        adapter = new IndiaAdapter(getContext(), stateModelList);
                        listView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
//                            loader.stop();
//                            loader.setVisibility(View.GONE);
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//                loader.stop();
//                loader.setVisibility(View.GONE);
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });



        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

}catch (Exception e) {
    e.printStackTrace();

}
    }
    public void settingdate(String lastupdated){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(lastupdated).getTime();
            Log.i("last",lastupdated);
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            LastUpdatedtv.setText(String.valueOf(ago));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
