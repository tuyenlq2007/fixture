package com.page;

import java.util.concurrent.ConcurrentHashMap;

//Singleton
public enum Variable {
	INSTANCE;
	private ConcurrentHashMap<Integer, String> symbols;
	public String value = "";

	Variable() {
		symbols = new ConcurrentHashMap<Integer, String>();
	}

	// uses: Variable symbol = Variable.INSTANCE.getVariable(1);
	public String getVariable(int i, String newValue) {
		if (symbols.get(i) == null) {
			symbols.put(i, newValue);
		}
		return symbols.get(i);
	}

	public String updateVariable(int i, String newValue) {
		symbols.put(i, newValue);
		return symbols.get(i);
	}

}
