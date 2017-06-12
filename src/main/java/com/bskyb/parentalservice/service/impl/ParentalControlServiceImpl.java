package com.bskyb.parentalservice.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.bskyb.parentalservice.common.ParentalPreferenceE;
import com.bskyb.parentalservice.exceptions.TechnicalFailureException;
import com.bskyb.parentalservice.exceptions.TitleNotFoundException;
import com.bskyb.parentalservice.exceptions.UnknownPreferenceException;
import com.bskyb.parentalservice.service.MovieService;
import com.bskyb.parentalservice.service.ParentalControlService;

public class ParentalControlServiceImpl implements ParentalControlService {

	final static Logger logger = Logger
			.getLogger(ParentalControlServiceImpl.class);

	@Inject
	private MovieService movieService;

	public boolean isMovieAccessible(String customerParentalControlPreference,
			String movieId) throws TitleNotFoundException,
			TechnicalFailureException, UnknownPreferenceException {

		String movieParentalControlPreference = movieService
				.getParentalControlLevel(movieId);

		if (logger.isDebugEnabled()) {
			logger.debug("movieParentalControlPreference for movieId: "
					+ movieId + " is " + movieParentalControlPreference);
		}

		return checkForAccessibility(customerParentalControlPreference,
				movieParentalControlPreference);
	}

	private boolean checkForAccessibility(
			String customerParentalControlPreference,
			String movieParentalControlPreference)
			throws UnknownPreferenceException {

		ParentalPreferenceE customerParentalPreferenceE = ParentalPreferenceE
				.valueOf(getActualEnumValue(customerParentalControlPreference));
		ParentalPreferenceE movieParentalControlPreferenceE = ParentalPreferenceE
				.valueOf(getActualEnumValue(movieParentalControlPreference));

		if (customerParentalPreferenceE.getPatentalPreference() <= movieParentalControlPreferenceE
				.getPatentalPreference()) {
			return true;
		}
		return false;
	}

	// To map given preference to Enum name
	private String getActualEnumValue(String givenPref)
			throws UnknownPreferenceException {
		if ("18".equals(givenPref)) {
			return ParentalPreferenceE.EIGHTEEN.name();
		} else if ("15".equals(givenPref)) {
			return ParentalPreferenceE.FIFTEEN.name();
		} else if ("12".equals(givenPref)) {
			return ParentalPreferenceE.TWELVE.name();
		} else if ("PG".equals(givenPref)) {
			return ParentalPreferenceE.PG.name();
		} else if ("U".equals(givenPref)) {
			return ParentalPreferenceE.U.name();
		} else {
			throw new UnknownPreferenceException("Given preference: "
					+ givenPref + " is not known!");
		}
	}

}