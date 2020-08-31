package com.iqvia.model;

import org.junit.Test;

import com.iqvia.handler.PasswordGenerator;
import com.iqvia.handler.PasswordValidator;

import junit.framework.Assert;

public class PasswordGeneratorTest {

	@Test
	public void generateRandomPassword() {
		PasswordGenerator generator = new PasswordGenerator();
		String generatedPassword = generator.generatePassword(7);
		System.out.println("Generated password = "+generatedPassword);

		PasswordValidator v = new PasswordValidator();
		User user = new User();
		user.setName("Erin M. Hagens");
		user.setEmail("userid@acme.com");
		boolean actualResult = v.isValid(user, generatedPassword );
		boolean expectedResult = true;
		Assert.assertEquals("Generated password is not valid", expectedResult, actualResult);
	}

}
