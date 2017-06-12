package com.bskyb.parentalservice.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.bskyb.parentalservice.exceptions.TechnicalFailureException;
import com.bskyb.parentalservice.exceptions.TitleNotFoundException;
import com.bskyb.parentalservice.exceptions.UnknownPreferenceException;
import com.bskyb.parentalservice.service.MovieService;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {

	private ParentalControlServiceImpl testClass;
	private MovieService movieService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		testClass = mock(ParentalControlServiceImpl.class, CALLS_REAL_METHODS);
		movieService = mock(MovieService.class);
		setInternalState(testClass, "movieService", movieService);
	}

	@Test
	public void test_isMovieAccessible_throws_TitleNotFoundException() {
		String customerParentalControlPreference = "15";
		String movieId = "9000";
		String movieIdNotFoundMessage = "Movie with id: " + movieId
				+ " is not found";
		try {
			when(movieService.getParentalControlLevel(movieId)).thenThrow(
					new TitleNotFoundException(movieIdNotFoundMessage));
			testClass.isMovieAccessible(customerParentalControlPreference,
					movieId);

		} catch (TitleNotFoundException e) {
			assertEquals(e.getMessage(), movieIdNotFoundMessage);
		} catch (TechnicalFailureException e) {
			fail("TechnicalFailureException is not expected!");
		} catch (UnknownPreferenceException e) {
			fail("UnknownPreferenceException is not expected!");
		}
	}

	@Test
	public void test_isMovieAccessible_throws_UnknownPreferenceException() {
		String customerParentalControlPreference = "100";
		String movieId = "9000";
		String errorMessage = "Given preference: "+ customerParentalControlPreference + " is not known!";
		try {
			testClass.isMovieAccessible(customerParentalControlPreference,movieId);
		} catch (TitleNotFoundException e) {
			fail("TitleNotFoundException is not expected!");
		} catch (TechnicalFailureException e) {
			fail("TechnicalFailureException is not expected!");
		} catch (UnknownPreferenceException e) {
			assertEquals(e.getMessage(), errorMessage);
		}
	}

	@Test
	public void test_isMovieAccessible_throws_TechnicalFailureException() {
		String customerParentalControlPreference = "U";
		String movieId = "200";
		String technicalErrorMessage = "The movie with id:" + movieId
				+ " cannot be watched";
		try {
			when(movieService.getParentalControlLevel(movieId)).thenThrow(
					new TechnicalFailureException(technicalErrorMessage));
			testClass.isMovieAccessible(customerParentalControlPreference,
					movieId);
		} catch (TitleNotFoundException e) {
			fail("TitleNotFoundException is not expected!");
		} catch (TechnicalFailureException e) {
			assertEquals(e.getMessage(), technicalErrorMessage);
		} catch (UnknownPreferenceException e) {
			fail("UnknownPreferenceException is not expected!");
		}
	}

	@Test
	public void test_isMovieAccessible_returns_true_as_parental_control_level_is_equal_or_less_than_customers_pref() {
		String customerParentalControlPreference = "12";
		String movieParentalControlPreference = "15";
		String movieId = "300";

		try {
			when(movieService.getParentalControlLevel(movieId)).thenReturn(
					movieParentalControlPreference);
			boolean result = testClass.isMovieAccessible(
					customerParentalControlPreference, movieId);
			assertTrue(
					"This cannot be false as parental control level of the movie is equal to or less than the customer's preference",
					result);
		} catch (TitleNotFoundException e) {
			fail("TitleNotFoundException is not expected!");
		} catch (TechnicalFailureException e) {
			fail("TechnicalFailureException is not expected!");
		} catch (UnknownPreferenceException e) {
			fail("UnknownPreferenceException is not expected!");
		}
	}

	@Test
	public void test_isMovieAccessible_returns_false_as_parental_control_level_is_greater_than_customers_pref() {
		String customerParentalControlPreference = "18";
		String movieParentalControlPreference = "12";
		String movieId = "500";

		try {
			when(movieService.getParentalControlLevel(movieId)).thenReturn(
					movieParentalControlPreference);
			boolean result = testClass.isMovieAccessible(
					customerParentalControlPreference, movieId);
			assertFalse(
					"This cannot be true as parental control level of the movie is greater than the customer's preference",
					result);
		} catch (TitleNotFoundException e) {
			fail("TitleNotFoundException is not expected!");
		} catch (TechnicalFailureException e) {
			fail("TechnicalFailureException is not expected!");
		} catch (UnknownPreferenceException e) {
			fail("UnknownPreferenceException is not expected!");
		}
	}

}