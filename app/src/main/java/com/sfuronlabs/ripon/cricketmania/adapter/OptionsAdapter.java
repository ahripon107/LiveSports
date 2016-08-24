package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.Constants;

/**
 * @author Ripon
 */
public class OptionsAdapter extends BaseAdapter {

    Context context;
    String[] itemName;

    LayoutInflater layoutInflater;
    Typeface banglafont;

    public OptionsAdapter(Context context, String[] itemName) {
        this.context = context;
        this.itemName = itemName;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        banglafont = Typeface.createFromAsset(context.getAssets(), Constants.SOLAIMAN_LIPI_FONT);
    }

    @Override
    public int getCount() {
        return itemName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.singleoption, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.tvItemName);

        textView.setTypeface(banglafont);
        textView.setText(itemName[position]);
        return view;
    }
}
