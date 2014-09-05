package com.pinetree.cambus.handlers;

import com.pinetree.cambus.interfaces.SwitchActivityInterface;

// observer pattern을 통한 call back
public class SwitchActivityHandler implements Runnable{
	protected SwitchActivityInterface saInterface;
	protected Class<?> activity;
	protected boolean close;
	
	public SwitchActivityHandler(SwitchActivityInterface saInterface, Class<?> activity, boolean close){
		this.saInterface = saInterface;
		this.activity = activity;
		this.close = close;
	}
	
	@Override
	public void run(){
		saInterface.switchActivity(activity, close);
	}
}
