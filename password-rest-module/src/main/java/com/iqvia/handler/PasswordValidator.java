package com.iqvia.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.iqvia.model.User;

public class PasswordValidator {

	interface Rule {
		public boolean test(String password, User user);
	}

	List<Rule> rules = new ArrayList<>();

	public PasswordValidator() {
		
		/**
		 * 2.1.a: Must be at least 8 characters long
		 */
		rules.add((p,u) -> p.length()>=8);
		
		/**y of these delimiters are found, the name is split and all parsed sections (tokens) are confirmed not to
         * be included in 
		 * 2.1.b:
		 * Passwords must not contain the user's entire name, or token value. Both checks are not case sensitive:
         * The user's name is parsed for delimiters: commas, periods, dashes or hyphens, underscores, spaces, pound signs,
         * and tabs. If any of these delimiters are found, the name is split and all parsed sections (tokens) are confirmed not to
         * be included in the password. Tokens that are less than three characters in length are ignored, and substrings of the
         * tokens are not checked. For example, the name "Erin M. Hagens" is split into three tokens: "Erin," "M," and "Hagens."
         * Because the second token is only one character long, it is ignored. Therefore, this user could not have a password
         * that included either "erin" 
		 */
		rules.add((p,u) -> {
			String nameSplittingTokens = "[ ,.\\-_#\t]";
			List<String> passwordTokens = Arrays.asList(p.toUpperCase().split(nameSplittingTokens)); 
			List<String> userNameTokens = Arrays.asList(
					u.getName()
					.toUpperCase()
					.split(nameSplittingTokens))
					.stream()
					.filter(token->token.length()>3)
					.collect(Collectors.toList());
			boolean result = false;
			for(String passwordToken : passwordTokens) {
				if(userNameTokens.contains(passwordToken)) {
					result = true;
				}
			}
			return !result;
		});
		
		/**
		 * 2.1.c:
		 * Cannot contain the local part and domain part of email address
		 */
		rules.add((p,u) -> {
			String emailParts[] = u.getEmail().split("@");
			return p.toUpperCase().indexOf(emailParts[0].toUpperCase()) < 0  
					&& p.toUpperCase().indexOf(emailParts[1].toUpperCase()) < 0;
		});
		
		/**
		 * 2.1.d
		 * Must contain characters from 3 of the following 5 character sets:
		 * 
         * • Upper Case Characters: A-Z
         * • Lower Case Characters: a-z
         * • Numbers: 0-9
         * • Punctuation Characters: !’?”-:,;()[]{}
         * • Symbols: ~@#$%^&*+=|<>/\
         * 
		 */
		rules.add((p,u) -> {
			int groupsMatchingCount = 0;
			Pattern upperCaseCharactersPresent = Pattern.compile("[A-Z]");
			Matcher upperCaseCharactersPresentMatcher = upperCaseCharactersPresent.matcher(p);
			groupsMatchingCount+=upperCaseCharactersPresentMatcher.find()?1:0;

			Pattern lowerCaseCharactersPresent = Pattern.compile("[a-z]");
			Matcher lowerCaseCharactersPresentMatcher = lowerCaseCharactersPresent.matcher(p);
			groupsMatchingCount+=lowerCaseCharactersPresentMatcher.find()?1:0;

			Pattern numbersPresent = Pattern.compile("[0-9]");
			Matcher numbersPresentMatcher = numbersPresent.matcher(p);
			groupsMatchingCount+=numbersPresentMatcher.find()?1:0;

			Pattern punctuationPresent = Pattern.compile("[!’?\\”\\-:,;\\(\\)\\[\\]\\{\\}]");
			Matcher punctuationPresentMatcher = punctuationPresent.matcher(p);
			groupsMatchingCount+=punctuationPresentMatcher.find()?1:0;
			
			String[] charactersInClass = new String[] {
					"~"
					,"@"
					,"#" 
					,"\\$" 
					,"%" 
					,"^" 
					,"&" 
					,"*" 
					,"+" 
					,"=" 
					,"\\|" 
					,"<" 
					,">" 
					,"/" 
					,"\\\\"};
			String symbolPatternString = "["+Arrays.asList(charactersInClass).stream().collect(Collectors.joining())+"]";
			Pattern symbolsPresent = Pattern.compile(symbolPatternString);
			Matcher symbolPresentMatcher = symbolsPresent.matcher(p);
			groupsMatchingCount+=symbolPresentMatcher.find()?1:0;
			
			return groupsMatchingCount >= 3;
			
		});
	}

	public boolean isValid(User user, String newPassword) {
		boolean result = true;
		Iterator<Rule> iterator = rules.listIterator();
		while (result && iterator.hasNext()) {
			result = iterator.next().test(newPassword, user);
		}
		return result;
	}

}
