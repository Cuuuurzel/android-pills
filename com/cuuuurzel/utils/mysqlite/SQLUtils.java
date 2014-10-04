package com.cuuuurzel.utils.mysqlite;

import android.database.Cursor;

import java.util.ArrayList;

public class SQLUtils {

	public static void print( Cursor c ) {
		System.out.println( toString( c ) );
	}

    public static String[] toStringArray( Cursor c, boolean useHeader ) {

        int nocol = c.getColumnCount();
        ArrayList<String> result = new ArrayList<String>();

        if ( useHeader ) {
            for ( String name : c.getColumnNames() ) { result.add( name ); }
        }

        while ( c.moveToNext() ) {
            for ( int j=0; j<nocol; j++ ) {
                result.add( c.getString( j ) );
            }
        }

        return result.toArray( new String[]{} );
    }

    public static String[][] toStringMatrix( Cursor c, boolean useHeader ) {

        int nocol = c.getColumnCount();
        String[] x = toStringArray( c, useHeader );
        String[][] result = new String[ c.getCount() + ( useHeader ? 1 : 0) ][ nocol ];

        for ( int i=0; i<x.length; i++ ) {
            for ( int j=0; j<nocol; j++ ) {
                result[ i ][ j ] = x[ i * j ];
            }
        }

        return result;
    }

    public static String[] toStringArray( Cursor c ) {
        return toStringArray( c, false );
    }

    public static String[][] toStringMatrix( Cursor c ) {
        return toStringMatrix( c, false );
    }

	public static String toString( Cursor c ) {
		String s = "";
		int pos = c.getPosition();
		c.moveToPosition( -1 );
		
		while ( c.moveToNext() ) {
			for ( int j=0; j<c.getColumnCount(); j++ ) {
				
				switch ( c.getType( j ) ) {
					case Cursor.FIELD_TYPE_BLOB :
						s += c.getBlob( j );
						break;
					case Cursor.FIELD_TYPE_FLOAT :
						s += c.getFloat( j );
						break;
					case Cursor.FIELD_TYPE_INTEGER :
						s += c.getInt( j );
						break;
					case Cursor.FIELD_TYPE_NULL :
						s += "null";
						break;
					case Cursor.FIELD_TYPE_STRING :
						s += c.getString( j );
						break;
				}
				s += " | ";
			}
			s += "\n";
		} 
		c.moveToPosition( pos );
		return s;
	}
}
