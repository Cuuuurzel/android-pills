package com.cuuuurzel.utils.mysqlite;

import android.database.Cursor;

public class SQLUtils {

	public static void print( Cursor c ) {
		System.out.println( toString( c ) );
	}
	
	public static String toString( Cursor c ) {
		String s = "";
		
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
		return s;
	}
}
