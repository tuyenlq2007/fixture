package com.page;

import java.util.List;

public class TempContainer<T> {
	private List<T> items;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}