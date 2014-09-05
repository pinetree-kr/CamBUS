package com.pinetree.cambus.interfaces;

import java.util.Observer;

public interface Observerable {
	public void addObserver(Observer o);
	public void deleteObserver(Observer o);
	public void notifyObservers();
}
