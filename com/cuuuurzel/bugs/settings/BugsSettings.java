package com.cuuuurzel.bugs.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.cuuuurzel.bugs.R;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

public class BugsSettings implements Serializable {
	
	private static final long serialVersionUID = 1017;
	private static final String path = "settings";
	private static final String TAG = "Bugs Settings";

	public static Integer[] icons = new Integer[] {
		R.drawable.i00, 
		R.drawable.i10, 
		R.drawable.i20, 
		R.drawable.i30
	};
	
	public static Integer[][] ids = new Integer[][] {
		{ R.drawable.i00, R.drawable.i01, R.drawable.i02, R.drawable.i03, R.drawable.i04 },
		{ R.drawable.i10, R.drawable.i11, R.drawable.i12, R.drawable.i13, R.drawable.i14 },
		{ R.drawable.i20, R.drawable.i21, R.drawable.i22, R.drawable.i23, R.drawable.i24 },
		{ R.drawable.i30, R.drawable.i31, R.drawable.i32, R.drawable.i33, R.drawable.i34 }
	};
	
	public static Integer[][] ids_splatted = new Integer[][] {
		{ R.drawable.i00s, R.drawable.i01s, R.drawable.i02s, R.drawable.i03s, R.drawable.i04s },
		{ R.drawable.i10s, R.drawable.i11s, R.drawable.i12s, R.drawable.i13s, R.drawable.i14s },
		{ R.drawable.i20s, R.drawable.i21s, R.drawable.i22s, R.drawable.i23s, R.drawable.i24s },
		{ R.drawable.i30s, R.drawable.i31s, R.drawable.i32s, R.drawable.i33s, R.drawable.i34s }
	};
	
	public static int pause_max_fps = 20;
	public static int pause_min_fps = 7;
	public static int movin_max_fps = 7;
	public static int movin_min_fps = 0;
	public static int splat_countdown_fps = 3;
	public static int set_lenght = 5;
	public static int n_sets = 4;
	public static int default_bug_size = 150;
	public static int default_bug_speed = 150;

	public BugInfo[][] bugsdata;
	public SetInfo[] setsdata;
	public SparseArray<Integer> setsIds;
	
	public static BugsSettings getInstance( Context c ) {
		BugsSettings bs = BugsSettings.getDefault( c );
		try {
			bs.load( c );
		} catch ( InvalidClassException e ) {
			try {
				bs.save( c );
			} catch ( IOException e1 ) {
			}
			return BugsSettings.getInstance( c );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return bs;
	}	
	
	public BugsSettings() {
		this.setupIdsArray();
	}
	
	private static BugsSettings getDefault( Context c ) {
		BugsSettings bs = new BugsSettings();

		bs.bugsdata = new BugInfo[ n_sets ][ set_lenght ];
		bs.setsdata = new SetInfo[ n_sets ];
		
		for ( int s=0; s<n_sets; s++ ) {			
			bs.setsdata[ s ] = new SetInfo( false, false );
			
			for ( int b=0; b<set_lenght; b++ ) {				
				bs.bugsdata[ s ][ b ] = new BugInfo(
					s, b, default_bug_size, default_bug_speed, true
				);
			}
		}
		bs.setsdata[ 0 ].usable = true;
		bs.setsdata[ 0 ].bought = true;		
		return bs;
	}
	
	public void load( Context c ) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = new ObjectInputStream(
			getFIStream( c )
		);
		BugsSettings temp = (BugsSettings) ois.readObject();
		this.bugsdata = temp.bugsdata;
		this.setsdata = temp.setsdata; 
		ois.close();
	}	
	
	public void save( Context c ) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
			getFOStream( c )
		);
		oos.writeObject( this );
		oos.close();
		
		String ss = "";
		for ( int s=0; s<n_sets; s++ ) {
			for ( int b=0; b<set_lenght; b++ ) {				
				ss += " " + bugsdata[s][b];
			}
			ss += "\n";
		}		
		Log.d( TAG, ss );
	}
	
	private FileOutputStream getFOStream( Context c ) 
	throws FileNotFoundException {
		return c.openFileOutput(
			BugsSettings.path,
			Context.MODE_PRIVATE
		);
	}
	
	private FileInputStream getFIStream( Context c ) 
	throws FileNotFoundException {
		return c.openFileInput(
			BugsSettings.path
		);
	}
	
	public int getBugSize( int s, int p ) {
		return bugsdata[s][p].size;
	}
	
	public int getBugSprite( int s, int p ) {
		return ids[s][p];
	}
	
	public int getBugSpriteSplatted( int s, int p ) {
		return ids_splatted[s][p];
	}
	
	public boolean isBugDescriptor( int s, int p ) {
		return bugsdata[s][p].isShown;
	}
	
	public float getBugSpeed( int s, int p ) {
		return bugsdata[s][p].speed;
	}
	
	public boolean isSetUsable( int s ) {
		return setsdata[ s ].usable;
	} 

	private boolean isBugShowable( int s, int p ) {
		return setsdata[s].usable && bugsdata[s][p].isShown;
	}
	
	public Integer[] getSetsIcons( boolean usableSets ) {
		ArrayList<Integer> usables = new ArrayList<Integer>();
		ArrayList<Integer> locked = new ArrayList<Integer>();
		
		for ( int i=0; i<setsdata.length; i++ ) {
			if ( setsdata[ i ].bought ) {
				usables.add( icons[ i ] );
			} else {
				locked.add( icons[ i ] );
			}
		} 
		
		if ( usableSets ) {
			return usables.toArray( new Integer[]{} );
		} else {
			return locked.toArray( new Integer[]{} );
		}
	}
	
	private void setupIdsArray() {
		setsIds = new SparseArray<Integer>();		
		for ( int s=0; s<n_sets; s++ ) {
			for ( int b=0; b<set_lenght; b++ ) {
				setsIds.append( ids[s][b], s );			
			}			
		}
	}
	
	public Integer[] getSets( boolean usableSets ) {
		ArrayList<Integer> usables = new ArrayList<Integer>();
		ArrayList<Integer> locked = new ArrayList<Integer>();
		
		for ( int i=0; i<setsdata.length; i++ ) {
			if ( setsdata[ i ].bought ) {
				usables.add( i );
			} else {
				locked.add( i );
			}
		} 
		
		if ( usableSets ) {
			return usables.toArray( new Integer[]{} );
		} else {
			return locked.toArray( new Integer[]{} );
		}
	}
	
	public int[] randomBugKind() {
		//FIXME
		double[] ps = new double[ ids.length ];		
		for ( int i=0; i<ids.length; i++ ) {			
			if ( isSetUsable( i ) ) {
				ps[i] = Math.random() + 1;
			} else {
				ps[i] = 0;
			}
		}		
		int s = getMax( ps );
		
		double[] pb = new double[ set_lenght];
		for ( int i=0; i<set_lenght; i++ ) {			
			if ( isBugShowable( s, i ) ) {
				pb[i] = Math.random() + 1;
			} else {
				pb[i] = 0;
			}
		}
		return new int[]{ s, getMax( pb ) };
	}
	
	private static int getMax( double[] p ) {
		int i = 0;
		for ( int j=1; j<p.length; j++ ) {
			if ( p[j] > p[i] ) {
				i = j;
			}
		}
		return i;
	}
	
	public int getSet( int drawable ) {
		return setsIds.get( drawable );
	}
}
