package com.bskyb.parentalservice.service;

import com.bskyb.parentalservice.exceptions.TechnicalFailureException;
import com.bskyb.parentalservice.exceptions.TitleNotFoundException;

public interface MovieService {
	String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
