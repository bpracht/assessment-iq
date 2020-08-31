package com.iqvia.handler;

import java.util.Random;
import java.util.stream.IntStream;

public class PasswordGenerator {
	class RandomCharacterGenerator {
		private char[] characters;
		private Random r = new Random();

		public RandomCharacterGenerator(char[] characters) {
			this.characters = characters;
		}

		public char getChar() {
			int index = r.nextInt(characters.length);
			return this.characters[index];
		}
	};

	private RandomCharacterGenerator[] randomCharacterGenerators = new RandomCharacterGenerator[] {
			/**
			 * char array of A-Z
			 */
			new RandomCharacterGenerator(
			IntStream.range('A', 'Z' + 1)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString().toCharArray()
			)
			/**
			 * char array of a-z
			 */
			,new RandomCharacterGenerator(
			IntStream.range('a', 'z' + 1)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString().toCharArray()
			)
			/**
			 * char array of 0-9
			 */
			,new RandomCharacterGenerator(
			IntStream.range('0', '9' + 1)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString().toCharArray()
			)
			/**
			 * char array with !’?”-:,;()[]{}
			 */
			,new RandomCharacterGenerator("!’?”-:,;()[]{}".toCharArray())
			/**
			 * char array with ~@#$%^&*+=|<>/\
			 */
			,new RandomCharacterGenerator("~@#$%^&*+=|<>/\\".toCharArray())
	};

	public String generatePassword(int lengthIn) {
		StringBuilder resultBuilder = new StringBuilder();
		int length = (lengthIn < 8) ? 8 : lengthIn;
		int characterClass = 0;
		for(int counter = 0 ; counter < length; counter++) {
			resultBuilder.append(randomCharacterGenerators[characterClass].getChar());
			characterClass++;
			if( characterClass >= randomCharacterGenerators.length ) {
				characterClass = 0;
			}
		}
		return resultBuilder.toString();
	}

}
