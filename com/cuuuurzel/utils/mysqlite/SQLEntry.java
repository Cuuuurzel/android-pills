package com.cuuuurzel.utils.mysqlite;

import android.database.sqlite.SQLiteDatabase;

public interface SQLEntry {

	public String getTableName();
	
	public boolean runInsert( SQLiteDatabase db );
	
	public int getId( SQLiteDatabase db );
}
