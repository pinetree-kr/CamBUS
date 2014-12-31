package com.pinetree.cambus.models;

import java.io.Serializable;

import com.pinetree.cambus.interfaces.FragmentCallbackInterface;

public abstract class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100L;
	
	// transient : non-serialized part
	protected transient FragmentCallbackInterface fcInterface;
}
