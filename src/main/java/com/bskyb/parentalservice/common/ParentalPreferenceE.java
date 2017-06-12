package com.bskyb.parentalservice.common;

public enum ParentalPreferenceE {
	U(100), // no age limit restrictions apply
	PG(8), // PG means age 8+
	TWELVE(12), // age 12+
	FIFTEEN(15), // age 15+
	EIGHTEEN(18); // age 18+
	
	private int parentalPreference;
	
	ParentalPreferenceE(int parentalPreference){
		this.parentalPreference = parentalPreference;
	}
	
	public int getPatentalPreference(){
		return parentalPreference;
	}
	
}
