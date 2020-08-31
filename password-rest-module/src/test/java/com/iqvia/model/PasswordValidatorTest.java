package com.iqvia.model;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

import com.iqvia.handler.PasswordValidator;

import junit.framework.Assert;

public class PasswordValidatorTest {

	@Test
	public void testMinPassword() {
		PasswordValidator v = new PasswordValidator();
		User user = new User();
		user.setName("Erin M. Hagens");
		user.setEmail("userid@acme.com");

		boolean actualResult = v.isValid(user, "123");
		boolean expectedResult = false;
		Assert.assertEquals("Password is less than 8 characters, yet passed", expectedResult, actualResult);

		actualResult = v.isValid(user, "123aB0!@");
		expectedResult = true;
		Assert.assertEquals("Password is exactly 8 characters, yet did not pass", expectedResult, actualResult);

		actualResult = v.isValid(user, "1234aB0!@");
		expectedResult = true;
		Assert.assertEquals("Password is greater than 8 characters, yet did not pass", expectedResult, actualResult);
	}

	@Test
	public void testWordTokenMatching() {
		PasswordValidator v = new PasswordValidator();
		User user = new User();
		user.setName("Erin M. Hagens");
		user.setEmail("userid@acme.com");

		boolean actualResult = v.isValid(user, "12345678 erin");
		boolean expectedResult = false;
		Assert.assertEquals(expectedResult, actualResult);

		actualResult = v.isValid(user, "12345678 hagens");
		expectedResult = false;
		Assert.assertEquals(expectedResult, actualResult);

		actualResult = v.isValid(user, "1234aB0!@ m.");
		expectedResult = true;
		Assert.assertEquals(expectedResult, actualResult);

		actualResult = v.isValid(user, "1234aB0!@9");
		expectedResult = true;
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testEmailNotInPassword() {
		PasswordValidator v = new PasswordValidator();
		User user = new User();
		user.setName("Erin M. Hagens");
		user.setEmail("userid@acme.com");

		boolean actualResult = v.isValid(user, "12345678 userid acme.com");
		boolean expectedResult = false;
		Assert.assertEquals("Contains both userid and domain from email yet passed validation", expectedResult,
				actualResult);

		actualResult = v.isValid(user, "12345678 userid");
		expectedResult = false;
		Assert.assertEquals("Contains userid from email yet passed validation", expectedResult, actualResult);

		actualResult = v.isValid(user, "12345678 acme.com");
		expectedResult = false;
		Assert.assertEquals("Contains domain from email yet passed validation", expectedResult, actualResult);

		actualResult = v.isValid(user, "1234aB0!@");
		expectedResult = true;
		Assert.assertEquals(
				"Contained neither userid nor domain from email in password, yet failed validation but should not have",
				expectedResult, actualResult);
	}

	@Test
	public void testCharacterClassCounts() {
		PasswordValidator v = new PasswordValidator();
		User user = new User();
		user.setName("Erin M. Hagens");
		user.setEmail("userid@acme.com");

		boolean actualResult = v.isValid(user, "12345678 aB0!@");
		boolean expectedResult = true;
		Assert.assertEquals("All expected character sets included yet test failed", expectedResult, actualResult);

		actualResult = v.isValid(user, "12345678 aB0!");
		expectedResult = true;
		Assert.assertEquals("4 of 5 character sets included yet test failed", expectedResult, actualResult);

		actualResult = v.isValid(user, "12345678 aB0");
		expectedResult = true;
		Assert.assertEquals("3 of 5 character sets included yet test failed", expectedResult, actualResult);

		actualResult = v.isValid(user, "abcdefghij aB");
		expectedResult = false;
		Assert.assertEquals("2 of 5 character sets included yet test failed", expectedResult, actualResult);

		actualResult = v.isValid(user, "abcdefghij a");
		expectedResult = false;
		Assert.assertEquals("1 of 5 character sets included yet test failed", expectedResult, actualResult);

	}

	@Test
	public void testNameSplitting() {
		String nameSplittingTokens = "[ ,.\\-_#\t]";

		String names = "first last";
		String expectedNames = Arrays.toString(new String[] { "first", "last" });
		String actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first,last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first.last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first-last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first_last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first#last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);

		names = "first	last";
		expectedNames = Arrays.toString(new String[] { "first", "last" });
		actualNames = Arrays.toString(names.split(nameSplittingTokens));
		Assert.assertEquals(expectedNames, actualNames);
	}

	@Test
	public void testPunctuationRegEx() {
		Pattern punctuationPresent = Pattern.compile("[!’?\\”\\-:,;\\(\\)\\[\\]\\{\\}]");

		String haystack = "asdf";
		Matcher matcher = punctuationPresent.matcher(haystack);
		boolean actualResult = matcher.find();
		boolean expectedResult = false;
		Assert.assertEquals("Pattern matched but should not have: " + haystack, expectedResult, actualResult);

		haystack = "asdf!";
		matcher = punctuationPresent.matcher(haystack);
		actualResult = matcher.find();
		expectedResult = true;
		Assert.assertEquals("Pattern did not match but should have: " + haystack, expectedResult, actualResult);

		haystack = "(";
		matcher = punctuationPresent.matcher(haystack);
		actualResult = matcher.find();
		expectedResult = true;
		Assert.assertEquals("Pattern did not match but shouStringld have: " + haystack, expectedResult, actualResult);

		haystack = "\\";
		matcher = punctuationPresent.matcher(haystack);
		actualResult = matcher.find();
		expectedResult = false;
		Assert.assertEquals("Pattern did not match but should have: " + haystack, expectedResult, actualResult);
	}

	@Test
	public void testSymbolRegEx() {
		/**
		 * This regular expression is built separately to avoid needing to escape
		 * characters that would otherwise be interpreted incorrectly.
		 */
		String[] charactersInClass = new String[] { "~", "@", "#", "\\$", "%", "^", "&", "*", "+", "=", "\\|", "<", ">",
				"/", "\\\\" };
		char[] needleCharacters = new char[] { '~', '@', '#', '$', '%', '^', '&', '*', '+', '=', '|', '<', '>', '/',
				'\\' };

		String patternString = "[" + Arrays.asList(charactersInClass).stream().collect(Collectors.joining()) + "]";
		System.out.println(patternString);
		Pattern symbolPresent = Pattern.compile(patternString);

		String haystack = "asdf";
		Matcher matcher = symbolPresent.matcher(haystack);
		boolean actualResult = matcher.find();
		boolean expectedResult = false;
		Assert.assertEquals("Pattern matched but should not have: " + haystack, expectedResult, actualResult);

		for (int counter = 0; counter < needleCharacters.length; counter++) {
			haystack = "12345678" + needleCharacters[counter];
			matcher = symbolPresent.matcher(haystack);
			actualResult = matcher.find();
			expectedResult = true;
			Assert.assertEquals("Pattern did not match but should have: " + haystack, expectedResult, actualResult);
		}
	}

}
