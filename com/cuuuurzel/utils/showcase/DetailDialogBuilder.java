package com.cuuuurzel.utils.showcase;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

public interface DetailDialogBuilder {

	public AlertDialog buildDialog( Context c, AdapterView<?> a, View v, int p, long i );
}
