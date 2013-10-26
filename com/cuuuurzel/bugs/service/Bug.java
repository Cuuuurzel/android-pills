package com.cuuuurzel.bugs.service;

import com.cuuuurzel.bugs.R;
import com.cuuuurzel.bugs.settings.BugsSettings;
import com.cuuuurzel.utils.MyUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

@SuppressLint("ViewConstructor")
public class Bug extends View {
	
	private Paint mPaint;
	private Bitmap mSprite1;
	private Bitmap mSprite2;
	private Matrix mMatrix;
	private float[] direction;
	private WindowManager mWindowManager;
	private LayoutParams mLayoutParams;
	private String action;
	private int setId;
	private int kind;
	private int nid;
	private boolean splatted = false;
	private int pause_countdown;
	private boolean onPause = false;
	private int splat_countdown;
	private GestureDetector mGestureDetector;
	private BugsSettings mSettings;
	
	public final static String TAG = "Bug View";
			
	public Bug( Context c, int setId, int kind, int id, String action ) {		
		super( c );
		this.setupAttributes( setId, kind, id, action );
		this.setDefaultLayoutParams();
		this.mWindowManager = (WindowManager) c.getSystemService( 
			Context.WINDOW_SERVICE 
		);
		this.splat_countdown = (int) BugsService.FPS * BugsSettings.splat_countdown_fps;
		this.loadPauseCountdown();
		this.setupGestureListener();
		this.setPaint();
		this.setupSprites();	
		this.setRandomDirection();
	}
	
	private void setupAttributes( int setId, int kind, int id, String action ) {
		this.mMatrix = new Matrix();
		this.action = action;
		this.nid = id;		
		this.kind = kind;
		this.setId = setId;
		this.mSettings = BugsSettings.getInstance(
			getContext()	
		);		
	}
	
	private void playSplat() {
		MediaPlayer mp = MediaPlayer.create( getContext(), R.raw.splat );
		
        mp.setOnCompletionListener(        	
        	new OnCompletionListener() {

	            @Override
	            public void onCompletion( MediaPlayer mp ) {
	                mp.release();
	            }
        	}
        );   
        mp.start();
	}

	private void setDefaultLayoutParams() {
		mLayoutParams = new LayoutParams(
			LayoutParams.TYPE_PHONE,
			LayoutParams.FLAG_NOT_FOCUSABLE, 
			PixelFormat.TRANSLUCENT
		);
		mLayoutParams.width = ( int )( 
			mSettings.getBugSize( setId, kind )
		);
		mLayoutParams.height = ( int )( 
			mSettings.getBugSize( setId, kind )
		);
	}
	
	private void loadPauseCountdown() {
		int min, max;
		
		if ( onPause ) {
			min = BugsSettings.pause_min_fps;
			max = BugsSettings.pause_max_fps;
		} else {
			min = BugsSettings.movin_min_fps;
			max = BugsSettings.movin_max_fps;			
		}
		
		this.pause_countdown = (int)( 
			BugsService.FPS * rnd( min, max )
		);
	}
	
	private double rnd( int min, int max ) {
		return min + Math.random() * ( max - min );
	}
	
	private void setRandomDirection() {
		this.direction = new float[]{
			(float) Math.random() * 2 - 1,
			(float) Math.random() * 2 - 1,	
		};
		double a = Math.atan2(
			this.direction[1],
			this.direction[0]
		);
		mMatrix.setRotate( 
			(float) MyUtils.deg( a ) + 90,
			mSprite1.getWidth() / 2,
			mSprite1.getHeight() / 2
		);
	}
	
    private void setupGestureListener() {
        SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp( MotionEvent me ) {
    			splatted = true;
    			Bug.this.invalidate();
    			launchApp();
    			playSplat();
				return true;            	
            }
            
            @Override
            public boolean onFling( MotionEvent m1, MotionEvent m2, 
            		                float dx, float dy ) {
    			splatted = true;
    			splat_countdown = 0;
				return true;            	            	
            }
        };
        
