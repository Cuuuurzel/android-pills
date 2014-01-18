package com.cuuuurzel.utils.checkablelist;

public class CheckableListItem {

	private String name = "";
	private boolean checked = false;

	public CheckableListItem() {
	}

	public CheckableListItem(String name) {
		this.name = name;
	}

	public CheckableListItem(String name, boolean checked) {
		this.name = name;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toString() {
		return name;
	}

	public void toggleChecked() {
		checked = !checked;
	}
}
