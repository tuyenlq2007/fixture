package com.page;

public class FillGlobalInfo {
	public String IGetVariableWith(int i, String newValue) {
		return Variable.INSTANCE.getVariable(i, newValue);
	}

	public String IUpdateVariableWith(int i, String newValue) {
		return Variable.INSTANCE.updateVariable(i, newValue);
	}
}