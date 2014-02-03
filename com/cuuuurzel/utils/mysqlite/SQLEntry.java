package com.cuuuurzel.utils.mysqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public interface SQLEntry {

	public String getTableName();
	
	public void runInsert( SQLiteDatabase db );
	
	public int getId( SQLiteDatabase db );
	
	public ContentValues getContentValues();
}
