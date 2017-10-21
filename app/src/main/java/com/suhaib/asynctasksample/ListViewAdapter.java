package com.suhaib.asynctasksample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Suhaib on 20/10/2017.
 */
public class ListViewAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        int counter = 0;
        String keyString = "";
        for (String key : itemsList.keySet()) {
            keyString = key;
            if (counter == i) break;
            counter++;
        }
        return keyString + ";" + itemsList.get(keyString);
    }

    private class ViewData {
        TextView itemName, itemPrice;
    }

    HashMap<String, BigDecimal> itemsList;
    LayoutInflater inflater;

    public ListViewAdapter(HashMap<String, BigDecimal> items, Context context) {
        itemsList = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            ViewData viewData;
            if (view == null) {
                view = inflater.inflate(R.layout.card_layout, null);
                viewData = new ViewData();
                viewData.itemName = view.findViewById(R.id.tv_itemName);
                viewData.itemPrice = view.findViewById(R.id.tv_item_price);
                view.setTag(viewData);
            } else {
                viewData = (ViewData) view.getTag();
            }
            viewData.itemName.setText(((String) getItem(i)).split(";")[0]);
            viewData.itemPrice.setText(((String) getItem(i)).split(";")[1]);
        }
        catch (Exception ex) {
            Log.d(null, ex.getLocalizedMessage());
        }
        return view;
    }
}
