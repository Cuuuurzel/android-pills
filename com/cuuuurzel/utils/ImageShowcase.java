package com.cuuuurzel.utils;

import com.cuuuurzel.bugs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Simple showcase for images whitch uses the ImageAdapter I wrote.
 */
public abstract class ImageShowcase extends Activity {
		
	public static int not = 0;
    public static Integer[] imageSet;
    public static OnItemClickListener onItemClickListener;
    
	@Override
	public void onCreate( Bundle b ) {
		super.onCreate( b );
		this.createLayout();
		this.setupGrid();
	}    	
    
	private void createLayout() {
		//Will made a layout file unnecessary
	}
    
	@Override
	public abstract boolean onCreateOptionsMenu( Menu menu );
	
	@Override
	public abstract boolean onOptionsItemSelected( MenuItem item );
	
    public void setupGrid() {
    	GridView grid = (GridView) findViewById( R.id.bugsGrid );
    	grid.setAdapter( 
    		new ImageAdapter(
    			this, 
    			MyUtils.screenW( getApplicationContext() ) / 4,
    			MyUtils.screenW( getApplicationContext() ) / 4,
    			imageSet	
    		)    		
    	);
    	grid.setOnItemClickListener(
    		this.onItemClickListener
    	);
    }
}