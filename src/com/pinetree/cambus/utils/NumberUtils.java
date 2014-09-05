package com.pinetree.cambus.utils;

public class NumberUtils {
	public static boolean isNumeric(String str){
		return str.matches("[-+]?\\d*\\.?\\d+");
	}
}
