package com.india.covid9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<Model> {

    private Context context;
    private List<Model> countrylist;
    private List<Model> countrylistfiltered;

    public MyCustomAdapter( Context context, List<Model> countrylist) {
        super(context, R.layout.custom_item,countrylist);

        this.context = context;
        this.countrylist = countrylist;
        this.countrylistfiltered=countrylist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,null,true);
        TextView tvCountryName = view.findViewById(R.id.countryname);
        ImageView imageView = view.findViewById(R.id.imageflag);

        tvCountryName.setText(countrylistfiltered.get(position).getCountry());
        Picasso.get().load(countrylistfiltered.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countrylistfiltered.size();
    }

    @Nullable
    @Override
    public Model getItem(int position) {
        return countrylistfiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null|| constraint.length()==0){
                    filterResults.count=countrylist.size();
                    filterResults.values=countrylist;



                }else {
                    List<Model> resultlist=new ArrayList<>();
                    String searchstring=constraint.toString().toLowerCase();
                    for(Model itemmodel:countrylist){
                        if(itemmodel.getCountry().toLowerCase().contains(searchstring)){
                            resultlist.add(itemmodel);
                        }
                        filterResults.count=resultlist.size();
                        filterResults.values=resultlist;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
           countrylistfiltered = (List<Model>) results.values;
           Affected.countrylist= (List<Model>) results.values;
           notifyDataSetChanged();
            }
        };
        return filter;
    }
}