package com.bskyb.parentalservice.service;

import com.bskyb.parentalservice.exceptions.TechnicalFailureException;
import com.bskyb.parentalservice.exceptions.TitleNotFoundException;
import com.bskyb.parentalservice.exceptions.UnknownPreferenceException;

public interface ParentalControlService {

	boolean isMovieAccessible(String customerParentalControlPreference,
			String movieId) throws TitleNotFoundException,
			TechnicalFailureException, UnknownPreferenceException;

}
