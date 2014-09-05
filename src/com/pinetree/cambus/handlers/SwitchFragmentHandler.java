package com.pinetree.cambus.handlers;

import com.pinetree.cambus.interfaces.SwitchFragmentInterface;

import android.app.Fragment;

//observer pattern을 통한 call back
public class SwitchFragmentHandler implements Runnable{
	protected SwitchFragmentInterface sfInterface;
	protected Fragment fragment;
	protected boolean close;
	
	public SwitchFragmentHandler(SwitchFragmentInterface sfInterface, Fragment fragment, boolean close){
		this.sfInterface = sfInterface;
		this.fragment = fragment;
		this.close = close;
	}
	
	@Override
	public void run(){
		sfInterface.switchFragment(fragment, close);
	}
}
