package com.iqvia.services;

import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.iqvia.handler.ValidPasswordHandler;
import com.iqvia.model.BatchItem;

@Path("/")
public class ValidPasswordsService {

	@GET
	@Produces({"text/csv","application/json"})
	@Path("/valid-passwords")
	public Response getStartingPageAsCsv(@HeaderParam("accept") String acceptHeader) {
		Response result = null;
		try {
			ValidPasswordHandler handler = new ValidPasswordHandler();
			List<BatchItem> items = handler.getValidBatchItems();
			if (acceptHeader.contains("json")) {
				String itemsAsString = new ObjectMapper().writeValueAsString(items);
				result = Response.status(Response.Status.OK).entity(itemsAsString).build();
			} else if (acceptHeader.contains("csv")) {
				CsvMapper csvMapper = new CsvMapper().enable(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS);
				CsvSchema schema = csvMapper.schemaFor(BatchItem.class);

				ObjectWriter objectWriter = csvMapper.writer(schema);
				StringWriter csvWriter = new StringWriter();
				objectWriter.writeValues(csvWriter).writeAll(items);

				result = Response.status(Response.Status.OK).entity(csvWriter.toString()).build();
			}
		} catch (Throwable throwable) {
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return result;

	}

}