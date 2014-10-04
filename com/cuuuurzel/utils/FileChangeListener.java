package com.cuuuurzel.utils;

/**
 *  Workaround to use FileObserver as an interface. 
 */
public interface FileChangeListener {

	/**
	 * Called by MyFileObserver objects when an event is reached.
	 */
	public void onEvent(int event, String path);
}
