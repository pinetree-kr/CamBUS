package com.pinetree.cambus.models;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Fragment;

import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.ModelAsyncTaskInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;

public abstract class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100L;
	
	// transient : non-serialized part
	protected transient FragmentCallbackInterface fcInterface;
}
