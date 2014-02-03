package com.cuuuurzel.utils.checkablelist;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CheckableListView extends ListView {

	private ArrayAdapter<CheckableListItem> listAdapter;
	private ArrayList<CheckableListItem> items;
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
		
		@Override
		public void onItemClick( AdapterView<?> parent, View item, int p, long id ) {
			CheckableListItem planet = listAdapter.getItem( p );
			planet.toggleChecked();
			SelectViewHolder viewHolder = (SelectViewHolder) item.getTag();
			viewHolder.getCheckBox().setChecked( planet.isChecked() );
		}
	};
	
	public CheckableListView( Context c ) {
		this( c, null );
	}
	
	public CheckableListView( Context c, AttributeSet a ) {
		this( c, a, 0 );
	}
	
	public CheckableListView( Context c, AttributeSet a, int d ) {
		super( c, a, d );
		this.setOnItemClickListener( mOnItemClickListener );
	}
	
	public void setAdapter( ArrayAdapter<CheckableListItem> aa ) {
		super.setAdapter( aa );
		
		this.listAdapter = aa;
		this.items = new ArrayList<CheckableListItem>();
		
		CheckableListItem item;
		
		for ( int i=0; i<aa.getCount(); i++ ) {
			item = aa.getItem( i );
			items.add( item );
		}
	}
	
	public ArrayList<CheckableListItem> getItems() {
		return items;
	}
	
	public ArrayList<CheckableListItem> getChecked() {
		ArrayList<CheckableListItem> list = new ArrayList<CheckableListItem>();
		for ( CheckableListItem item : this.items ) {
			if ( item.isChecked() ) { list.add( item ); }
		}
		return list;
	}
	
	public ArrayList<CheckableListItem> getUnchecked() {
		ArrayList<CheckableListItem> list = new ArrayList<CheckableListItem>();
		for ( CheckableListItem item : this.items ) {
			if ( !item.isChecked() ) { list.add( item ); }
		}
		return list;
	}
	
	public ArrayList<String> getNames() {
		ArrayList<String> list = new ArrayList<String>();
		for ( CheckableListItem item : this.items ) {
			list.add( item.getName() );
		}
		return list;
	}
	
	public ArrayList<String> getCheckedNames() {
		ArrayList<String> list = new ArrayList<String>();
		for ( CheckableListItem item : this.items ) {
			if ( item.isChecked() ) { list.add( item.getName() ); }
		}
		return list;
	}
	
	public ArrayList<String> getUncheckedNames() {
		ArrayList<String> list = new ArrayList<String>();
		for ( CheckableListItem item : this.items ) {
			if ( !item.isChecked() ) { list.add( item.getName() ); }
		}
		return list;
	}
}
