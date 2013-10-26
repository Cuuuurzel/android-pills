package com.cuuuurzel.bugs.showcase;

import com.cuuuurzel.bugs.R;
import com.cuuuurzel.bugs.service.BugsService;
import com.cuuuurzel.bugs.service.NotificationService;
import com.cuuuurzel.bugs.settings.BugsSettings;
import com.cuuuurzel.utils.MyUtils;
import com.cuuuurzel.utils.showcase.DetailDialogBuilder;
import com.cuuuurzel.utils.showcase.DetailedShowcase;
import com.cuuuurzel.utils.showcase.ImageAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

public class SetsShowcase extends Activity implements DetailDialogBuilder {
		
	private static final String TAG = "Sets Showcase";
    private DetailedShowcase mShowcase1;
    private Dialog mDialog;
    private BugsSettings mSettings;
    private Context mContext;
	private int notificationId;
    
	@Override
	public void onCreate( Bundle b ) {
		super.onCreate( b );	
		startService( new Intent( this, BugsService.class ) );
		startService( new Intent( this, NotificationService.class ) );
		setContentView( R.layout.sets_showcase );
		mContext = getApplicationContext();
		mSettings = BugsSettings.getInstance( mContext );
		this.setupSetsView();
		this.toastStartMsg();
		this.fixNotifications();
	}

	private void fixNotifications() {
		int id = getIntent().getIntExtra( "notification id", -1 );
		if ( id != -1 ) {
			NotificationManager mgr = (NotificationManager) 
        		getSystemService(NOTIFICATION_SERVICE);		
			mgr.cancel( id );
		}
	}
	
    private void setupSetsView() {
    	mShowcase1 = setupShowcase( R.id.hview, true );
    	setupShowcase( R.id.hview2, false );
    }
    
    private DetailedShowcase setupShowcase( int id, boolean sets ) {
    	DetailedShowcase showcase = (DetailedShowcase) findViewById( id );
    	Integer[] ids = mSettings.getSetsIcons( sets );
    	showcase.setAdapter( 
    		new ImageAdapter( mContext, ids ) 
    	);
    	showcase.setFixedItemSize( 
    		MyUtils.screenW( mContext ) / 3,
    		MyUtils.screenW( mContext ) / 3    			
        );
    	showcase.setDetailDialogBuilder( this ); 	
    	return showcase;
    }
    
    public void test() {
    	NotificationCompat.Builder mBuilder =
    		new NotificationCompat.Builder( this )
            .setSmallIcon( R.drawable.icon )
            .setContentTitle( "Message" )
            .setContentText( "Notification" );
    	
    	NotificationManager mgr = (NotificationManager) 
    		getSystemService(NOTIFICATION_SERVICE);
    	mgr.notify( notificationId++, mBuilder.build() );
    }
    
    public void unlock() {
    	//Show a popup were telling the user about the full version
    }
	
	public void showMyApps() {
		try {
		    startActivity(
		    	new Intent(
		    		Intent.ACTION_VIEW, 
		    		Uri.parse(
		    			"market://developer?id=Cuuuurzel"
		    		)
		    	)
		    );
		} catch ( ActivityNotFoundException anfe ) {
		    startActivity(
		    	new Intent(
		    		Intent.ACTION_VIEW, 
		    		Uri.parse(
		    			"http://play.google.com/store/apps/developer?id=Cuuuurzel"
		    		)
		    	)
		    );
		}
	}
	
	public void share() {
		String message = getResources().getString( 
			R.string.share_msg 
		);
		String link = "**link to play store**";
		String title = getResources().getString( 
			R.string.choose_an_app 
		);
		Intent share = new Intent( Intent.ACTION_SEND );
		share.putExtra( 
			Intent.EXTRA_TEXT, 
			message + "\n" + link 
		);
		share.setType( "text/plain" );
		startActivity( 
			Intent.createChooser( 
				share, 
				title
			)
		);
	}    	
    
