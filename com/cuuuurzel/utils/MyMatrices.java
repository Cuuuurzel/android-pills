package com.cuuuurzel.utils;

public class MyMatrices {

	public static double[][] getRotationX( double a ) {
		return new double[][] {
			{ 1, 0,	0 },
			{ 0, Math.cos( a ), -Math.sin( a ) },
			{ 0, Math.sin( a ), Math.cos( a ) }
		};
	}

	public static double[][] getRotationY( double a ) {
		return new double[][] {
			{ Math.cos( a ), 0, Math.sin( a ) },
			{ 0, 1, 0 },
			{ -Math.sin( a ), 0, Math.cos( a ) },
		};
	}

	public static double[][] getRotationZ( double a ) {
		return new double[][] {
			{ Math.cos( a ), -Math.sin( a ), 0 },
			{ Math.sin( a ), Math.cos( a ), 0 },
			{ 0, 0, 1 }
		};
	}

	public static double[][] getIdentity( int d ) {
		double[][] m = new double[ d ][ d ];
		double[] row;
		
		for ( int r=0; r<d; r++ ) {
			row = new double[ d ];
			
			for ( int c=0; c<d; c++ ) {
				if ( c == r ) {
					row[ c ] = 1;					
				} else {
					row[ c ] = 0;					
				}
			}	
			m[ r ] = row;
		}
		return m;
	}
}
