package com.cuuuurzel.bugs.showcase;

import android.content.Context;

import com.cuuuurzel.utils.showcase.ImageAdapter;

public class BugsImageAdapter extends ImageAdapter {

    public BugsImageAdapter( Context c, boolean usableSets ) {
        super( c, BugsSettings.getIds( usableSets ), 0, 0 );             	
    }
   
    This class will replace ImageAdapter in this project.
    Create method getIds( usableSets ) in BugsSettings and make it return the ids of the sets the user bought. 
}