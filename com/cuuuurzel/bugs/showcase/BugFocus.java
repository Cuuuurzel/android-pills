package com.cuuuurzel.bugs.showcase;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.cuuuurzel.bugs.R;
import com.cuuuurzel.bugs.settings.BugsSettings;
import com.cuuuurzel.utils.MyUtils;

public class BugFocus extends Activity {
	
	private BugsSettings mSettings;
	private int mPosition;
	private int setId;
	private Bitmap mPreview;
	
	public static final String TAG = "Bug Focus Activity";
	
    @Override
	protected void onCreate( Bundle b ) {
		super.onCreate( b );	
		setContentView( R.layout.bug_focus );

		mPosition = getIntent().getIntExtra( "position", -1 );		
		setId = getIntent().getIntExtra( "set", -1 );		
		
		mPreview = BitmapFactory.decodeResource(
			getApplicationContext().getResources(),
			BugsSettings.ids[ setId ][ mPosition ]
		); 
		mSettings = BugsSettings.getInstance( this );

		Log.d( TAG, setId + " " + mPosition );
		
		setupView();
	}    	
    
    public void save( View v ) {
    	SeekBar speedBar = (SeekBar) findViewById( R.id.skbSpeed );
    	SeekBar sizeBar = (SeekBar) findViewById( R.id.skbSize );
    	CheckBox chkShow = (CheckBox) findViewById( R.id.ckbShowBug );

    	mSettings.bugsdata[ setId ][ mPosition ].isShown = chkShow.isChecked();
    	mSettings.bugsdata[ setId ][ mPosition ].speed = speedBar.getProgress() / 10.0f; 
    	mSettings.bugsdata[ setId ][ mPosition ].size = sizeBar.getProgress();

    	Log.d( TAG,  "Settings update" );
    	try {
    		mSettings.save( this );
    		toastSaved();
    	} catch ( IOException e ) {
    		e.printStackTrace();
    		toastNotSaved();
    	}
    }
    
    public void exit( View v ) {
    	finish();
    }
    
    private void setupView() {    
    	setupSpeedBar();
    	int size = setupSizeBar();	
    	setupBugPreview( size );
    	setupShown();
    }    
    
    private void setupShown() {
    	CheckBox chkShow = (CheckBox) findViewById( R.id.ckbShowBug );
    	chkShow.setChecked( mSettings.bugsdata[ setId ][ mPosition ].isShown );
    }
	
	private void setupBugPreview( int size ) {
		ImageView bugPreview = (ImageView) findViewById( 
			R.id.bugPreview
		);
		bugPreview.setImageBitmap( 
			Bitmap.createScaledBitmap( 
				mPreview, size, size, false 
			)
		);    	
    }
    
    private void setupSpeedBar() {
    	SeekBar speedBar = (SeekBar) findViewById( R.id.skbSpeed );
    	speedBar.setMax( 50 );
    	speedBar.setProgress( 
    		( int )( 
    			mSettings.bugsdata[ setId ][ mPosition ].speed * 10
    		) 
    	);    	
    }
    
    private int setupSizeBar() {
    	SeekBar sizeBar = (SeekBar) findViewById( R.id.skbSize );
    	sizeBar.setMax( 
    		MyUtils.screenW( 
    			getApplicationContext()
    		) / 2
    	);
    	sizeBar.setProgress(  
    		( int )( 
    			mSettings.bugsdata[ setId ][ mPosition ].size
    		) 
    	);
    	sizeBar.setOnSeekBarChangeListener(
    		getSizeBarListener()
    	);
    	return sizeBar.getProgress();
    }
    
    private OnSeekBarChangeListener getSizeBarListener() {
    	return new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch( SeekBar s ) {}
			
			@Override
			public void onStartTrackingTouch( SeekBar s ) {}
			
			@Override
			public void onProgressChanged( SeekBar s, int p, boolean fu ) {
				if ( p == 0 ) { 
					s.setProgress( 1 );
					p = 1;
				}
				if ( fu ) setupBugPreview( p );
			}
		};
    }
    
	private void toastSaved() {
		Toast.makeText(
			getApplicationContext(),
			getResources().getString( R.string.saved ),
			Toast.LENGTH_SHORT 
		).show();
	}

	private void toastNotSaved() {
		Toast.makeText(
			getApplicationContext(),
			getResources().getString( R.string.not_saved ),
			Toast.LENGTH_SHORT
		).show();
	}
}








 