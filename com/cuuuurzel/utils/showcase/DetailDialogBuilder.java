package com.cuuuurzel.utils.showcase;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

/**
 * Use this interface to build your own dialog builder.
 */
public interface DetailDialogBuilder {

	/**
	 * Return the dialog to be used.
	 */
	public Dialog buildDialog( Context c, AdapterView<?> a, View v, int p, long i );
}
