package com.cuuuurzel.utils.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Generic adapter to show text in a grid.
 */
public class StringGridAdapter extends BaseAdapter {

    private String[] values;
    private int cellw, cellh;

    public StringGridAdapter( Context c ) {
        values = "This is some demo content for the string grid adapter".split( "\\s" );
        mContext = c;
    }

    private Context mContext;

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public void setCellSize( int cellw, int cellh ) {
        this.cellh = cellh;
        this.cellw = cellw;
    }

    public int getCount() {
        return values.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            view = new TextView( mContext );
            view.setGravity( Gravity.CENTER );
            view.setLayoutParams(new GridView.LayoutParams(cellw, cellh));
        } else {
            view = (TextView) convertView;
        }

        view.setText( values[ position ] );

        return view;
    }
}
