package com.cuuuurzel.utils.showcase;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

/**
 * This class extends my image showcase and add it the possibility to open a Dialog when an element is clicked.
 */
public class DetailedShowcase extends ImageShowcase {

	private DetailDialogBuilder mDialogBuilder;
	private int mLastClickedPosition;
	private View mLastClickedView;
	protected long mLastClickedId;
	
	public DetailedShowcase( Context c, AttributeSet a ) {
		super( c, a );
		this.setOnItemClickListener();
	}
	
	private Dialog getDialog( AdapterView<?> a, View v, int p, long i ) {
		if ( mDialogBuilder == null ) {
			Dialog d = new Dialog(
				getContext()
			);
			d.setTitle( "Default dialog" );
			return d;
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
    				mLastClickedId = i;
    				mLastClickedView = v;
    			}    			
        	}
    	);
	}

	/**
	 * Use this method to suppy a custom dialog builder.
     * The "buildDialog" method is called every time an item is clicked.
	 */
	public void setDetailDialogBuilder( DetailDialogBuilder b ) {
		this.mDialogBuilder = b;
	}

	/**
	 * Returns the position of the last clicked item.
	 */
	public long getLastClickedId() {
		return mLastClickedId;
	}

	/**
	 * Returns the position of the last clicked item.
	 */
	public int getLastClickedPosition() {
		return mLastClickedPosition;
	}

	/**
	 * Returns clicked view.
	 */
	public View getLastClickedView() {
		return mLastClickedView;
	}
}
