package com.fil.workerappz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.pojo.BankNetworkListJsonPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FUSION on 12/04/2018.
 */

public class BankListAdapter extends ArrayAdapter<String> implements Filterable{

    protected Filter filter;
    protected ArrayList<String> items;
    protected ArrayList<String> res;

//    String sWds[] = { "SIMPSON", "JONES" };
    private ArrayList<String>data=new ArrayList<>();

    public BankListAdapter(Context context, int textViewResourceId,ArrayList<String> listData) {
        super(context, textViewResourceId,0,listData);

        filter = new PhysFilter();
        res = new ArrayList<String>();
        data=listData;
    }

    public Filter getFilter() {
        return filter;
    }

    private class PhysFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults f = new FilterResults();
            res.clear();
            if (constraint != null) {
                ArrayList<String> res = new ArrayList<String>();
                for (int x = 0; x < data.size(); x++) {
                    if (data.get(x).toUpperCase().contains(constraint.toString().toUpperCase())) {
                        res.add(data.get(x));
                    }
                }
                f.values = res;//.toArray();
                f.count = res.size();
            }
            return f;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                Log.println(Log.INFO, "Results", "FOUND");
//                lWds.clear();
//                lWds.addAll((ArrayList<String>) results.values);
                notifyDataSetChanged();
            } else {
                Log.println(Log.INFO, "Results", "-");
                notifyDataSetInvalidated();
            }
        }
    }
}
