package com.pinetree.cambus.interfaces;

public interface SwitchActivityInterface {
	public void switchActivity(Class<?> name, boolean close);
	public void switchActivity(Class<?> name, int time, boolean close);	
}