	private void toastStartMsg() {
		Toast.makeText(
			getApplicationContext(),
			getResources().getString( R.string.sets_on_start ),
			Toast.LENGTH_LONG
		).show();
	}
    
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.showcase_bar, menu );
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		String s = item.getTitle().toString();

		if ( MyUtils.equals( this, s, R.string.test ) ) {
			test();
			return true;
		}
		if ( MyUtils.equals( this, s, R.string.unlock ) ) {
			unlock();
			return true;
		}
		if ( MyUtils.equals( this, s, R.string.more ) ) {
			showMyApps();
			return true;
		}
		if ( MyUtils.equals( this, s, R.string.share ) ) {
			share();
			return true;
		}
		if ( MyUtils.equals( this, s, R.string.exit ) ) {
			finish();
			return true;
		}
			
		return false;
	}

	@Override
	public Dialog buildDialog( Context c, AdapterView<?> a, View v, int p, long i ) {
		if ( a.equals( mShowcase1 ) ) {
			return this.buildDialog1( c, a, v, p, i );
		} else {
			return this.buildDialog2( c, a, v, p, i );
		}
	}

	public Dialog buildDialog1( Context c, AdapterView<?> a, View v, int p, long i ) {
		mDialog = new Dialog( c );
		mDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
		mDialog.setContentView( R.layout.bugs_dialog );
		
		mDialog.setOnCancelListener( mOnDetailDialogCancelListener );
		
		GridView bugs = ( GridView ) mDialog.getWindow().findViewById( 
			R.id.bugsGrid 
		);
		CheckBox chkUseThis = ( CheckBox ) mDialog.getWindow().findViewById( 
			R.id.chkUseThis
		);
		chkUseThis.setChecked( 
			mSettings.setsdata[ mSettings.getSet( (int)i ) ].usable
		);
			
		bugs.setAdapter(
			new ImageAdapter( 
				c,
				BugsSettings.ids[ mSettings.getSet( (int)i ) ],
				MyUtils.screenW( c ) / 4,
				MyUtils.screenW( c ) / 4
			)
		);
		bugs.setOnItemClickListener( mOnBugsGridItemClickListener );
		
		return mDialog;
	}

	public Dialog buildDialog2( Context c, AdapterView<?> a, View v, int p, long i ) {
		mDialog = new Dialog( c );
		mDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
		mDialog.setContentView( R.layout.bugs_dialog2 );
		
		GridView bugs = ( GridView ) mDialog.getWindow().findViewById( 
			R.id.bugsGrid2
		);			
		bugs.setAdapter( getDialogGridAdapter( i ) );
		return mDialog;
	}
	
	private ImageAdapter getDialogGridAdapter( long i ) {
		return new ImageAdapter( 
			mContext,
			BugsSettings.ids[ mSettings.getSet( (int)i ) ],
			MyUtils.screenW( mContext ) / 4,
			MyUtils.screenW( mContext ) / 4
		);
	}
	private OnCancelListener mOnDetailDialogCancelListener = new OnCancelListener() {

		@Override
		public void onCancel( DialogInterface dialog ) {
			CheckBox chkUseThis = ( CheckBox ) mDialog.getWindow().findViewById(
				R.id.chkUseThis 
			);
			int pos = ( int ) mShowcase1.getLastClickedId();
			int set = mSettings.getSet( pos );
			mSettings.setsdata[ set ].usable = chkUseThis.isChecked();
		}		
	};
	
	private OnItemClickListener mOnBugsGridItemClickListener = 
	new OnItemClickListener() {
		
		@Override
		public void onItemClick( AdapterView<?> a, View v, int p, long i ) {
			Intent intent = new Intent(
				SetsShowcase.this, 
				BugFocus.class 
			);

			intent.putExtra( "set", mSettings.getSet( (int)i ) );
			intent.putExtra( "position", p );
			startActivityForResult( intent, 0 );	
		}    			
	};
}