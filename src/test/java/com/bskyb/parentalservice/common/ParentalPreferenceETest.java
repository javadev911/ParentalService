package com.bskyb.parentalservice.common;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ParentalPreferenceETest {

	@Test
	public void getValue() {
		int expectedValueForPG = ParentalPreferenceE.PG.getPatentalPreference();
		ParentalPreferenceE e = ParentalPreferenceE.valueOf("PG");
		assertEquals("Values didn't match!", expectedValueForPG, e.getPatentalPreference());
	}

}
