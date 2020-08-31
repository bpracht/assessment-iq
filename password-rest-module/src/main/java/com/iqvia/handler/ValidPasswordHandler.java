package com.iqvia.handler;

import java.io.IOException;
import java.util.List;

import com.iqvia.dao.BatchItemServices;
import com.iqvia.model.BatchItem;
import com.iqvia.model.User;

public class ValidPasswordHandler {

	public List<BatchItem> getValidBatchItems() throws IOException {
		BatchItemServices batchItemServices = new BatchItemServices();
		List<BatchItem> batchItems = batchItemServices.getBatchItems();
		PasswordValidator passwordValidator = new PasswordValidator();
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		for (BatchItem batchItem : batchItems) {
			User user = new User();
			user.setEmail(batchItem.getEmail());
			user.setName(batchItem.getName());

			if (!passwordValidator.isValid(user, batchItem.getInitialPassword())) {
				String newPassword = passwordGenerator.generatePassword(8);
				batchItem.setInitialPassword(newPassword);
			}
		}
		return batchItems;
	}

}
