package com.cuuuurzel.utils.mysqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public abstract class SQLEntry {

    protected int rowid;

    public abstract String getTableName();

    public int rowid() { return rowid; }

    public void setRowid( int id ) { this.rowid = id; }

    public void runInsert(SQLiteDatabase db) {
        if ( rowid == -1 ) {
            db.insert(getTableName(), "", getContentValues());
        } else {
            db.update( getTableName(), getContentValues(), "rowid = " + rowid, null );
        }
    }

    /**
     * Should use values from this class to filter records and return the best match's rowid.
     * Rowid should be updated if a match is found.
     */
    public int linkToDB( SQLiteDatabase db ) { return rowid; }

    /**
     * Will return a set of values representing this record.
     */
	public abstract ContentValues getContentValues();
}
