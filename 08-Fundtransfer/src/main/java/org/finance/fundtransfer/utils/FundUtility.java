/**
 * 
 */
package org.finance.fundtransfer.utils;

import java.security.SecureRandom;
import java.util.Random;

import javax.validation.Valid;

import org.finance.fundtransfer.entity.AccountTransaction;
import org.finance.fundtransfer.request.UserRegistrationRequest;

/**
 * @author Athatey Shyam
 *
 */
public class FundUtility {
	private final Random RANDOM = new SecureRandom();
	private final String NUMBERS = "0123456789";
	private final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$_";

	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
		}

		return new String(returnValue);
	}

	public String generateAccountNo(int length) {
		return generateRandomString(length);
	}

	public String generatePassword(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));
		}

		return new String(returnValue);
	}

	/**
	 * @param request
	 * @return
	 */
	public static boolean validateUserRegistrationRequest(UserRegistrationRequest request) {
		if (!(request.getFirstName() == null || request.getFirstName().trim().isEmpty() || request.getLastName() == null
				|| request.getLastName().trim().isEmpty() || request.getEmail() == null
				|| request.getEmail().trim().isEmpty() || request.getMobile() == null
				|| request.getMobile().trim().isEmpty() || request.getPanNo() == null
				|| request.getPanNo().trim().isEmpty() || request.getAadharNo() == null
				|| request.getAadharNo().trim().isEmpty() || request.getAge() == null
				|| request.getAge().trim().isEmpty() || request.getGender() == null
				|| request.getGender().trim().isEmpty() || request.getListOfAddress() == null
				|| request.getListOfAddress().isEmpty()))
			return false;
		return true;
	}

	/**
	 * @param i
	 * @return
	 */
	public String genrateTransactionId(int length) {
		return generatePassword(length);
	}
	
	public static AccountTransaction generateTransactionDetails(String fromAccNo, String toAccNo, Double balance, String type,
			String transactionId) {
		AccountTransaction transaction = new AccountTransaction();
		transaction.setFromAccount(fromAccNo);
		transaction.setToAccount(toAccNo);
		transaction.setTransactionType(type);
		transaction.setBalance(balance);
		transaction.setTransactionId(transactionId);

		return transaction;
	}

}
