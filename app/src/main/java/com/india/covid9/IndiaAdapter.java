package com.india.covid9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class IndiaAdapter extends ArrayAdapter<IndiaAdapter> {
    private Context context;
    private List<StateModel> statelist;


    public IndiaAdapter(Context context, List<StateModel> statelist) {
        super(context, R.layout.list_item);

        this.context = context;
        this.statelist = statelist;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null,true);
        TextView state=view.findViewById(R.id.stateTv);
        TextView active=view.findViewById(R.id.activeTv);
        TextView deceased=view.findViewById(R.id.deceasedTv);
        TextView confirmed=view.findViewById(R.id.confirmedTv);
        TextView recovered=view.findViewById(R.id.recoveredTv);
        state.setText(statelist.get(position).getState());
        active.setText(statelist.get(position).getActive());
        deceased.setText(statelist.get(position).getDeaths());
        confirmed.setText(statelist.get(position).getConfirmed());
        recovered.setText(statelist.get(position).getRecovered());
     return view;


    }

    @Override
    public int getCount() {
      return   statelist.size();
    }
}
