package com.cuuuurzel.utils.showcase;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

public class DetailedShowcase extends ImageShowcase {

	private DetailDialogBuilder mDialogBuilder;
	private int mLastClickedPosition;
	
	public DetailedShowcase( Context c, AttributeSet a ) {
		super( c, a );
		this.setOnItemClickListener();
	}
	
	private AlertDialog getDialog( AdapterView<?> a, View v, int p, long i ) {
		if ( mDialogBuilder == null ) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
				getContext()
			);
			builder.setTitle( "Default dialog" );
			return builder.create();
		} else {
			return mDialogBuilder.buildDialog( 
				getContext(), a, v, p, i 
			);			
		}
	}
	
	private void setOnItemClickListener() {
		
    	this.setOnItemClickListener( 		
    		new OnItemClickListener() {
        		
    			@Override
    			public void onItemClick( AdapterView<?> a, View v, int p, long i ) {
    				getDialog( a, v, p, i ).show();
    				mLastClickedPosition = p;
    			}    			
        	}
    	);
	}

	public void setDetailDialogBuilder( DetailDialogBuilder b ) {
		this.mDialogBuilder = b;
	}

	public int getLastClickedPosition() {
		return mLastClickedPosition;
	}
}