package com.cuuuurzel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Set of useful and useless functions.
 */
public class MyUtils {

	/**
	 * Shortcut for verify if a string equals the given one in resources.
	 * @param c, the application context.
	 * @param s, first string.
	 * @param i, id of the second string.
	 */
	public static boolean equals( Context c, String s, int i ) {
		String is = c.getResources().getString( i );
		return s.equals( is );
	}
	
	/**
	 * Return the screen width in pixel.
	 */
	public static int screenW( Context c ) {
		return screenSize( c ).x;
	}
	
	/**
	 * Return the screen height in pixel.
	 */	
	public static int screenH( Context c ) {
		return screenSize( c ).y;
	}
	
	/**
	 * Return the screen size in pixel.
	 */	
	public static Point screenSize( Context c ) {
		WindowManager wm = (WindowManager) c.getSystemService( 
			Context.WINDOW_SERVICE
		);
		Point p = new Point( 0, 0 );
		wm.getDefaultDisplay().getSize( p );
		return p;
	}
	
	/**
	 * Will return a rotated version of the given bitmap.
	 * Rotation occurs in anti-clockwise.
	 * @param b, the source bitmap.
	 * @param rad, the angle of rotation, in radians.
	 */
	public static Bitmap rotateBitmap( Bitmap b, double rad ) {
		Matrix matrix = new Matrix();
		matrix.setRotate( 
			( float ) absDeg( rad ), 
			( float ) b.getWidth() / 2, 
			( float ) b.getHeight() / 2 
		);
		return Bitmap.createBitmap( 
			b, 0, 0, 
			b.getWidth(), b.getHeight(), 
			matrix, false
		);
	}
	
	/**
	 * Converts radians to degrees.
	 * If a negative amount of radians is given then degrees amount is adjusted.
	 */
	public static double absDeg( double rad ) {
		double deg = deg( rad );
		if ( deg < 0 ) {
			return 360 + deg;
		}
		return deg;
	}

	/**
	 * Converts degrees to radians.
	 * If a negative amount of degrees is given then radians amount is adjusted.
	 */
	public static double absRad( double deg ) {
		double rad = rad( deg );
		if ( rad < 0 ) {
			return Math.PI * 2 + rad;
		}
		return rad;
	}

	/**
	 * Converts radians to degrees.
	 */
	public static double deg( double rad ) {
		return 360 * rad / ( 2 * Math.PI );
	}

	/**
	 * Converts degrees to radians.
	 */
	public static double rad( double deg ) {
		return 2 * Math.PI * deg / 360;
	}

	/**
	 * Returns the pixel density of the display.
	 */
	public static double getDP( WindowManager wm, double pixels ) {
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		
		switch ( metrics.densityDpi ) {
			case DisplayMetrics.DENSITY_LOW : {
				pixels = pixels * 0.75;
				break;
			}
			case DisplayMetrics.DENSITY_MEDIUM : {
				// pixels = pixels * 1;
				break;
			}	
			case DisplayMetrics.DENSITY_HIGH : {
				pixels = pixels * 1.5;
				break;
			}
		}
		return pixels;
	}
	
	/**
	 * Returns the best provider available.
	 * If no providers are enabled, "" is returned.
	 */
	public static String getProvider( LocationManager lm ) {
		String provider = LocationManager.NETWORK_PROVIDER;
		if ( !lm.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {
			if ( lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
				provider = LocationManager.GPS_PROVIDER;
			} else {
				return "";
			}
		}
		if ( provider == null ) return "";
		if ( provider.equals( "null" ) ) return "";
		return provider;
	}

	/**
	 * Returns true if any provider is available.
	 */
	public static boolean locationAvailable( LocationManager lm ) {
		return !getProvider( lm ).equals( "" );
	}

	/**
	 * Returns the device name in lower case, for example :
	 * lg nexus 4
	 */
	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER.toLowerCase();
		String model = Build.MODEL.toLowerCase();
		if ( model.startsWith( manufacturer ) ) {
			return model;
		} else {
			return manufacturer + " " + model;
		}
	}
}
