package com.cuuuurzel.utils;

import android.os.FileObserver;

/**
 *  Workaround to use FileObserver as an interface. 
 */
public class MyFileObserver extends FileObserver {

	FileChangeListener listener;

	/**
	 * Same as call with mask setted to FileObserver.CLOSE_WRITE.
	 */
	public MyFileObserver( String path, FileChangeListener l ) {
		this( path, l, FileObserver.ALL_EVENTS );
	}
	
	/**
	 * Creates the observer with path and mask provided, l is the listener to be used. 
	 */
	public MyFileObserver( String path, FileChangeListener l, int mask ) {
		super( path, mask );
		this.listener = l;
	}

	/**
	 * Will call the listener onEvent method.
	 */
	@Override
	public void onEvent( int event, String path ) {
		listener.onEvent( event, path );
	}
}
