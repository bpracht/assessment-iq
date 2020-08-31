package com.iqvia.dao;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.iqvia.model.BatchItem;

public class BatchItemServicesTest {

	@Test
	public void readBatchItems() throws IOException {
		BatchItemServices batchItemServices = new BatchItemServices();
		List<BatchItem> batchItems = batchItemServices.getBatchItems();
		for (BatchItem batchItem : batchItems) {
			System.out.println(batchItem);
		}
	}


}
