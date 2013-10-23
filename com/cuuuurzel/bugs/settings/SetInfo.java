package com.cuuuurzel.bugs.settings;

import java.io.Serializable;

public class SetInfo implements Serializable {
	
	private static final long serialVersionUID = 1009;

	public boolean usable;
	public boolean bought;
			
	public SetInfo( boolean u, boolean b ) {
		this.usable = u;
		this.bought = b;
	}
}
