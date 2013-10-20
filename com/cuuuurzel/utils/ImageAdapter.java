package com.cuuuurzel.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * An adapter I've tried to keep as simplest as possible.
 */
public class ImageAdapter extends BaseAdapter {
	
    private Context mContext;
    private Integer[] mThumbIds;
    private float fixedImageWidth;
    private float fixedImageHeight;
    
    /**
     * @param c, the application context.
     * @param ids, 
     *     the list of ids the adapter will use, for example :
     *     new Integer[]{ R.drawable.img1, R.drawable.img2, ... }
     */
    public ImageAdapter( Context c, Integer[] ids ) {
        this( c, 0, 0, ids );     
    }

    /**
     * @param c, the application context.
     * @param ids, 
     *     the list of ids the adapter will use, for example :
     *     new Integer[]{ R.drawable.img1, R.drawable.img2, ... }
     * @param fixedImageWidth, fixed image width in pixel.
     * @param fixedImageHeight, fixed image height in pixel.
     */
    public ImageAdapter( Context c, 
    		             float fixedImageWidth, 
    		             float fixedImageHeight, 
    		             Integer[] ids ) {
        this.mContext = c;
        this.mThumbIds = ids;
        this.fixedImageWidth = fixedImageWidth;
        this.fixedImageHeight = fixedImageHeight;        
    }

    /**
     * Return the number of elements in this adapter.
     */
    public int getCount() {
        return mThumbIds.length;
    }

    /**
     * Return the Drawable corresponding to the given index.
     */
    public Drawable getItem(int position) {
    	return mContext.getResources().getDrawable( 
    		(int) getItemId( position )
    	);
    }

    /**
     * Return the id corresponding to the given index. 
     */
    public long getItemId(int position) {
        return mThumbIds[ position ];
    }


    /**
     * Given position, a view and the parent, will return an ImageView.
     */
    public View getView( int position, View convertView, ViewGroup parent ) {
        ImageView imageView;
    	
        if ( convertView == null ) {
            imageView = new ImageView( mContext );
            imageView.setScaleType( 
            	ImageView.ScaleType.FIT_CENTER
            );            
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(
        	(int) getItemId( position )
        );
        
        if ( fixedImageWidth!=0 && fixedImageHeight!= 0 ) {
	        imageView.setLayoutParams(
	        	new LayoutParams( 
	        		( int ) fixedImageWidth, 
	        		( int ) fixedImageHeight 
	        	) 
	        );
        }
        return imageView;        		
    }
}