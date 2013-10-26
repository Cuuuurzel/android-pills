package com.cuuuurzel.bugs.service;

import com.cuuuurzel.bugs.settings.BugsSettings;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.view.WindowManager;

public class BugsService extends Service {

	public static float FPS = 30;
	public static int DT = ( int )( 1000 / FPS );
	
	private SparseArray<Bug> mNotifications;
	private Handler mHandler;
	private WindowManager mWM;
	private NotificationReceiver mReceiver;
	private BugsSettings mSettings;
	
    public static final String TAG = "Bugs Service";

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNotifications.clear();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.setupAttributes();
		this.setupReceiver();
		this.scheduleUpdate();
		Log.d( TAG, TAG + " started" );
	}
	
	private void setupAttributes() {
		mSettings = BugsSettings.getInstance( this );
		mNotifications = new SparseArray<Bug>();
		mHandler = new Handler();
		mWM = (WindowManager) getSystemService(WINDOW_SERVICE);	
	}
	
	private void setupReceiver() {
		mReceiver = new NotificationReceiver();        
		IntentFilter filter = new IntentFilter();
        filter.addAction( NotificationService.N_POSTED );
        filter.addAction( NotificationService.N_REMOVED );
        registerReceiver( mReceiver, filter );
	}
	
    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive( Context context, Intent intent ) {
    		//Log.d( TAG, "Got something!" );
            String event = intent.getAction();
            String pkg = intent.getStringExtra( "package" );
            int id = intent.getIntExtra( "id", -1 );
            
            if ( event.equals( NotificationService.N_POSTED ) ) {
            	addBug( id, pkg );
            } else {
            	removeBug( id, true );
            }
        }
    }
        
    public void removeBug( int id, boolean notificationRemoved ) {
		if ( mNotifications.indexOfKey( id ) < 0 ) return;
		Bug bug = this.mNotifications.get( id );
		if ( bug.isDead() ) {
	    	mWM.removeView( bug );
	    	this.mNotifications.remove( id );
		} else {
			if ( notificationRemoved && !bug.isSplatted() ) {
				bug.splatNow();
			}	
		}
    }
    
	public void addBug( int id, String pkg ) {
		Log.d( TAG, "Adding bug!" );
		if ( mNotifications.indexOfKey( id ) >= 0 ) return;			
		int[] kind = mSettings.randomBugKind();
		
		Bug bug = new Bug( this, kind[0], kind[1], id, pkg );
		mWM.addView( bug, bug.getLayoutParams() );		
		this.mNotifications.put( id, bug );
	}
	
	private void scheduleUpdate() {
		mHandler.postDelayed( updateCallback, DT );
	}
	
	public Runnable updateCallback = new Runnable() {
		
		public void run() {
			Bug bug;
			for ( int i=0; i<mNotifications.size(); i++ ) {
				bug = mNotifications.valueAt( i );
				if ( !bug.isSplatted() ) {
					bug.update( DT );
				} else {
					removeBug( bug.notificationId(), false );
				}
			}
			scheduleUpdate();
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}