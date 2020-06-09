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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFrag extends Fragment {
    TextView TotalCases,active,recovered,deaths,todayrecovered,todaydeaths,trecovered,India1;
    ImageView nav;
    FloatingActionButton btn;
   MediaPlayer mp;
    private int positioncountry;
    private SimpleArcLoader simpleArcLoader;
    private PieChart mPieChart;
    ScrollView scrollView;
    String countryname;

    public DetailsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(getContext());
        final NavController navController= Navigation.findNavController(view);
        positioncountry=Paper.book().read("position");
        TotalCases=view.findViewById(R.id.ttlcasesd);
        btn=view.findViewById(R.id.states1);
        active=view.findViewById(R.id.cases1d);
        nav=view.findViewById(R.id.navigation);
        recovered=view.findViewById(R.id.RecoveredActived);
        deaths=view.findViewById(R.id.Deathsactivedd);
        India1=view.findViewById(R.id.india1);
        todaydeaths=view.findViewById(R.id.todaydeathsactived);
        todayrecovered=view.findViewById(R.id.recoveredtodayactived);
        mp = MediaPlayer.create(getContext(), R.raw.beep);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
             navController.navigate(R.id.action_detailsFrag_to_affected);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                navController.navigate(R.id.action_detailsFrag_to_indiaDetails);
            }
        });
        fetchdata();
    }

    private void fetchdata() {
        countryname =Affected.countrylist.get(positioncountry).getCountry();
        India1.setText("Covid-19 Stats "+ countryname);

        TotalCases.setText(Affected.countrylist.get(positioncountry).getCases());
        recovered.setText(Affected.countrylist.get(positioncountry).getRecovered());
        todayrecovered.setText(Affected.countrylist.get(positioncountry).getTodayCases());
        active.setText(Affected.countrylist.get(positioncountry).getActive());
        deaths.setText(Affected.countrylist.get(positioncountry).getDeaths());
        todaydeaths.setText(Affected.countrylist.get(positioncountry).getTodayDeaths());



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Paper.book().delete("position");
        Paper.book().destroy();
    }
}