        mGestureDetector = new GestureDetector( getContext(), gestureListener );
        setOnTouchListener(
        	new OnTouchListener() {
        		
	            @Override
	            public boolean onTouch( View view, MotionEvent motionEvent ) {
	            	mGestureDetector.onTouchEvent( motionEvent );
	                return true;
	            }
        	}
        );
    }
    
	public boolean isSplatted() {
		return splatted;
	}
    
	public void splatNow() {
		splatted = true;
		splat_countdown = 0;
	}
	
	public boolean isDead() {
		if ( splatted ) {
			return --splat_countdown <= 0;
		} else {
			return false;
		}
	}
	
	public int notificationId() {
		return nid;
	}
	
	private void setupSprites() {	
		mSprite1 = Bitmap.createScaledBitmap( 				
			BitmapFactory.decodeResource( 
				getResources(), 
				mSettings.getBugSprite( setId, kind )
			),			
			mSettings.getBugSize( setId, kind ), 
			mSettings.getBugSize( setId, kind ),
			false
		);				
		mSprite2 = Bitmap.createScaledBitmap( 				
			BitmapFactory.decodeResource( 
				getResources(), 
				mSettings.getBugSpriteSplatted( setId, kind )
			),			
			mSettings.getBugSize( setId, kind ), 
			mSettings.getBugSize( setId, kind ),
			false
		);		
	}
	
	public void setPaint( Paint p ) {
		this.mPaint = p;
	}
	
	private void setPaint() {
		Paint p = new Paint();
		p.setStyle( Style.STROKE );
		p.setStrokeWidth( 5 );
		p.setColor( 0xFF000000 );
		setPaint( p );
	}
	
    public void launchApp() {
    	try {
    		Context c = getContext();
        	Log.d( TAG, "Launching " + this.action );
        	Intent i = c.getPackageManager().getLaunchIntentForPackage( 
        		Bug.this.action 
        	);
        	i.putExtra( "notification id", this.nid );
        	c.startActivity( i );
    	} catch ( NullPointerException e ) {        		
    	}
    }
    
	public void update( int dt ) {
		if ( --pause_countdown == 0 ) {
			onPause = !onPause;
			loadPauseCountdown();
		}
		if ( !onPause ) {
			float dx = this.direction[0] * mSettings.getBugSpeed( setId, kind ) * dt;
			float dy = this.direction[1] * mSettings.getBugSpeed( setId, kind ) * dt;
			float w = MyUtils.screenW( getContext() );
			float h = MyUtils.screenH( getContext() );
			
			if ( this.getX()+dx <= -w/2 ||
				 this.getX()+dx+this.getWidth() >= w/2 ||
				 this.getY()+dy <= -h/2 ||
				 this.getY()+dy+this.getHeight() >= h/2 ) {
				setRandomDirection();
			} else {
				this.moveTo( this.getX()+dx, this.getY()+dy );
			}
			updateLayout();
		}
	}
	
	public LayoutParams getLayoutParams() {
		return mLayoutParams;
	}
	
	private void updateLayout() {	
		mLayoutParams.x = (int) this.getX();
		mLayoutParams.y = (int) this.getY();
		mWindowManager.updateViewLayout( 
			this, 
			mLayoutParams 
		);
	}
	
	public void moveTo( float x, float y ) {
		this.setX( x );		
		this.setY( y );
	}
	
	@Override
	public void draw( Canvas canvas ) {
		if (!splatted) {
			canvas.drawBitmap( 
				mSprite1,  
				mMatrix,
				mPaint
			);
		} else {
			canvas.drawBitmap( 
				mSprite2,  
				mMatrix,
				mPaint
			);
		}		
		//canvas.drawRect( 0, 0, getWidth(), getHeight(), mPaint );
	}
}















