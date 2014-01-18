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
	private ArrayList<CheckableListItem> checked;
	private ArrayList<CheckableListItem> unchecked;
	
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
		
		this.items = new ArrayList<CheckableListItem>();
		this.checked = new ArrayList<CheckableListItem>();
		this.unchecked = new ArrayList<CheckableListItem>();
		CheckableListItem item;
		
		for ( int i=0; i<aa.getCount(); i++ ) {
			item = aa.getItem( i );
			items.add( item );
			if ( item.isChecked() ) {
				checked.add( item );
			} else {
				unchecked.add( item );
			}
		}
	}
	
	public ArrayList<CheckableListItem> getItems() {
		return items;
	}
	
	public ArrayList<CheckableListItem> getChecked() {
		return checked;
	}
	
	public ArrayList<CheckableListItem> getUnchecked() {
		return unchecked;
	}
}
