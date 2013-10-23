package com.cuuuurzel.bugs.settings;

import java.io.Serializable;

public class BugInfo implements Serializable {
	
	private static final long serialVersionUID = 1008;
	
	public int set;
	public int position;
	public int size = 150;
	public boolean isShown; 
	public float speed = 5f;
			
	public BugInfo( int set, int p, int size, float speed, boolean shown ) {
		this.set = set;
		this.position = p;
		this.size = size;	
		this.isShown = shown;
		this.speed = speed;
	}
}
